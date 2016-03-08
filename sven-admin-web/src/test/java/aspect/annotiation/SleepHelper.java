package aspect.annotiation;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;

/**
 * Created by stefan on 16-1-25.
 * advice通知
 */
@Aspect
public class SleepHelper{

    @Pointcut(value = "execution(* *.*sleep(..)) && args(method, method1)", argNames = "method, method1")
    public void sleeppoint(Method method, Method method1){}

    @AfterReturning(pointcut = "sleeppoint(method, method1)", argNames = "method, method1")
    public void afterReturning(Method method, Method method1) throws Throwable {
        System.out.println(method.getName() + ": 起床以后要穿衣服");
    }

    @Before(value = "sleeppoint(method, method1)", argNames = "method, method1")
    public void before(Method method, Method method1) throws Throwable {
        System.out.println(method.getName() + ": 睡觉之前要脱衣服");
    }
}
