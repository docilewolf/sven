import aspect.Sleepable;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by stefan on 16-1-25.
 */
public class Main {

    @Test
    public void Test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-v2.xml");
        Sleepable sleepable = (Sleepable) context.getBean("humanSleep");
        sleepable.sleep();
    }
}
