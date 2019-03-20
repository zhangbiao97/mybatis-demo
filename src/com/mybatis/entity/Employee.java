package com.mybatis.entity;

import org.apache.ibatis.type.Alias;

@Alias("emp")
public class Employee {

	private Integer id;
	private String lastName;
	private Integer gender;
	private String email;
	private Department dept;

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employee(String lastName, Integer gender, String email, Department dept) {
		super();
		this.lastName = lastName;
		this.gender = gender;
		this.email = email;
		this.dept = dept;
	}

	public Employee(Integer id, String lastName, Integer gender, String email) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.gender = gender;
		this.email = email;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", lastName=" + lastName + ", gender=" + gender + ", email=" + email + ", dept="
				+ dept + "]";
	}

}
