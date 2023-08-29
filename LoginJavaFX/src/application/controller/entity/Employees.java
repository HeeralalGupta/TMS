package application.controller.entity;

public class Employees {
	
	int id;
	String name;
	String Designation;
	String email;
	String mobile;
	public Employees() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Employees(int id, String name, String designation, String email, String mobile) {
		super();
		this.id = id;
		this.name = name;
		Designation = designation;
		this.email = email;
		this.mobile = mobile;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesignation() {
		return Designation;
	}
	public void setDesignation(String designation) {
		Designation = designation;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public String toString() {
		return "Employees [id=" + id + ", name=" + name + ", Designation=" + Designation + ", email=" + email
				+ ", mobile=" + mobile + "]";
	}
	

}
