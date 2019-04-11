package integration.classes;
import java.util.ArrayList;

import javax.persistence.*;  

 
public class FaceBike implements java.io.Serializable {
	
	//Bean attributes
	private String name;
	private String email;
	private int id;
	private String department;
	private double salary;
	private String country;
	private String createdAt;
	private String dateOfHire;
	
	//Non SQL 
	private String FirstName;
	private String LastName;
	

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}  
	
	public String getDateOfHire() {
		return createdAt;
	}

	public void setDateOfHire(String dateOfHire) {
		this.dateOfHire = dateOfHire;
	} 
	
	public String getFirstName()
	{
		if(!this.name.equals(null)) 
		{ 
			return this.name.split(" ")[0];
		}
		
		return null;
	}
	
	public String getLastName()
	{
		if(!this.name.equals(null)) 
		{ 
			String[] nameSplit = this.name.split(" ");
			
			return nameSplit[nameSplit.length - 1];
		}
		
		return null;
	}

}
