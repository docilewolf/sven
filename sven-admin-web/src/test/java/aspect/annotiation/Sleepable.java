package aspect.annotiation;

import java.lang.reflect.Method;

/**
 * Created by stefan on 16-1-25.
 */
public interface Sleepable {
    public void sleep();

    public void sleep(Method method, Method method1);
}
