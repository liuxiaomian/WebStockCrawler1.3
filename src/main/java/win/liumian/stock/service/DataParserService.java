package win.liumian.stock.service;

import com.alibaba.fastjson.JSONArray;
import org.apache.log4j.Logger;
import win.liumian.stock.po.StockCollection;
import win.liumian.stock.po.StockDataPo;
import win.liumian.stock.util.DataUtil;
import win.liumian.stock.util.FileUtil;

import java.io.*;
import java.util.*;

/**
 * Created by liumian on 16/8/9.
 */

public class DataParserService {

    private static Logger logger = Logger.getLogger(DataParserService.class);
    private StockCollection priceRecord;

    public JSONArray parseCurrentData(int limit) {

        StockDataPo[] sorted = DataUtil.sortResult(priceRecord);
        StockDataPo shStock = priceRecord.get("sh000001");
        JSONArray jsonArray = new JSONArray(sorted.length);
        limit = limit < sorted.length ? limit : sorted.length;
        for (int i = 0; i < limit; i++) {
//            sorted[i].updateRatio();
//            sorted[i].updateShRatio(shStock);
            jsonArray.add(sorted[i]);
        }
        return jsonArray;

    }

    public JSONArray parseData(String fileName) {
        Map<String, StockDataPo> priceRecord = FileUtil.readData(FileUtil.getFile(fileName));

        StockDataPo[] sorted = DataUtil.sortResult(priceRecord);

        JSONArray jsonArray = new JSONArray(sorted.length);
        for (int i = 0; i < sorted.length; i++) {
            jsonArray.add(sorted[i]);
        }
        return jsonArray;

    }


    /**
     * 分析每一行记录
     * 1.上一周期价格
     * 2.当前价格
     * 3.上涨之和
     * 4.下跌之和
     * 5.当日最高价(大于1.5)
     * 6.超过1.5次数
     * 7.除以上证上涨与下跌之商 且大于1.5的最大值
     * 8.除以上证大于1.5次数
     * 9.上涨之和 除以 下跌之和
     */



    public void setPriceRecord(StockCollection priceRecord) {
        this.priceRecord = priceRecord;
    }
}
