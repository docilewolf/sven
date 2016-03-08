import aspect.annotiation.Sleepable;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by stefan on 16-1-25.
 */
public class AnnotionationMain {
    @Test
    public void Test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-v3.xml");
        Sleepable sleepable = (Sleepable) context.getBean("humanSleep");
        sleepable.sleep();
        sleepable.sleep(context.getClass().getDeclaredMethods()[0], sleepable.getClass().getDeclaredMethods()[1]);
    }
}
