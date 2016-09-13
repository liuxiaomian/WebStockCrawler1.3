import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liumian on 16/8/11.
 */
public class CodeCrawler {

    public static void main(String[] args) {

        CodeCrawler crawler = new CodeCrawler();

        DecimalFormat format = new DecimalFormat("000000");


        int i = 0;
        while (i < 9999){

            List<String> shCodes = new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                shCodes.add("sz"+format.format(i++));
            }
            String url = crawler.createUrl(shCodes);
            try {
                List<String> records = crawler.request(url);
                List<String> codes = crawler.getCodes(records);
                crawler.writeCodeToFile(codes);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }


    public String createUrl(List<String> codes){
        StringBuffer urlSB = new StringBuffer("http://hq.sinajs.cn/list=");
        for (String code : codes) {
            urlSB.append(code + ",");
        }
        urlSB.deleteCharAt(urlSB.length() - 1);
        return urlSB.toString();
    }


    public List<String> request(String url) throws IOException {

        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);

        List<String> records = new ArrayList<>();
        HttpResponse response = client.execute(httpGet);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"gbk"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                records.add(line);
            }
            return records;
        }
    }

    public List<String> getCodes(List<String> records) {
        List<String> codes = new ArrayList<>(records.size());
        for (String record : records) {
            String[] part = record.split("=");
            if (!part[1].equals("\"\";")) {
                String codeAndData = part[0].substring(part[0].indexOf("sz"));
                codes.add(codeAndData);
            }
        }
        return codes;
    }

    public void writeCodeToFile(List<String> codes) throws IOException {

        FileWriter fw = new FileWriter(new File("code.txt"),true);
        for (String code:codes){
            System.out.println(code);
            fw.write(code+",");
        }
        fw.flush();
    }

}
