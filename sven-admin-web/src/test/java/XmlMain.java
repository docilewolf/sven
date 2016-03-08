import aspect.xml.Sleepable;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by stefan on 16-1-25.
 */
public class XmlMain {
    @Test
    public void Test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-v4.xml");
        Sleepable sleepable = (Sleepable) context.getBean("humanSleep");
        sleepable.sleep();
    }
}
