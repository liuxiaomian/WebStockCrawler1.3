package win.liumian.stock.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liumian on 16/8/7.
 */
public class StockConstant {

    public static final int THRESHOLD = 100;

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

//    public static volatile int crawlTimes = 0;

    public static final AtomicInteger CRAWL_TIMES = new AtomicInteger(-1);

}
