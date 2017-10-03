package PushNotePack1;

import java.util.List;
public class Ticket {
	
	String id;
	String updated_at;
	String created_at;
	String assignee_id;
	public String getAssignee_id() {
		return assignee_id;
	}
	public void setAssignee_id(String assignee_id) {
		this.assignee_id = assignee_id;
	}

	String priority;
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	String subject;
	List<Cust_field> custom_fields;
	public List<Cust_field> getCust() {
		return custom_fields;
	}
	public void setCust(List<Cust_field> cust) {//arry of json obj in json object
		this.custom_fields = cust;
	}

	Via via;
	public Via getVia() {  //json object in json object
		return via;
	}
	public void setVia(Via via) {
		this.via = via;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public class Via
	{
	String channel;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}	
  }

public class Cust_field
{
String id;
public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

String value;

public String getValue() {
	return value;
}

public void setValue(String value) {
	this.value = value;
}	
}
}

