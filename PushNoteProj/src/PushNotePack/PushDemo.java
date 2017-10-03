package PushNotePack;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;



public class PushDemo {
	
/*	public static void main(String args[]) throws IOException
	{
		String arg[]= new String[3];
				//args[] = {"Ankit","Bohra","Xyz"};
				arg[0]="title5";
				arg[1]="dummy";
				arg[2]="message5";
		PushDemo pd= new PushDemo();
		pd.mains(arg);

	}
	
*/	
	
	public void mains(String[] args) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		triggerPush(args);
		//triggerPush();

	}
	
	
	public static String getClaimsToken() throws ClientProtocolException,IOException {
		String baseUri = "http://productsupport.konylabs.net";
		String userId = "cseinterns@kony.com";
		String password = "Kony$$123";
		String claimsToken = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(baseUri + "/authService/accounts/login");
		List<NameValuePair> nvps = new ArrayList < NameValuePair > ();
		nvps.add(new BasicNameValuePair("userid", userId));
		nvps.add(new BasicNameValuePair("password", password));
		post.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse httpResponse = httpclient.execute(post);
		String response = EntityUtils.toString(httpResponse.getEntity());
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonRootNode = objectMapper.readTree(response);
		Iterator< Entry < String,
		JsonNode >> jsonFieldsIter = jsonRootNode.fields();
		while (jsonFieldsIter.hasNext()) {
			Entry< String,
			JsonNode > field = jsonFieldsIter.next();
			if (field.getKey().equals("claims_token")) {
				claimsToken = field.getValue().get("value").asText();
			}

		}
		return claimsToken;

	}
	
	
	public static void triggerPush(String[] args) throws ClientProtocolException,
	IOException {
		
		//String[] empTags=new String[]{"",""};
		String[] subscriberArry=new String[] {args[3]};// you should keep all subscribers ufid list here. 
			//{1123452};
		URL url = new URL("https://productsupport.konylabs.net/kpns/api/v1/subscribers?start=0&pageSize=5");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
		con.setRequestProperty("Content-Type", "application/json");
		String claimesToken=getClaimsToken();
		con.setRequestProperty("X-Kony-Authorization",claimesToken);
		con.setRequestMethod("GET");
		
		//Scanner sc1 = new Scanner(con.getInputStream());
		//while(sc1.hasNext())
		//	System.out.println(sc1.nextLine());
		//RealtriggerPush("holy");
		Gson gson2= new Gson();
		Subscribers subscribers= gson2.fromJson(new InputStreamReader(con.getInputStream()), Subscribers.class);
		try
		{
			for(Subscriber sub:subscribers.getSubscribers())
			{
				if(sub.getUfid().equals(subscriberArry[0]))
				{
					System.out.println(" ufid "+sub.getUfid()+" ksid "+sub.getKsid());
					RealtriggerPush(sub.getKsid(),args);// this imp for execution
					//RealtriggerPush(sub.getKsid());
					System.out.println("push notification sent!!");
					
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		/*Subscriber subscribers= gson2.fromJson(new InputStreamReader(con.getInputStream()),Subscriber.class);
				*/
	}
		

	public static void RealtriggerPush(String ksId,String[] args) throws ClientProtocolException,
	IOException {
		String test="now test";
		//String args[]= new String[3];
		//args[] = {"Ankit","Bohra","Xyz"};
		//args[0]="title5";
		//args[1]="dummy";
		//args[2]="message5";
		String pushPayload ="{\r\n" + 
		"  \"messageRequest\" : {\r\n" + 
		"    \"appId\" : \"0c07f417-42ae-4dd8-a738-672872afbf06\",\r\n" + 
		"    \"global\" : { },\r\n" + 
		"    \"messages\" : {\r\n" + 
		"      \"message\" : {\r\n" + 
		"        \"content\" : {\r\n" + 
		"          \"priorityService\" : \"true\",\r\n" + 
		"          \"data\" : \""+args[2]+"\"\n"+ 
		"          \"mimeType\" : \"text/plain\"\r\n" + 
		"        },\r\n" + 
		"        \"overrideMessageId\" : 0,\r\n" + 
		"        \"startTimestamp\" : \"0\",\r\n" + 
		"        \"expiryTimestamp\" : \"0\",\r\n" + 
		"        \"subscribers\" : {\r\n" + 
		"          \"subscriber\" : [ {\r\n" + 
		"            \"ksid\" : "+ksId+"\r\n" + 
		"          } ]\r\n" + 
		"        },\r\n" + 
		"        \"platformSpecificProps\" : {\r\n" + 
		"          \"title\" : \""+args[0]+"\",\r\n" + 
		"          \"iphone\" : {\r\n" + 
		"            \"title\" : \""+args[0]+"\",\r\n" + 
		"            \"customData\" : { }\r\n" + 
		"          },\r\n" + 
		"          \"blackberry\" : { },\r\n" + 
		"          \"android\" : {\r\n" + 
		"            \"title\" : \""+args[0]+"\"\r\n" + 
		"          },\r\n" + 
		"          \"jpush\" : {\r\n" + 
		"            \"key\" : [ {\r\n" + 
		"              \"name\" : \"title\",\r\n" + 
		"              \"value\" : \""+args[0]+"\"\r\n" + 
		"            } ]\r\n" + 
		"          },\r\n" + 
		"          \"windows\" : {\r\n" + 
		"            \"notificationType\" : \"TOAST\",\r\n" + 
		"            \"text1\" : \""+args[0]+"\",\r\n" + 
		"            \"text2\" : \"lols\",\r\n" + 
		"            \"params\" : { }\r\n" + 
		"          },\r\n" + 
		"          \"wns\" : {\r\n" + 
		"            \"notificationType\" : \"TOAST\",\r\n" + 
		"            \"text1\" : \""+args[0]+"\",\r\n" + 
		"            \"text2\" : \"lols\",\r\n" + 
		"            \"params\" : { },\r\n" + 
		"            \"image\" : { },\r\n" + 
		"            \"text\" : { }\r\n" + 
		"          }\r\n" + 
		"        },\r\n" + 
		"        \"type\" : \"PUSH\"\r\n" + 
		"      }\r\n" + 
		"    }\r\n" + 
		"  }\r\n" + 
		"}";
String baseUri="https://productsupport.konylabs.net";
CloseableHttpClient httpclient = HttpClients.createDefault();
HttpPost post = new HttpPost(baseUri +     "/kpns/api/v1/messages/push");
post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
String claimsToken=getClaimsToken();
post.setHeader("X-Kony-Authorization", claimsToken);
post.setEntity(new StringEntity(pushPayload));
CloseableHttpResponse httpResponse = httpclient.execute(post);
String response = EntityUtils.toString(httpResponse.getEntity());
System.out.println(response);
	}
		
}

