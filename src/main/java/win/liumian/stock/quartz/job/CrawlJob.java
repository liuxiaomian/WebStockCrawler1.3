package win.liumian.stock.quartz.job;

import org.apache.log4j.Logger;
import win.liumian.stock.crawler.SinaCrawler;
import win.liumian.stock.po.StockCollection;
import win.liumian.stock.util.CodeSingleton;
import win.liumian.stock.util.StockConstant;

import java.util.concurrent.ForkJoinPool;

/**
 * Created by liumian on 16/8/8.
 */
public class CrawlJob {

    private static Logger logger = Logger.getLogger(CrawlJob.class);

    private CodeSingleton codeSingleton;

    private StockCollection priceRecord;

    private ForkJoinPool pool;

    public void execute() {
        StockConstant.CRAWL_TIMES.incrementAndGet();
        logger.info("No."+StockConstant.CRAWL_TIMES.get()+" job start ...");
        long start = System.currentTimeMillis();

        /**
         * 首先获得上证指数数据
         */
        String[] shCode = {"sh000001"};
        SinaCrawler shCrawler = new SinaCrawler(shCode, priceRecord);
        shCrawler.shCompute();

        String[] codes = codeSingleton.getCodes();
        SinaCrawler crawler = new SinaCrawler(codes, priceRecord);
        pool.invoke(crawler);

        long end = System.currentTimeMillis();
        logger.info("consume time: " + (end - start));
    }


    public void setCodeSingleton(CodeSingleton codeSingleton) {
        this.codeSingleton = codeSingleton;
    }

    public void setPriceRecord(StockCollection priceRecord) {
        this.priceRecord = priceRecord;
    }


    public void setPool(ForkJoinPool pool) {
        this.pool = pool;
    }
}
