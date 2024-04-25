package com.tempKafka.model;

import java.util.List;
import com.tempKafka.dto.FriendsObject;

public class SuspectIframe {

	private String Name;
	private List<FriendsObject> Friends;
	private String mapUrl;
	private String description;
	private Object information;

	public Object getInformation() {
		return information;
	}

	public void setInformation(Object information) {
		this.information = information;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SuspectIframe(String name, List<FriendsObject> friends, String mapUrl) {
		super();
		Name = name;
		Friends = friends;
		this.mapUrl = mapUrl;
	}

	public List<FriendsObject> getFriends() {
		return Friends;
	}

	public void setFriends(List<FriendsObject> friends) {
		Friends = friends;
	}

	public SuspectIframe() {
		super();
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getMapUrl() {
		return mapUrl;
	}

	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}

	@Override
	public String toString() {
		return "SuspectIframe [Name=" + Name + ", Friends=" + Friends + ", mapUrl=" + mapUrl + ", description="
				+ description + ", information=" + information + "]";
	}

}
