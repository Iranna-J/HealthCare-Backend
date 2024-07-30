package com.HealthCareBack.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonManagedReference
	private User user;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="address_id", referencedColumnName = "id")
	@JsonManagedReference
	private Address address;
	
	private int date_of_birth;
	private String gender;
	private String age;
	private String bloodgroup;
	private long emergency_contact;
	public long getid() {
		return id;
	}
	public void setid(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public int getDate_of_birth() {
		return date_of_birth;
	}
	public void setDate_of_birth(int date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getBloodgroup() {
		return bloodgroup;
	}
	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}
	public long getEmergency_contact() {
		return emergency_contact;
	}
	public void setEmergency_contact(long emergency_contact) {
		this.emergency_contact = emergency_contact;
	}
	

}
