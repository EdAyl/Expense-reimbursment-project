package com.ed.models;

public class Employee {
	private int accountID;
	private String fName;
	private String lName;
	private String username;
	private String password;
	private boolean isManager;
	public boolean loggedIn;
	
	public Employee() {
		super();
	}
	
	public Employee(int accountID, String fName, String lName, String username, String password, boolean isManager) {
		this.accountID = accountID;
		this.fName = fName;
		this.lName = lName;
		this.username = username;
		this.password = password;
		this.isManager = isManager;
		this.loggedIn = false;
	}
	public boolean login(String password) {
		if (this.password.equals(password)) {
			loggedIn = true;
			return true;
		}
		return false;
	}
	public void logout() {
		loggedIn = false;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}
}
