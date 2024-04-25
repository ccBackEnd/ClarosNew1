package com.tempKafka.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import com.tempKafka.dto.*;
@Document(indexName = "crime_location", createIndex = true)
public class Crime {

	@Id
	private String id;
//	@Field(type = FieldType.Geo)
	@GeoPointField
	private GeoPoint location;
	@Field(name = "userid")
	private String userId;
	@Field(name = "Name")
	private String name;
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS")
	private Date time;
	
	private Date longTime;
	public Crime(GeoPoint location, String userId, String name, String displayTime, Date time) {
		super();
		this.location = location;
		this.userId = userId;
		this.name = name;
		this.time = time;
		this.longTime = time;
	}
	
	public Crime() {
		super();
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GeoPoint getLocation() {
		return location;
	}

	public void setLocation(GeoPoint location) {
		this.location = location;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public Date getLongTime() {
		return longTime;
	}

	public void setLongTime(Date longTime) {
		this.longTime = longTime;
	}

	public Date getTime() {
		return longTime;
	}

	public void setTime(Date time) {
		this.longTime = time;
	}

	@Override
	public String toString() {
		return "Crime [id=" + id + ", location=" + location + ", userId=" + userId + ", name=" + name + ", time=" + time
				+ ", longTime=" + longTime + "]";
	}
	
	
		
	
	
	
	
}
