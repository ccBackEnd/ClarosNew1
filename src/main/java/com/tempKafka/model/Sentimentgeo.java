//package com.tempKafka.model;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
//@Document(indexName = "proccessed_sentiment_geolocation", createIndex = true)
//public class Sentimentgeo {
//
//	@Id
//	private String id;
//	@Field(type = FieldType.Keyword, name = "Name")
//	private String name;
//	@Field(type = FieldType.Keyword, name = "userid")
//	private String userid;
//	
//	
//	
//	public Sentimentgeo() {
//		super();
//	}
//	public Sentimentgeo(String id, String name, String userid) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.userid = userid;
//	}
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getUserid() {
//		return userid;
//	}
//	public void setUserid(String userid) {
//		this.userid = userid;
//	}
//	@Override
//	public String toString() {
//		return "Sentimentgeo [id=" + id + ", name=" + name + ", userid=" + userid + "]";
//	}
//	
//	
//	
//	
//}
