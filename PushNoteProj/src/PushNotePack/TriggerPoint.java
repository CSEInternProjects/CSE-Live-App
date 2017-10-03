package PushNotePack;

import java.util.TimerTask;
public class TriggerPoint extends TimerTask 
{
	@Override
	public void run()  {
		PushNoteClass pnc= new PushNoteClass();
		try {
			//pnc.StartPointToExtractTickets();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void schedule(TimerTask task,
            long delay,
            long period)
	{
		System.out.println("Hello World");
	}
}