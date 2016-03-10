package com.tarena.tlbs.entity;

import java.io.Serializable;

public class UpdateEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;
	private String version;
	private String changeLog;
	private String apkUrl;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getChangeLog() {
		return changeLog;
	}
	public void setChangLog(String changLog) {
		this.changeLog = changLog;
	}
	public String getApkUrl() {
		return apkUrl;
	}
	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}
	public UpdateEntity(String status, String version, String changLog, String apkUrl) {
		super();
		this.status = status;
		this.version = version;
		this.changeLog = changLog;
		this.apkUrl = apkUrl;
	}
	
	public UpdateEntity() {
		// TODO Auto-generated constructor stub
	}
}
