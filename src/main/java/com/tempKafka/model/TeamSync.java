//package com.tempKafka.model;
//
//import java.util.List;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//
//@Document(indexName = "teamsyncfirst", createIndex = true)
//public class TeamSync {
//	
//	@Id
//	private String id;
//	@Field
//	private List<String> username;
//	@Field
//	private String text;
//	@Field(name="fId")
//	private String fId;
//	@Field 
//	private String path;
//	@Field
//	private String fileName;
//	
//	public TeamSync() {
//		super();
//	}
//
//	
//	
//	public TeamSync(String id, List<String> username, String text, String fId, String path, String fileName) {
//		super();
//		this.id = id;
//		this.username = username;
//		this.text = text;
//		this.fId = fId;
//		this.path = path.toLowerCase().replaceAll("/", "_");
//		this.fileName = fileName;
//	}
//
//
//
//	public String getId() {
//		return id;
//	}
//
//
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//
//
//	public List<String> getUsername() {
//		return username;
//	}
//
//
//
//	public void setUsername(List<String> username) {
//		this.username = username;
//	}
//
//
//
//	public String getfId() {
//		return fId;
//	}
//
//
//
//	public void setfId(String fId) {
//		this.fId = fId;
//	}
//
//
//	public String getPath() {
//		return path;
//	}
//
//
//
//	public void setPath(String path) {
//		this.path = path;
//	}
//
//
//
//	public String getFileName() {
//		return fileName;
//	}
//
//
//
//	public void setFileName(String fileName) {
//		this.fileName = fileName;
//	}
//
//
//
//	public String getText() {
//		return text;
//	}
//
//	public void setText(String text) {
//		this.text = text;
//	}
//
//
//
//	@Override
//	public String toString() {
//		return "TeamSync [id=" + id + ", username=" + username + ", text=" + text + ", fId=" + fId + ", path=" + path
//				+ ", fileName=" + fileName + "]";
//	}
//	
//
//	
//
//}
