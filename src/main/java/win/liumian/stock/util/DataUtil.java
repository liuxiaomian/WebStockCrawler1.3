package win.liumian.stock.util;

import com.sun.istack.internal.NotNull;
import win.liumian.stock.po.StockDataPo;

import java.util.Map;
import java.util.Set;

/**
 * Created by liumian on 16/8/11.
 */
public class DataUtil {


    public static StockDataPo[] sortResult(@NotNull Map<String, StockDataPo> data) {

        StockDataPo[] stockArray = data.values().toArray(new StockDataPo[0]);

        for (int i = 0; i < stockArray.length - 1; i++) {
            for (int j = 0; j < stockArray.length - i - 1; j++) {
                if (stockArray[j].compareTo(stockArray[j + 1]) == -1) {
                    StockDataPo temp = stockArray[j];
                    stockArray[j] = stockArray[j + 1];
                    stockArray[j + 1] = temp;
                }
            }
        }
        return stockArray;
    }
}
