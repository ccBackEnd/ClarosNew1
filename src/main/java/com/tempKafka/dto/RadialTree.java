package com.tempKafka.dto;

public class RadialTree {
	
	String id;
	String name;
	String age;
	String parentId;
	Object image;
	String color;//red=enemy // green=friend
//	{
//        'id': 'parent', 'name': 'Maria Anders', 'role': 'Managing Director'
//    },
//    {
//        'id': 1, 'name': 'Ana Trujillo', 'role': 'Project Manager',
//        'parentId': 'parent'
//    }
	public RadialTree(String id, String name, String age, String parentId,Object image,String color) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.parentId = parentId;
		this.image= image;
		if(color.equals("true")) this.color="green";
		else this.color="red";
	}
	
	


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public Object getImage() {
		return image;
	}


	public void setImage(Object image) {
		this.image = image;
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
	
	public String getAge() {
		return age;
	}




	public void setAge(String age) {
		this.age = age;
	}




	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	@Override
	public String toString() {
		return "RadialTree [id=" + id + ", name=" + name + ", age=" + age + ", parentId=" + parentId + ", image="
				+ image + ", color=" + color + "]";
	}
	
	
}
