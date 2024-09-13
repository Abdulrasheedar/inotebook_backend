package com.inotebook.inotebook.entity;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import jakarta.validation.constraints.NotNull;


@Document(collection = "users")

public class User {
	
	@Id
	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;
	

	//@NonNull
	@NotNull(message = "Name cannot be null")
	@Size(min = 3, message = "Name must be at least 3 characters long")
	private String name;
	
	@Indexed(unique = true)
	//@NonNull
	@NotNull(message = "Email cannot be null")
	@Email(message = "Email should be valid")
	private String email;
	//@NonNull
	@NotNull(message = "Password cannot be null")
	@Size(min = 6, message = "Password must be at least 6 characters long")
	private String password;
	
	private LocalDate date;
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
}
