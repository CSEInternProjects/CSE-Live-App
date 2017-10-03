package PushNotePack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ForUfids {
	public static void main(String args[]) throws IOException
	{
		File f= new File("C:\\Users\\KH9331\\Desktop\\CSE_Engg-Tags.txt");
		FileReader fr= new FileReader(f);
		BufferedReader br= new BufferedReader(fr);
		boolean b=true;
		String u;
		String empTag[]= new String[50];
		String empId[]= new String[50];
		int i=0,j=0;
		while((u=br.readLine())!=null)
		{
			System.out.println(u);
			//u=br.readLine();
			if(j==1)
			{
				System.out.println("manp3 "+u);
				j=0;
			}
			
			if(u.indexOf("ony.com")!=-1)
			{   
				u=br.readLine();
				System.out.println("manp1 "+u);
			   empId[i]=u.trim();
				//b=false;
			}
				if(u.indexOf("Tag:")!=-1)
				   
				{
					u=br.readLine();
					System.out.println("manp2 "+u);
					//b=true;
					empTag[i]=u;
					i++;
				}
			   if(u.indexOf("Title")!=-1)
				   
				{
					j=1;
				   //b=true;
				
				}
			
			
		/*	else
			{
				if(u.indexOf("Tag")!=-1)
				{
					u=br.readLine();
					System.out.println("manp2 "+u);
					b=true;
				}
			}*/
		}
		for(i=0;i<empId.length;i++)
		{
			System.out.println(empId[i]+" len   "+empTag[i] );	
		}
	}
}
