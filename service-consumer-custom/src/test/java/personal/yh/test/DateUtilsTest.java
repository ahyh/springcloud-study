package personal.yh.test;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class DateUtilsTest {

    @Test
    public void test(){
        long seconds = DateUtils.getFragmentInSeconds(new Date(), Calendar.MINUTE);
        System.out.println(seconds);
    }
}
