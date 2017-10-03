package PushNotePack1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import com.google.gson.Gson;

import PushNotePack1.Ticket.Cust_field;

public class ViewCalls {
	static String[] managersList= new String[]{ "Uday Koneti","Manish Khemka","Venkata Naagaboina","Raj"};
	static String[] RespectivemanagerIds= new String[]{"KH2205","KH2106","KH2181","1570"};
	
	
	public static void HighAndNormalTicketsHandle(String args[]) throws Exception
	{
		String u="",nowPrint="",output;
		ViewCalls vc= new ViewCalls();
		URL url = new URL("https://konysolutions.zendesk.com/api/v2/views/114120102914/tickets.json");// https://konysolutions.zendesk.com/agent/filters/130458947 
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
			for(Ticket t:tickets.getTickets())
			{	
				
				//System.out.println(t.getId()+"    "+t.getCreated_at()+"   "+t.getSubject()+"   "+t.getVia().getChannel());
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String udd=t.getCreated_at();
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
					for(Cust_field cf:t.getCust())
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
						
						reqResult[0]=t.getId();
						reqResult[1]=t.getCreated_at();
						reqResult[2]="this "+t.getPriority()+" priority ticket is going to breach with in 36 hours";
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
						reqResult[4]=t.getPriority();
						if(t.getAssignee_id().equals("14272801548"))
							reqResult[5]="BDE";
						else
							reqResult[5]="Team";
						//vc.OnlyAssine(reqResult);
						
					    if(t.getPriority().equalsIgnoreCase("high"))
					    	premiumUpdateValue=premiumUpdateValue+(1*60*24*7);
					    if(t.getPriority().equalsIgnoreCase("normal"))
					    	premiumUpdateValue=premiumUpdateValue+(1*60*24*14);
						if((premiumUpdateValue-currentDateValue)<(36*60)&&(premiumUpdateValue-currentDateValue>0))//for hours, if u need in min then replace 5 with sec val
						{
							reqResult[2]="this "+t.getPriority()+" priority ticket is going to breach with in 36 hours";
							vc.commonAccessForDatabaseCheck(reqResult, "assigneEngg");
						}
						if((premiumUpdateValue-currentDateValue)<(24*60)&&(premiumUpdateValue-currentDateValue>0))
					{
						reqResult[2]="this "+t.getPriority()+" priority ticket is going to breach with in 24 hours";
						vc.commonAccessForDatabaseCheck(reqResult, "manager");
					
					}
					
						if((premiumUpdateValue-currentDateValue)<(12*60)&&(premiumUpdateValue-currentDateValue>0))
					{
						reqResult[2]="this "+t.getPriority()+" priority ticket is going to breach with in 12 hours";
						vc.commonAccessForDatabaseCheck(reqResult, "premierManager");
					
					}
					
				
			  }
		   }
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		
	}

	public void commonAccessForDatabaseCheck(String[] reqResult,String type)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://10.21.11.39:3306", "shiftroster", "Kony$$123");
			Statement stmt= con.createStatement();
			ResultSet rs=stmt.executeQuery("use csedatabase");
			rs= stmt.executeQuery("SELECT proActiveAlerts.ticketId FROM csedatabase.proActiveAlerts");
			String flafForTicketsconcomitant="";
			while(rs.next())
			{
				if(rs.getString(1).equalsIgnoreCase(reqResult[0]))
				{
					flafForTicketsconcomitant="exists";
					break;
				}
			}
			if(flafForTicketsconcomitant.equalsIgnoreCase("exists"))
			{
				
			}
			else
				{
					int ticketId=Integer.parseInt(reqResult[0]); 
					int x=stmt.executeUpdate("INSERT INTO csedatabase.proActiveAlerts (ticketId, assigneEngg, manager, premiemManager, priority) VALUES ("+ticketId+", 0, 0, 0,\""+reqResult[4]+"\")");
				}
			
			
			//rs= stmt.executeQuery("SELECT csedatabase.empdetails.EmployeeID,csedatabase.empdetails.ReportingManager FROM csedatabase.empdetails");
			if(type.equals("assigneEngg"))
				this.OnlyAssine(reqResult,stmt,rs);
			else if(type.equals("manager"))
				this.ForManagerAndAssignedEmployee(reqResult,stmt,rs);
			else if(type.equals("premierManager"))
				this.ForPremierManagerAnd_MGR_AssigneEngg(reqResult,stmt,rs);
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void ForPremierManagerAnd_MGR_AssigneEngg(String[] reqResult, Statement stmt, ResultSet rs) {
		// TODO Auto-generated method stub
		try {
			String SendToManager = "no";
			String[] customerNames=new String[] {"Chevron", "MBFS","Michaels Stores"};
			rs= stmt.executeQuery("SELECT proActiveAlerts.ticketId,proActiveAlerts.premiemManager FROM csedatabase.proActiveAlerts");
			while(rs.next())
			{
				if(rs.getString(1).equalsIgnoreCase(reqResult[0]))
				{
					if(rs.getInt(2)==0)
					{
						PushDemo pd= new PushDemo();
						pd.mains(reqResult);
						SendToManager="yes";
						
						System.out.println("project completed");
						int i= stmt.executeUpdate("UPDATE proActiveAlerts SET premiemManager=1 WHERE ticketId="+reqResult[0]);
						break;
					}
				}
				
			}
			String[] reqResultForManager=new String[reqResult.length];
			for(int i=0;i<reqResult.length;i++)
			{
				reqResultForManager[i]=reqResult[i];
			}
			reqResult[3]=reqResult[3].trim();
			if(SendToManager.equalsIgnoreCase("yes")) {
		    	rs=stmt.executeQuery("SELECT EmployeeID,ReportingManager FROM csedatabase.empdetails");
		        while(rs.next())
		        {
		        	if(rs.getString(1).equalsIgnoreCase(reqResult[3]))
		        	{
		        		for(int i=0;i<managersList.length;i++) {
		        			if(rs.getString(2).equalsIgnoreCase(managersList[i])) {
		        				reqResultForManager[3]=RespectivemanagerIds[i];
		        				PushDemo pd= new PushDemo();
								pd.mains(reqResultForManager);
		        			    System.out.println("\n\nmanager is done");
		        			    System.out.println("\n\n\n");
		        			}
		        				
		        		}
		        		
		        	}
		        }
		      
		        int i;
		        for(i=0;i<customerNames.length;i++)
		        {
		        	if(reqResult[6].equalsIgnoreCase(customerNames[i]))
		        	{
		        		//reqResult[3]="";
		        		break;
		        	}
		        	
		        }
		        if(i>=customerNames.length)
		        	{
		        		reqResult[3]="1570";
		        		System.out.println("\n\nraj gonna get push");
		        	}
		        else
		        	{
		        		reqResult[3]="6220";
		        		System.out.println("\n\n arthur got push");
		        	}
		        PushDemo pd= new PushDemo();
		        pd.mains(reqResult);
		        
		        
			}
			
			
			
		}
			catch (Exception e) {
				// TODO: handle exception
			System.out.println(e);
			}
		
	}

	private void OnlyAssine(String[] reqResult, Statement stmt, ResultSet rs) {
		// TODO Auto-generated method stub
		
		try {
		rs= stmt.executeQuery("SELECT proActiveAlerts.ticketId,proActiveAlerts.assigneEngg FROM csedatabase.proActiveAlerts");
		while(rs.next())
		{
			if(rs.getString(1).equalsIgnoreCase(reqResult[0]))
			{
				if(rs.getInt(2)==0)
				{
					PushDemo pd= new PushDemo();
					pd.mains(reqResult);
					System.out.println("project completed");
					int i= stmt.executeUpdate("UPDATE proActiveAlerts SET assigneEngg=1 WHERE ticketId="+reqResult[0]);
					break;
				}
			}
		}
		}
		catch (Exception e) {
			// TODO: handle exception
		System.out.println(e);
		}
	}

	private void ForManagerAndAssignedEmployee(String[] reqResult, Statement stmt, ResultSet rs) {//exe for normal,high<24
		// TODO Auto-generated method stub
		try {
			String SendToManager = "no";
			rs= stmt.executeQuery("SELECT proActiveAlerts.ticketId,proActiveAlerts.manager FROM csedatabase.proActiveAlerts");
			while(rs.next())
			{
				if(rs.getString(1).equalsIgnoreCase(reqResult[0]))
				{
					if(rs.getInt(2)==0)
					{
						PushDemo pd= new PushDemo();
						pd.mains(reqResult);
						SendToManager="yes";
						
						System.out.println("project completed");
						int i= stmt.executeUpdate("UPDATE proActiveAlerts SET manager=1 WHERE ticketId="+reqResult[0]);
						break;
					}
				}
				
			}
			String[] reqResultForManager=new String[reqResult.length];
			for(int i=0;i<reqResult.length;i++)
			{
				reqResultForManager[i]=reqResult[i];
			}
			reqResult[3]=reqResult[3].trim();
			if(SendToManager.equalsIgnoreCase("yes")) {
		    	rs=stmt.executeQuery("SELECT EmployeeID,ReportingManager FROM csedatabase.empdetails");
		        while(rs.next())
		        {
		        	if(rs.getString(1).equalsIgnoreCase(reqResult[3]))
		        	{
		        		for(int i=0;i<managersList.length;i++) {
		        			if(rs.getString(2).equalsIgnoreCase(managersList[i])) {
		        				reqResultForManager[3]=RespectivemanagerIds[i];
		        				PushDemo pd= new PushDemo();
								pd.mains(reqResultForManager);
		        			    System.out.println("manager is done");
		        			    System.out.println("\n\n\n");
		        			}
		        				
		        		}
		        		
		        	}
		        }
		    }
		}
			catch (Exception e) {
				// TODO: handle exception
			System.out.println(e);
			}
	}

}
