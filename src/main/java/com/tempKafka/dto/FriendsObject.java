package com.tempKafka.dto;

public class FriendsObject {
	
	String Name;
	String age;
	String id;
	Object image;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public FriendsObject(String name, String age, String id,Object image) {
		super();
		Name = name;
		this.age = age;
		this.id = id;
		this.image = image;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public FriendsObject(String name, String age) {
		super();
		Name = name;
		this.age = age;
	}
	public FriendsObject() {
		super();
	}
	public Object getImage() {
		return image;
	}
	public void setImage(Object image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "FriendsObject [Name=" + Name + ", age=" + age + ", id=" + id + ", image=" + image + "]";
	}


	

}
