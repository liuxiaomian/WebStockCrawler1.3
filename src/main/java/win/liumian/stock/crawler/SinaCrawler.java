package win.liumian.stock.crawler;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import win.liumian.stock.po.StockCollection;
import win.liumian.stock.util.StockConstant;
import win.liumian.stock.util.StockUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

/**
 * Created by liumian on 16/8/8.
 */
public class SinaCrawler extends RecursiveAction {

    //1.获取数据 2.将获取的数据统一处理

    private String[] codes;

    private StockCollection stockCollection;


    public SinaCrawler(String[] codes, StockCollection stockCollection) {
        this.codes = codes;
        this.stockCollection = stockCollection;
    }


    protected void compute() {
        if (codes.length < StockConstant.THRESHOLD) {
            StringBuffer sb = new StringBuffer(codes[0]);
            for (int i = 1; i < codes.length; i++) {
                sb.append("," + codes[i]);
            }
            Map<String, String> price = doCrawl(sb.toString());
            stockCollection.putAllStock(price,StockConstant.CRAWL_TIMES.get());

        } else {
            String[] leftArray = StockUtil.subArray(codes, 0, codes.length / 2);
            String[] rightArray = StockUtil.subArray(codes, codes.length / 2, codes.length);

            SinaCrawler left = new SinaCrawler(leftArray, stockCollection);
            SinaCrawler right = new SinaCrawler(rightArray, stockCollection);

            invokeAll(left, right);

        }
    }

    /**
     * 爬取 上证数据
     */
    public void shCompute() {
        compute();
    }


    /**
     * 从新浪服务器抓取数据
     *
     * @param code 股票代码
     * @return 返回代码和当前价格的HashMap
     */
    private Map<String, String> doCrawl(String code) {
        HttpClient client = new DefaultHttpClient();
        StringBuffer sb = new StringBuffer("http://hq.sinajs.cn/list=");
        sb.append(code);
        HttpGet httpGet = new HttpGet(sb.toString());
        try {
            HttpResponse response = client.execute(httpGet);

            BufferedReader br =
                    new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "gbk"));
            StringBuffer content = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
            br.close();
            return parseData(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 分析数据
     *
     * @param data 数据
     * @return 如:Map(sh601899,price)
     */
    public Map<String, String> parseData(String data) {
        Map<String, String> codeAndPrice = new HashMap<String, String>(130);
        String[] stockArray = data.split(";");
        for (int i = 0; i < stockArray.length; i++) {
            String[] stockInfo = stockArray[i].split(",");
            if (stockInfo.length > 4) {
                String code = stockInfo[0].substring(stockInfo[0].lastIndexOf("_") + 1, stockInfo[0].indexOf("="));
                codeAndPrice.put(code, stockInfo[3]);

            }
        }
        return codeAndPrice;
    }


}
