package com.tempKafka.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "osinttt", createIndex = true)
public class Sntt {

	@Id
	String id;
	
	String address;
	String description;
	String type;
	String typeofcrime;
	private Object date;
	
    Long datel;
    
    List<AttachmentFile> fileobjects ;
    List<String> Userids;
    List<String> userNames;
   
			
	public Sntt() {
		super();
	}
	public Sntt(String address, String description, String type, String typeofcrime, Object date,Long datel) {
		super();
		this.address = address;
		this.description = description;
		this.type = type;
		this.typeofcrime = typeofcrime;
		this.date = date;
		this.datel = datel;
		
	}
	public Sntt(String id, String address, String description, String type, String typeofcrime, Object date, Long datel,
			List<AttachmentFile> fileobjects, List<String> userids) {
		super();
		this.id = id;
		this.address = address;
		this.description = description;
		this.type = type;
		this.typeofcrime = typeofcrime;
		this.date = date;
		this.datel = datel;
		this.fileobjects = fileobjects;
		Userids = userids;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeofcrime() {
		return typeofcrime;
	}
	public void setTypeofcrime(String typeofcrime) {
		this.typeofcrime = typeofcrime;
	}
	public Object getDate() {
		return date;
	}
	public void setDate(Object date) {
		this.date = date;
	}
	public Long getDatel() {
		return datel;
	}
	public void setDatel(Long datel) {
		this.datel = datel;
	}
	public List<AttachmentFile> getFileobjects() {
		return fileobjects;
	}
	public void setFileobjects(List<AttachmentFile> fileobjects) {
		this.fileobjects = fileobjects;
	}
	public List<String> getUserids() {
		return Userids;
	}
	public void setUserids(List<String> userids) {
		Userids = userids;
	}
	
	public List<String> getUserNames() {
		return userNames;
	}
	public void setUserNames(List<String> userNames) {
		this.userNames = userNames;
	}
	@Override
	public String toString() {
		return "Sntt [id=" + id + ", address=" + address + ", description=" + description + ", type=" + type
				+ ", typeofcrime=" + typeofcrime + ", date=" + date + ", datel=" + datel + ", fileobjects="
				+ fileobjects + ", Userids=" + Userids + ", userNames=" + userNames + "]";
	}
	
	
	
	

}
