package win.liumian.stock.util;

import win.liumian.stock.po.StockDataPo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liumian on 16/8/11.
 */
public class FileUtil {

    public static File getFile(String fileName) {
        return new File(fileName);
    }

    public static Map<String, StockDataPo> readData(File file) {
        Map<String, StockDataPo> priceRecord = new HashMap<String, StockDataPo>(3000);

        try (BufferedReader br = new BufferedReader(new FileReader(file));) {
            String line = null;
            while ((line = br.readLine()) != null) {

                String[] record = line.split("=");
                String code = record[0];
                String[] dataArray = record[1].split(",");
                StockDataPo stockDataPo = new StockDataPo(code);
                priceRecord.put(code, stockDataPo);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return priceRecord;
    }

}
