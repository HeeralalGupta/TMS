package application.controller.entity;



public class DataObject {
	
	String id;
	String taskName;
	String estimateDate;
	String status;
	String assignTo;
	String currentOwner;
	String owner;
	String assignDate;
	String priority;
	String description;
	String assignId;
	
	
	public DataObject() {
		super();
		// TODO Auto-generated constructor stub
	}


	public DataObject(String id, String taskName, String estimateDate, String status, String assignTo,
			String currentOwner, String owner, String assignDate, String priority, String description,
			String assignId) {
		super();
		this.id = id;
		this.taskName = taskName;
		this.estimateDate = estimateDate;
		this.status = status;
		this.assignTo = assignTo;
		this.currentOwner = currentOwner;
		this.owner = owner;
		this.assignDate = assignDate;
		this.priority = priority;
		this.description = description;
		this.assignId = assignId;
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


	public String getAssignDate() {
		return assignDate;
	}


	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}


	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getAssignId() {
		return assignId;
	}


	public void setAssignId(String assignId) {
		this.assignId = assignId;
	}


	@Override
	public String toString() {
		return "DataObject [id=" + id + ", taskName=" + taskName + ", estimateDate=" + estimateDate + ", status="
				+ status + ", assignTo=" + assignTo + ", currentOwner=" + currentOwner + ", owner=" + owner
				+ ", assignDate=" + assignDate + ", priority=" + priority + ", description=" + description
				+ ", assignId=" + assignId + "]";
	}
	
	
	
}