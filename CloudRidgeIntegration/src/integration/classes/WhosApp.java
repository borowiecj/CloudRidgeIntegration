package integration.classes;
import javax.persistence.*;  

 
public class WhosApp implements java.io.Serializable {
	
	//Bean attributes
	private int employee_id;
	private String first_name;
	private String last_name;
	private String state;
	private int zip;
	private String sex;
	private String emploment_status;
	private String position_name;
	private String department_name;
	private int salary;
	private String hire_date;
	
	//Non SQL
	private String fullName;
	
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getHire_date() {
		return hire_date;
	}
	public void setHire_date(String hire_date) {
		this.hire_date = hire_date;
	}

	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEmploment_status() {
		return emploment_status;
	}
	public void setEmploment_status(String emploment_status) {
		this.emploment_status = emploment_status;
	}
	public String getPosition_name() {
		return position_name;
	}
	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}
	public String getDepartment_name() {
		return department_name;
	}
	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		salary = salary;
	}

	public String getFullName() 
	{
		String s = this.first_name;
		
		if(!this.last_name.equals(null))
		{
			s.concat(" ");
			s.concat(this.last_name);
		}
		
		return s;
	}
}
