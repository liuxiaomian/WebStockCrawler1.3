package win.liumian.stock.po;

import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liumian on 16/8/8.
 */

/**
 * 1.上一周期价格
 * 2.当前价格
 * 3.上涨之和
 * 4.下跌之和
 * 5.当日最高价(大于1.5)
 * 6.超过1.5次数
 * 7.除以上证上涨与下跌之商 且大于1.5的最大值
 * 8.除以上证大于1.5次数
 */
public class StockCollection extends ConcurrentHashMap<String, StockDataPo> {

    private static Logger logger = Logger.getLogger(StockCollection.class);

    public StockCollection(int initialCapacity) {
        super(initialCapacity);
    }

    public void putAllStock(Map<String, String> stocks,int times) {
        Set<String> codeSet = stocks.keySet();
        Iterator<String> iterator = codeSet.iterator();
        while (iterator.hasNext()) {
            String code = iterator.next();
            StockDataPo stock = putStock(code, stocks.get(code),times);
        }
    }

    private StockDataPo putStock(String code, String price,int times) {
        if (!this.containsKey(code)) {
            this.put(code,new StockDataPo(code));
        }
        StockDataPo stockDataPo = this.get(code);
        stockDataPo.updatePrice(price,times);
        stockDataPo.updateRatio();
        stockDataPo.updateShRatio(this.get("sh000001"));
        return stockDataPo;
    }

}
