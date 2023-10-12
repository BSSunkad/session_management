package com.sessionApp.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sessionApp.demo.entity.EmpExtraData;
import com.sessionApp.demo.entity.EmpExtraData1;
import com.sessionApp.demo.entity.Employee;
import com.sessionApp.demo.service.EmpService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EmpController {

	@Autowired
	EmpService empService;

	@Autowired
	HttpSession session;

	private static final String REQPAY_EXTRA = "reqPayExtra";
	private static final String CHQTXN_EXTRA = "chqPayExtra";
	
	@GetMapping("/test")
	public String testing() {
		return "Test method executed";
	}

	@PostMapping("/process")
	public ResponseEntity<Employee> reqPay(@RequestBody Employee employee) {
		log.info("[" + session.getId() + "]==> Request recieved : {}", employee);

		EmpExtraData extraData = new EmpExtraData();

		extraData.setAccType(employee.getAccType());
		extraData.setAddress(employee.getAddress());
		extraData.setApprovalNo(employee.getApprovalNo());
		extraData.setPhNo(employee.getPhNo());

		log.info("[" + session.getId() + "]==> ExtraData saved in session : {}", extraData);

		session.setAttribute(EmpController.REQPAY_EXTRA, extraData);
		Employee employee2 = new Employee();

		employee2.setAge(employee.getAge());
		employee2.setEmail(employee.getEmail());
		employee2.setEmpId(employee.getEmpId());
		employee2.setName(employee.getName());

		log.info("[" + session.getId() + "]==> data sending to service method : {}", employee2);

		Employee process = empService.process(employee2);

		EmpExtraData extData = (EmpExtraData) session.getAttribute(EmpController.REQPAY_EXTRA);
		process.setAccType(extData.getAccType());
		process.setAddress(extData.getAddress());
		process.setApprovalNo(extData.getApprovalNo());
		process.setPhNo(extData.getPhNo());

		log.info("[" + session.getId() + "]==> data after service method : {}", process);
		return new ResponseEntity<Employee>(process, HttpStatus.CREATED);
	}

	@PostMapping("/process1")
	public Employee chqTxn(@RequestBody Employee employee) {
		log.info("[" + session.getId() + "] ==> request recieved : {}", employee);

		EmpExtraData1 extraData = new EmpExtraData1();

		extraData.setAddress(employee.getAddress());
		extraData.setApprovalNo(employee.getApprovalNo());
		extraData.setPhNo(employee.getPhNo());

		log.info("[" + session.getId() + "] ==> data saved in session : {}", extraData);

		session.setAttribute(EmpController.CHQTXN_EXTRA, extraData);
		Employee empl = new Employee();
		empl.setEmail(employee.getEmail());
		empl.setName(employee.getName());
		empl.setEmpId(employee.getEmpId());

		log.info("[" + session.getId() + "] ==> data sending to service method : {}", empl);

		Employee process = empService.process1(empl);

		EmpExtraData1 extraData1 = (EmpExtraData1) session.getAttribute(EmpController.CHQTXN_EXTRA);
		process.setAddress(extraData1.getAddress());
		process.setApprovalNo(extraData1.getApprovalNo());
		process.setPhNo(extraData1.getPhNo());

		log.info("[" + session.getId() + "] ==> data after service method : {}", process);

		return process;
	}

}