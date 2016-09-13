package win.liumian.stock.service;

import win.liumian.stock.po.StockCollection;
import win.liumian.stock.po.StockDataPo;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by liumian on 16/8/15.
 */
public class DataAdjustService {

    private StockCollection priceRecord;


    public int adjustMaxRatio(){

        Set<String> keys = priceRecord.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()){
            String code = iterator.next();
            StockDataPo stockData = priceRecord.get(code);
            stockData.setMaxRatio(new BigDecimal(0));
            stockData.setShMaxRatio(new BigDecimal(0));
            stockData.setShTimes(0);
            stockData.setSurpassTimes(0);
        }
        return 0;
    }

    public void setPriceRecord(StockCollection priceRecord) {
        this.priceRecord = priceRecord;
    }
}
