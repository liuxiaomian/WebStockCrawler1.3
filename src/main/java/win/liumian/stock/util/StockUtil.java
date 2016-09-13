package win.liumian.stock.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.text.DecimalFormat;

/**
 * Created by liumian on 16/8/6.
 */
public class StockUtil {

    public static void writeStockCodeToFile() throws IOException {
        String httpUrl = "http://apis.baidu.com/apistore/stockservice/stock";

        DecimalFormat format = new DecimalFormat("600000");
        Writer writer = new FileWriter(new File("shcode.txt"));
        int i = 0;
        while(i<9999){
            System.out.println(i);
            StringBuffer httpArg = new StringBuffer("stockid=");

            for (int j = 0; j < 10; j++) {
                String code = format.format(i++);
                httpArg.append("sh"+code+",");
            }
            httpArg.append("sh"+format.format(i++));
            httpArg.append("&list=1");
//            String codeInfo = BaiduCrawler.request(httpUrl,httpArg.toString());
//            writeStockCodeToFile(codeInfo,writer);
        }
    }


    public static void writeStockCodeToFile(String stockInfo,Writer writer){
        JSONObject stocks = JSONObject.parseObject(stockInfo);
        JSONObject retData = stocks.getJSONObject("retData");
        JSONArray stockList = retData.getJSONArray("stockinfo");
        for (int i = 0; i < 10; i++) {
            if (!stockList.getJSONObject(i).getString("name").trim().equals("")){
                String code = stockList.getJSONObject(i).getString("code");
                try {
                    writer.write(code+",");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            StockUtil.writeStockCodeToFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] subArray(String[] array,int start,int end){
        String[] subArray = new String[end - start];
        for (int i = 0; i < end - start; i++) {
            subArray[i] = array[start+i];
        }
        return subArray;
    }

}
