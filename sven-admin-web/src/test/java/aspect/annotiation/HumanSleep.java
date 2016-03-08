package aspect.annotiation;

import java.lang.reflect.Method;

/**
 * Created by stefan on 16-1-25.
 */
public class HumanSleep implements Sleepable {
    @Override
    public void sleep() {
        System.out.println("i am going to sleep");
    }

    @Override
    public void sleep(Method method, Method method1) {
        System.out.println(method1.getName() + ": i am going to sleep");
    }
}
