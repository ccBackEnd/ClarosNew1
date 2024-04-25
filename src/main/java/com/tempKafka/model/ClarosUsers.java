package com.tempKafka.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.tempKafka.modelMySql.ClarosUsersJ;

@Document(indexName = "crime_user", createIndex = true)
public class ClarosUsers {

	@Id
	private String id;
	@Field(name = "Name")
	private String name;
	// @Field(type = FieldType.Keyword, name = "userid")
	private String userid;
	@Field(type = FieldType.Integer, name = "age")
	private long age;
	private String description;
	private Object image;
	@Field(type = FieldType.Object, name = "information")
	private Object information;
	private Boolean monitored;
	private String score;
	private List<String> friendsID;
	private List<String> enemyID;
	private String dob;
	private List<String> monitoredBy; // rolenames

	public ClarosUsers() {
		super();
	}

	public ClarosUsers(ClarosUsersJ a) {
		super();
		this.name = a.getName();
		this.userid = a.getUserid();
		this.age = a.getAge();
		this.description = a.getDescription();
		this.monitored = a.getMonitored();
		this.score = a.getScore();
		this.dob = a.getDob();
		this.monitoredBy=new ArrayList<>();
		this.friendsID= new ArrayList<>();
		this.enemyID = new ArrayList<>();
	}

	public ClarosUsers(String id, String name, String userid, long age, String description, Object image,
			Object information, Boolean monitored, String score) {
		super();
		this.id = id;
		this.name = name;
		this.userid = userid;
		this.age = age;
		this.description = description;
		this.image = image;
		this.information = information;
		this.monitored = monitored;
		this.score = score;
	}

	public ClarosUsers(String name, String userid, String description, Object information, boolean monitored,
			String score) {
		this.name = name;
		this.userid = userid;
		this.description = description;
		this.information = information;
		this.monitored = monitored;
		this.score = score;
	}

	//
	public ClarosUsers(String name, String userid) {
		this.name = name;
		this.userid = userid;
//        this.description = description;

	}

	public ClarosUsers(String id, String name, String userid, long age, String description, Object image,
			Object information, Boolean monitored, String score, List<String> friendsID, String dob) {
		super();
		this.id = id;
		this.name = name;
		this.userid = userid;
		this.age = age;
		this.description = description;
		this.image = image;
		this.information = information;
		this.monitored = monitored;
		this.score = score;
		this.friendsID = friendsID;
		this.dob = dob;
	}

	public ClarosUsers(String id, String name, String userid, long age, String description, Object image,
			Object information, Boolean monitored, String score, List<String> friendsID, List<String> enemyID,
			String dob) {
		super();
		this.id = id;
		this.name = name;
		this.userid = userid;
		this.age = age;
		this.description = description;
		this.image = image;
		this.information = information;
		this.monitored = monitored;
		this.score = score;
		this.friendsID = friendsID;
		this.enemyID = enemyID;
		this.dob = dob;
	}

	public ClarosUsers(String id, String name, String userid, long age, String description, Object image,
			Object information, Boolean monitored, String score, List<String> friendsID, List<String> enemyID,
			String dob, List<String> monitoredBy) {
		super();
		this.id = id;
		this.name = name;
		this.userid = userid;
		this.age = age;
		this.description = description;
		this.image = image;
		this.information = information;
		this.monitored = monitored;
		this.score = score;
		this.friendsID = friendsID;
		this.enemyID = enemyID;
		this.dob = dob;
		this.monitoredBy = monitoredBy;
	}

	public List<String> getMonitoredBy() {
		return monitoredBy;
	}

	public void setMonitoredBy(List<String> monitoredBy) {
		this.monitoredBy = monitoredBy;
	}

	public List<String> getEnemyID() {
		return enemyID;
	}

	public void setEnemyID(List<String> enemyID) {
		this.enemyID = enemyID;
	}

	public List<String> getFriendsID() {
		return friendsID;
	}

	public void setFriendsID(List<String> friendsID) {
		this.friendsID = friendsID;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getImage() {
		return image;
	}

	public void setImage(Object image) {
		this.image = image;
	}

	public Object getInformation() {
		return information;
	}

	public void setInformation(Object information) {
		this.information = information;
	}

	public Boolean getMonitored() {
		return monitored;
	}

	public void setMonitored(Boolean monitored) {
		this.monitored = monitored;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "ClarosUsers [id=" + id + ", name=" + name + ", userid=" + userid + ", age=" + age + ", description="
				+ description + ", image=" + image + ", information=" + information + ", monitored=" + monitored
				+ ", score=" + score + ", friendsID=" + friendsID + ", enemyID=" + enemyID + ", dob=" + dob
				+ ", monitoredBy=" + monitoredBy + "]";
	}

}
