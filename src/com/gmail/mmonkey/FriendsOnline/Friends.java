package com.gmail.mmonkey.FriendsOnline;

import java.io.Serializable;
import java.util.ArrayList;

public class Friends implements Serializable {

	private static final long serialVersionUID = 5473103934761478397L;
	
	private String name;
	private ArrayList<String> notificationList = new ArrayList<String>();
	
	public Friends(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	public ArrayList<String> getNotificationList() {
		return this.notificationList;
	}
	public void addNotification(String name) {
		this.notificationList.add(name);
	}
	public void removeNotification(String name) {
		this.notificationList.remove(name);
	}
}
