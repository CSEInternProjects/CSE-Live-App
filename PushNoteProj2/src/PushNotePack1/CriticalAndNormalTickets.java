package PushNotePack1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import com.google.gson.Gson;

import PushNotePack1.Ticket.Cust_field;

public class CriticalAndNormalTickets {

	public static void main(String args[]) throws Exception
	{
		ViewCalls.HighAndNormalTicketsHandle(null);
		criticalHandle(null);
	}

	private static void criticalHandle(Object object) {
		// TODO Auto-generated method stub
		//0=id
		//1=create
		//2=content with priority
		//3=empId
		//4=priority
		//5=BDE or Team
		//6=customer name

		try {
		String u="",nowPrint="",output;
		ViewCalls vc= new ViewCalls();
		URL url = new URL("https://konysolutions.zendesk.com/api/v2/views/114119982033/tickets.json");// https://konysolutions.zendesk.com/agent/filters/130458947 
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		String s=new String("product.license@kony.com/token:GneshbUqcIKpLtGFQsTTjxhrrGQvnsi9g68PIrFa");
		String auth=Base64.getEncoder().encodeToString(s.getBytes());
		con.setDoOutput(true);
		con.setRequestProperty("Authorization", "Basic "+auth);
		con.setRequestMethod("GET");
		
		/*Scanner sc1 = new Scanner(con.getInputStream());
		while(sc1.hasNextLine()){
			output = sc1.nextLine();   
			
			nowPrint += output;
			System.out.println(output);
		}
		*/
		
		Gson gson = new Gson();
		Tickets tickets2= gson.fromJson(new InputStreamReader(con.getInputStream()), Tickets.class);
		con.disconnect();
		//sc1.close();
		//for(int j=0;j<tickets.getTickets().size();j++)
		//Ticket t=tickets.getTickets().get(j);
		//try {
		File f= new File("C:\\Users\\KH9331\\Desktop\\CSE_Engg-Tags.txt");
		//File f= new File("/data/CSE-ENG/CSE_Engg-Tags.txt");
		FileReader fr= new FileReader(f);
		BufferedReader br= new BufferedReader(fr);
		boolean b=true;
		String u1="";
		String empTag[]= new String[50];
		String empId[]= new String[50];
		int i=0,j=0;
		int counter=0;
		for(Ticket t2:tickets2.getTickets())
		{
			counter++;
		}
		System.out.println("counter for tickets"+counter);
		while((u1=br.readLine())!=null)
		{
			if(counter==0)
			{
				System.out.println("\n\n not records found for critical ticket");
				return;
			}
			//System.out.println(u1);
			//u=br.readLine();
			if(j==1)
			{
				//System.out.println("manp3 "+u1);
				j=0;
			}
			
			if(u1.indexOf("ony.com")!=-1)
			{   
				u1=br.readLine();
				//System.out.println("manp1 "+u1);
			   empId[i]=u1.trim();
				//b=false;
			}
				if(u1.indexOf("Tag:")!=-1)
				   
				{
					u1=br.readLine();
					System.out.println("manp2 "+u1);
					//b=true;
					empTag[i]=u1;
					i++;
				}
			   if(u1.indexOf("Title")!=-1)
				   
				{
					j=1;
				   //b=true;
				
				}
		}
		try {
			String reqResult[] = new String[12];
			for(Ticket t2:tickets2.getTickets())
			{	
				
				//System.out.println(t.getId()+"    "+t.getCreated_at()+"   "+t.getSubject()+"   "+t.getVia().getChannel());
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String udd=t2.getCreated_at();
				//System.out.println("real update"+udd);
				udd=udd.replace("T", " ");
				udd=udd.substring(0, 19);
				Date dt1 = sdf.parse(udd);
				//System.out.println(dt1);
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				Long currentDateValue= (new Date().getTime())/(1000*60);//min
				System.out.println("current time"+date);
					Long premiumUpdateValue=(dt1.getTime())/(1000*60);// if i need in min then remove 60 division in back 
					premiumUpdateValue=premiumUpdateValue+((5*60)+30);
					System.out.println("update time"+dt1);
					System.out.println("the diff is "+(currentDateValue-premiumUpdateValue)+" diff should be less than"+150*60);
					String name ="";
					for(Cust_field cf:t2.getCust())
					{
						if(cf.getId().equals("21145230"))
						{	
							name=cf.getValue();
						}
						if(cf.getId().equals("21277110"))
						{	
							reqResult[6]=cf.getValue();
						}
					}
					
						//
						
						reqResult[0]=t2.getId();
						reqResult[1]=t2.getCreated_at();
						reqResult[2]="this "+t2.getPriority()+" priority ticket is going to breach with in 36 hours";
						//System.out.println(" hii "+name);
						for(int temp=0;temp<empTag.length;temp++)
						{
							if(empTag[temp]!=null)
							{	
								empTag[temp]=empTag[temp].trim();
								if(empTag[temp].equals(name))
								{
									reqResult[3]=empId[temp];
									System.out.println("emp id "+reqResult[3]+" name is "+empTag[temp]);
									break;
								}
							}
						}
						reqResult[4]=t2.getPriority();
						if(t2.getAssignee_id().equals("14272801548"))
							reqResult[5]="BDE";
						else
							reqResult[5]="Team";
						//vc.OnlyAssine(reqResult);
						premiumUpdateValue=premiumUpdateValue+(1*60*24);
						
						if((premiumUpdateValue-currentDateValue)<(8*60)&&(premiumUpdateValue-currentDateValue>0))//for hours, if u need in min then replace 5 with sec val
						{
							reqResult[2]="this "+t2.getPriority()+" priority ticket is going to breach with in 8 hours";
							vc.commonAccessForDatabaseCheck(reqResult, "assigneEngg");
						}
						if((premiumUpdateValue-currentDateValue)<(4*60)&&(premiumUpdateValue-currentDateValue>0))
					{
						reqResult[2]="this "+t2.getPriority()+" priority ticket is going to breach with in 4 hours";
						vc.commonAccessForDatabaseCheck(reqResult, "manager");
					
					}
						if((premiumUpdateValue-currentDateValue)<(2*60)&&(premiumUpdateValue-currentDateValue>0))
					{
						reqResult[2]="this "+t2.getPriority()+" priority ticket is going to breach with in 2 hours";
						vc.commonAccessForDatabaseCheck(reqResult, "premierManager");
					
					}
				
				
			  }
		   }
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

	}
	
}
