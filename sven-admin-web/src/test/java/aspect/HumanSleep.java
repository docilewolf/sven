package aspect;


/**
 * Created by stefan on 16-1-25.
 */
public class HumanSleep implements Sleepable {
    @Override
    public void sleep() {
        System.out.println("i am going to sleep");
    }
}
