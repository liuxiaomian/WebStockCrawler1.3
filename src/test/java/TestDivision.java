import java.math.BigDecimal;

/**
 * Created by liumian on 16/8/12.
 */
public class TestDivision {

    public static void main(String[] args) {
//        double last = 3015.785;
//        double current = 3016.4548;
//
//        double result = ((current / last) - 1) * 100 ;
//
//        result += result;
//
//        System.out.println(result);

        BigDecimal last = new BigDecimal("0");
        BigDecimal latest = new BigDecimal("1");
        BigDecimal current = new BigDecimal("3");

//        System.out.println(latest.divide(last));
        System.out.println(latest.divide(current,3,BigDecimal.ROUND_HALF_DOWN));
        System.out.println(last.divide(current));


    }

}
