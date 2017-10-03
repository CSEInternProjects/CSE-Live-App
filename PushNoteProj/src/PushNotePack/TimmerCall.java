package PushNotePack;
import java.util.Timer;
public class TimmerCall{
	public static void mains1(String args[])
	{
		TriggerPoint task = new TriggerPoint();
		Timer timer = new Timer();
    	timer.scheduleAtFixedRate(task, 500,60*60*1000);
	}
}