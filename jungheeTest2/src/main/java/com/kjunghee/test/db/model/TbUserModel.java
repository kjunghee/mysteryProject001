package com.kjunghee.test.db.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TbUserModel {
	private Long index_id;
	private String name;
	private String department;
	private String position;
	private String gender;
	private String hireDate;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	private String phoneNumber;
	
	private String departmentCode;
	private String positionCode;
	private String genderCode;
	
	public Long getIndex_id() {
		return index_id;
	}

	public void setIndex_id(Long index_id) {
		this.index_id = index_id;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = df.format(hireDate);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

}
