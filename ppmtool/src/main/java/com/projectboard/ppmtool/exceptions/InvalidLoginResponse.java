package com.projectboard.ppmtool.exceptions;



public class InvalidLoginResponse {
	private String username;
	private String password;
	
	public InvalidLoginResponse() {
		// TODO Auto-generated constructor stub
		this.username = "Invalid UserName";
		this.password = "Invalid Password";
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
	
	
}
