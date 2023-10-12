package com.sessionApp.demo.entity;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Employee {
	
	private Integer empId;
	private String name;
	private String age;
	private String email;
	private String phNo;
	private String address;
	private String approvalNo;
	private String accType;

}
