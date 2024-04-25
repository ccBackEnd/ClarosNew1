package com.tempKafka.dto;

public class Node {

	public String id;
	public String color;
	public int size;

	public Node(String id) {
		super();
		this.id = id;
		this.color="green";
		this.size=400;		
	}

	public Node(String id, String color, int size) {
		super();
		this.id = id;
		this.color = color;
		this.size = size;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", color=" + color + ", size=" + size + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
	
}
