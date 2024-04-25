package com.kafka;

public class Notification {
	private String filename;
	private String path;
	private String userId;
	private boolean shared;
	private String deptUsername;
	private String deptDisplayUsername;

	public Notification() {
		super();
	}

	public Notification(String userId) {
		super();
		this.userId = userId;
	}

	public Notification(String filename, String path, String userId, boolean shared, String deptUsername,
			String deptDisplayUsername) {
		super();
		this.filename = filename;
		this.path = path;
		this.userId = userId;
		this.shared = shared;
		this.deptUsername = deptUsername;
		this.deptDisplayUsername = deptDisplayUsername;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public String getDeptUsername() {
		return deptUsername;
	}

	public void setDeptUsername(String deptUsername) {
		this.deptUsername = deptUsername;
	}

	public String getDeptDisplayUsername() {
		return deptDisplayUsername;
	}

	public void setDeptDisplayUsername(String deptDisplayUsername) {
		this.deptDisplayUsername = deptDisplayUsername;
	}

	@Override
	public String toString() {
		return "Notification [filename=" + filename + ", path=" + path + ", userId=" + userId + ", shared=" + shared
				+ ", deptUsername=" + deptUsername + ", deptDisplayUsername=" + deptDisplayUsername + "]";
	}



	
}
