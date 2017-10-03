package PushNotePack1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.Gson;

import PushNotePack1.Ticket.Cust_field;

public class PushNoteClass {
	public static void mainlls(String args[]) throws Exception
	{
		String u="",nowPrint="",output;
		
		URL url = new URL("https://konysolutions.zendesk.com/api/v2/views/130458947/tickets.json");// https://konysolutions.zendesk.com/agent/filters/130458947 
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
		Tickets tickets= gson.fromJson(new InputStreamReader(con.getInputStream()), Tickets.class);
		//System.out.println(tickets.getTickets().size());
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
		while((u1=br.readLine())!=null)
		{
			System.out.println(u1);
			//u=br.readLine();
			if(j==1)
			{
				System.out.println("manp3 "+u1);
				j=0;
			}
			
			if(u1.indexOf("ony.com")!=-1)
			{   
				u1=br.readLine();
				System.out.println("manp1 "+u1);
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
		for(Ticket t:tickets.getTickets())
		{	
			
			System.out.println(t.getId()+"    "+t.getUpdated_at()+"   "+t.getSubject()+"   "+t.getVia().getChannel());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String udd=t.getUpdated_at();
			System.out.println("real update"+udd);
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
				System.out.println("the diff is "+(currentDateValue-premiumUpdateValue));
				if((currentDateValue-premiumUpdateValue)<(6*60))//for hours, if u need in min then replace 5 with sec val
				{
					String name ="";
					for(Cust_field cf:t.getCust())
					{
						if(cf.getId().equals("21145230"))
						{	name=cf.getValue();
							break;
						}
					}
					String reqResult[] = new String[6];
					reqResult[0]=t.getId();
					reqResult[1]=t.getUpdated_at();
					reqResult[2]=t.getSubject();
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
					PushDemo pd= new PushDemo();
					pd.mains(reqResult);
					System.out.println("project completed");
				    
				}
			
			
		  }
	   }
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
