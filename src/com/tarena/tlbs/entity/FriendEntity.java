package com.tarena.tlbs.entity;

import java.io.Serializable;

public class FriendEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName; // 用户名
	private String name; // 昵称
	private String group; // 分组
	
	public FriendEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public FriendEntity(String userName, String name, String group) {
		super();
		this.userName = userName;
		this.name = name;
		this.group = group;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
