package com.tempKafka.model;

import org.springframework.data.elasticsearch.annotations.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

@Document(indexName = "alerts_claros", createIndex = true)
public class Alerts {
	@Id
	private String id;
	private String priority;
	private String sourceofalert;
	private String description;
	private Object date;
	private String dispDate;
	private Long ldate;
	String name;
	List<String> userid; //
    List<String> usernames;
    List<AttachmentFile> fileobjects;
    String gis;
    String type;

	// for csv data
	public Alerts(String userid, String name, String priority, String sourceofalert, String description,
			String dispDate, Object date, Long ldate) {
		// TODO Auto-generated constructor stub
		super();
		this.priority = priority;
		this.sourceofalert = sourceofalert;
		this.description = description;
		this.date = date;
		this.dispDate = dispDate;
		this.ldate = ldate;
		this.name = name;
		this.userid = new ArrayList<>(Arrays.asList(userid));
	}
	
	

	public Alerts(String id, String priority, String sourceofalert, String description, Object date, String dispDate,
			Long ldate, String name, List<String> userid) {
		super();
		this.id = id;
		this.priority = priority;
		this.sourceofalert = sourceofalert;
		this.description = description;
		this.date = date;
		this.dispDate = dispDate;
		this.ldate = ldate;
		this.name = name;
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getUserid() {
		return userid;
	}

	public void setUserid(List<String> userid) {
		this.userid = userid;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Long getLdate() {
		return ldate;
	}

	public void setLdate(Long ldate) {
		this.ldate = ldate;
	}

	public String getDispDate() {
		return dispDate;
	}

	public void setDispDate(String dispDate) {
		this.dispDate = dispDate;
	}

	public Alerts() {
		super();
	}

	public Alerts(String id, String priority, String sourceofalert, String description, Date date, String dispDate) {
		super();
		this.id = id;
		this.priority = priority;
		this.sourceofalert = sourceofalert;
		this.description = description;
		this.date = date;
		this.dispDate = dispDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSourceofalert() {
		return sourceofalert;
	}

	public void setSourceofalert(String sourceofalert) {
		this.sourceofalert = sourceofalert;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getDate() {
		return date;
	}

	public Alerts(String id, String priority, String sourceofalert, String description, Date date) {
		super();
		this.id = id;
		this.priority = priority;
		this.sourceofalert = sourceofalert;
		this.description = description;
		this.date = date;
	}

	public Alerts(String priority, String sourceofalert, String description, Date formattedDate, String dispDate,
			long ldate,List<String> poi) {
		// TODO Auto-generated constructor stub
		this.priority = priority;
		this.sourceofalert = sourceofalert;
		this.description = description;
		this.date = formattedDate;
		this.dispDate = dispDate;
		this.ldate = ldate;
		this.userid = poi;

	}

	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}

	public void setDate(Object date) {
		this.date = date;
	}



	public List<AttachmentFile> getFileobjects() {
		return fileobjects;
	}



	public void setFileobjects(List<AttachmentFile> fileobjects) {
		this.fileobjects = fileobjects;
	}



	public String getGis() {
		return gis;
	}



	public void setGis(String gis) {
		this.gis = gis;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	@Override
	public String toString() {
		return "Alerts [id=" + id + ", priority=" + priority + ", sourceofalert=" + sourceofalert + ", description="
				+ description + ", date=" + date + ", dispDate=" + dispDate + ", ldate=" + ldate + ", name=" + name
				+ ", userid=" + userid + ", usernames=" + usernames + ", fileobjects=" + fileobjects + ", gis=" + gis
				+ ", type=" + type + "]";
	}




	

	

}
