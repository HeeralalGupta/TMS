package application.controller;

import java.sql.Date;

public class DataObject {
	
	String id;
	String taskName;
	String estimateDate;
	String status;
	String assignTo;
	String currentOwner;
	String owner;
	public DataObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DataObject(String id, String taskName, String estimateDate, String status, String assignTo,
			String currentOwner, String owner) {
		super();
		this.id = id;
		this.taskName = taskName;
		this.estimateDate = estimateDate;
		this.status = status;
		this.assignTo = assignTo;
		this.currentOwner = currentOwner;
		this.owner = owner;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getEstimateDate() {
		return estimateDate;
	}
	public void setEstimateDate(String estimateDate) {
		this.estimateDate = estimateDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAssignTo() {
		return assignTo;
	}
	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}
	public String getCurrentOwner() {
		return currentOwner;
	}
	public void setCurrentOwner(String currentOwner) {
		this.currentOwner = currentOwner;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	@Override
	public String toString() {
		return "DataObject [id=" + id + ", taskName=" + taskName + ", estimateDate=" + estimateDate + ", status="
				+ status + ", assignTo=" + assignTo + ", currentOwner=" + currentOwner + ", owner=" + owner + "]";
	}
	
	
}