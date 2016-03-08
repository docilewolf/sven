package aspect.xml;

/**
 * Created by stefan on 16-1-25.
 * advice通知
 */
public class SleepHelper{
    public void after(){
        System.out.println("起床后要锻炼身体");
    }

    public void afterReturning() throws Throwable {
        System.out.println("起床以后要穿衣服");
    }

    public void before() throws Throwable {
        System.out.println("睡觉之前要脱衣服");
    }

}
