//package com.tempKafka.model;
//
//import java.util.List;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//
//@Document(indexName = "crmfirst", createIndex = true)
//public class CRM {
//
//	@Id
//	private String id;
//	@Field
//	private String text;
//	@Field(name = "fId")
//	private String fId;
//
//	public CRM() {
//		super();
//	}
//
//	public CRM(String id, String text, String fId) {
//		super();
//		this.id = id;
//		this.text = text;
//		this.fId = fId;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getfId() {
//		return fId;
//	}
//
//	public void setfId(String fId) {
//		this.fId = fId;
//	}
//
//	public String getText() {
//		return text;
//	}
//
//	public void setText(String text) {
//		this.text = text;
//	}
//
//	@Override
//	public String toString() {
//		return "CRM [id=" + id + ", text=" + text + ", fId=" + fId + "]";
//	}
//
//}
