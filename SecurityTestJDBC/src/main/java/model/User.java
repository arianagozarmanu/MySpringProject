package model;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int iduders;
	private String username;
	private String password;
	private boolean enabled;
	private String email;
	private int age;
	private java.util.Date lastOperationDate; 

	public User(){
	}
	
	public User(int iduders, String username, String password, boolean enabled, String email, int age, java.util.Date date) {
		this.iduders = iduders;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.email = email;
		this.age = age;
		this.lastOperationDate=date;
	}

	public java.util.Date getLastOperationDate() {
		return lastOperationDate;
	}

	public void setLastOperationDate(java.util.Date lastOperationDate) {
		this.lastOperationDate = lastOperationDate;
	}

	public int getIduders() {
		return iduders;
	}
	public void setIduders(int iduders) {
		this.iduders = iduders;
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
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
