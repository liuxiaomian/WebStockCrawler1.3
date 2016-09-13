package win.liumian.stock.po;

import win.liumian.stock.util.StockConstant;
import win.liumian.stock.util.StockUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liumian on 16/8/12.
 */
public class StockDataPo implements Comparable<StockDataPo> {

    private String code;

    private BigDecimal current, riseSum, declineSum, ratio, maxRatio, shMaxRatio;

    private int surpassTimes, shTimes;

    private List<BigDecimal> priceRecord;


    public StockDataPo(String code) {
        this.code = code;
        priceRecord = new ArrayList<>(120);
        riseSum = new BigDecimal(0);
        declineSum = new BigDecimal(0);
        ratio = new BigDecimal(0);
        maxRatio = new BigDecimal(0);
        shMaxRatio = new BigDecimal(0);
        surpassTimes = 0;
    }


    /**
     * 更新价格
     *
     * @param price 价格
     * @param times 第几次更新,用于确定计算下标
     */
    public void updatePrice(String price, int times) {
        current = new BigDecimal(price);
        if (times > 119) {
            int index = times % 120 == 0 ? 119 : (times % 120) - 1;
            BigDecimal latest = priceRecord.get(index);
            computeChange(latest);
            rollBackPrice(times);
            priceRecord.set(times % 120, current);
        } else {
            priceRecord.add(current);
            if (times != 0) {
                BigDecimal latest = priceRecord.get(times - 1);
                computeChange(latest);
            }
        }
    }


    public void updateRatio() {

        if (riseSum.doubleValue() != 0 && declineSum.doubleValue() != 0) {
            ratio = riseSum.divide(declineSum.abs(), 3);
            if (ratio.doubleValue() > 1.5) {
                surpassTimes++;
            }
            if (ratio.compareTo(maxRatio) == 1) {
                maxRatio = ratio;
            }
        }
    }

    public void updateShRatio(StockDataPo sh) {
        if (sh.getRatio().doubleValue() != 0) {

            BigDecimal result = this.getRatio().divide(sh.getRatio(), 3);
            if (result.doubleValue() > 1.5) {
                this.shTimes++;
            }
            if (result.compareTo(shMaxRatio) == 1) {
                this.shMaxRatio = result;
            }
        }
    }

    public void computeChange(BigDecimal latest) {
        if (latest.doubleValue() != 0) {
            BigDecimal change = current.subtract(latest);
            if (change.doubleValue() > 0) {
                riseSum = riseSum.add(change);
            } else {
                declineSum = declineSum.add(change);
            }

        }
    }

    private void rollBackPrice(int times) {
        //还原掉一百二十分钟以前的改变
        BigDecimal old = priceRecord.get(times % 120);
        int index = times % 120 == 119 ? 0 : (times % 120) + 1;
        BigDecimal oldChange = priceRecord.get(index).subtract(old);

        if (oldChange.doubleValue() > 0) {
            riseSum = riseSum.subtract(oldChange);
        } else {
            declineSum = declineSum.subtract(oldChange);
        }
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getRiseSum() {
        return riseSum;
    }

    public BigDecimal getDeclineSum() {
        return declineSum;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public BigDecimal getMaxRatio() {
        return maxRatio;
    }

    public int getSurpassTimes() {
        return surpassTimes;
    }

    public List<BigDecimal> getPriceRecord() {
        return priceRecord;
    }

    public BigDecimal getCurrent() {
        return current;
    }

    public BigDecimal getShMaxRatio() {
        return shMaxRatio;
    }

    public int getShTimes() {
        return shTimes;
    }

    public void setMaxRatio(BigDecimal maxRatio) {
        this.maxRatio = maxRatio;
    }

    public void setShMaxRatio(BigDecimal shMaxRatio) {
        this.shMaxRatio = shMaxRatio;
    }

    public void setSurpassTimes(int surpassTimes) {
        this.surpassTimes = surpassTimes;
    }

    public void setShTimes(int shTimes) {
        this.shTimes = shTimes;
    }

    @Override
    public int compareTo(StockDataPo o) {
        return ratio.compareTo(o.getRatio());
    }
}
