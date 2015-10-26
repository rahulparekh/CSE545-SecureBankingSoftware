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
	
	public SystemLog(LocalDateTime timeStamp, String employeeID, String action) {
		super();
		TimeStamp = timeStamp;
		this.employeeID = employeeID;
		Action = action;
	}

	@Id
	@NotNull
	@Column(name = "TimeStamp", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime TimeStamp;
	
	@NotNull
	@Size(min = 11, max = 11)
	@Column(name = "employeeID", nullable = false, length = 11, unique = true)
	private String employeeID;
	
	
	@NotNull
	@Size(min = 0, max = 100)
	@Column(name = "Action", nullable = false, length = 100)
	private String Action;

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

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

	@Override
	public String toString() {
		return "SystemLog [TimeStamp=" + TimeStamp + ", employeeID="
				+ employeeID + ", Action=" + Action + "]";
	}

	
}
