package com.dto;

public class UserDTO {
	
	private String name;
	private String gender;
	private String city;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public UserDTO(String name, String gender, String city) {
		super();
		this.name = name;
		this.gender = gender;
		this.city = city;
	}
	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}
