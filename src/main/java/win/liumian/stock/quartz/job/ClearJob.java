package win.liumian.stock.quartz.job;

import org.apache.log4j.Logger;
import win.liumian.stock.crawler.SinaCrawler;
import win.liumian.stock.po.StockCollection;
import win.liumian.stock.po.StockDataPo;
import win.liumian.stock.service.DataAdjustService;
import win.liumian.stock.util.CodeSingleton;
import win.liumian.stock.util.StockConstant;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by liumian on 16/8/8.
 */
public class ClearJob {

    private static Logger logger = Logger.getLogger(ClearJob.class);


    private DataAdjustService adjustService;

    public void execute() {
        adjustService.adjustMaxRatio();
    }

    public void setAdjustService(DataAdjustService adjustService) {
        this.adjustService = adjustService;
    }
}
