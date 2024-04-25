package com.tempKafka.modelMySql;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tempKafka.model.ClarosUsers;


@Entity
@Table(name = "calros_user")
public class ClarosUsersJ {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	// @Field(type = FieldType.Keyword, name = "userid")
	private String userid;
	private long age;
	private String description;

	private Boolean monitored;
	private String score;
	private String dob;

	private String friendsID;
	private String enemyID;

	public ClarosUsersJ() {
		super();
	}

	public ClarosUsersJ(ClarosUsers a) {
		super();

		this.name = a.getName();
		this.userid = a.getUserid();
		this.age = a.getAge();
		this.description = a.getDescription();

		this.monitored = a.getMonitored();
		this.score = a.getScore();
        if(a.getDob()!=null)
		this.dob = a.getDob();
		if(a.getEnemyID()!=null)
		this.enemyID = a.getEnemyID().stream().collect(Collectors.joining(", "));
		if(a.getFriendsID()!=null)
		this.friendsID = a.getFriendsID().stream().collect(Collectors.joining(", "));

	}

	public ClarosUsersJ(Long id, String name, String userid, long age, String description, Boolean monitored,
			String score) {
		super();
		this.id = id;
		this.name = name;
		this.userid = userid;
		this.age = age;
		this.description = description;
		this.monitored = monitored;
		this.score = score;
	}

	public ClarosUsersJ(String name, String userid, String description, boolean monitored, String score) {
		this.name = name;
		this.userid = userid;
		this.description = description;
		this.monitored = monitored;
		this.score = score;
	}

	//
	public ClarosUsersJ(String name, String userid) {
		this.name = name;
		this.userid = userid;
//        this.description = description;

	}

	public ClarosUsersJ(Long id1, String name1, String userid1, long age1, String description1, Boolean monitored1,
			String score1, String dob1) {
		super();
		this.id = id1;
		this.name = name1;
		this.userid = userid1;
		this.age = age1;
		this.description = description1;
		this.monitored = monitored1;
		this.score = score1;
		this.dob = dob1;
	}

	public ClarosUsersJ(Long id, String name, String userid, long age, String description, String score, String dob) {
		super();
		this.id = id;
		this.name = name;
		this.userid = userid;
		this.age = age;
		this.description = description;
		this.score = score;
		this.dob = dob;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	
	public Boolean getMonitored() {
		return monitored;
	}

	public void setMonitored(Boolean monitored) {
		this.monitored = monitored;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	
	@Override
	public String toString() {
		return "ClarosUsersJ [id1=" + id + ", name1=" + name + ", userid1=" + userid + ", age1=" + age
				+ ", description1=" + description + ", monitored1=" + monitored + ", score1=" + score + ", dob1=" + dob
				+ ", friendsID=" + friendsID + ", enemyID=" + enemyID + "]";
	}

}
