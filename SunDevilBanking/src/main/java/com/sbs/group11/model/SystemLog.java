package com.sbs.group11.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name ="SystemLog")

public class SystemLog {
	
	public SystemLog()
	{
		
	}
	
	public SystemLog(LocalDateTime timeStamp, String firstName,
			String userType, String action) {
		super();
		TimeStamp = timeStamp;
		this.firstName = firstName;
		this.userType = userType;
		Action = action;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Id
	@NotNull
	@Column(name = "TimeStamp", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime TimeStamp;
	
	@NotNull
	@Size(min = 2, max = 35)
	@Column(name = "FirstName", nullable = false, length = 35)
	private String firstName;
	
	@Size(min = 5, max = 8)
	@Column(name = "UserType", nullable = true, length = 8)
	private String userType;
	
	@NotNull
	@Size(min = 0, max = 100)
	@Column(name = "Action", nullable = false, length = 100)
	private String Action;

	

	public LocalDateTime getTimeStamp() {
		return TimeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		TimeStamp = timeStamp;
	}

	public String getAction() {
		return Action;
	}

	public void setAction(String action) {
		Action = action;
	}

	
	
}
