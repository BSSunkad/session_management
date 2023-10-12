package com.sessionApp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sessionApp.demo.entity.Employee;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmpService {

	@Autowired
	HttpSession session;

	public Employee process(Employee employee) {
		try {
			Thread.sleep(10000);
			log.info("[" + session.getId() + "]==> Threat waiting for 10 seconds");
		} catch (InterruptedException e) {
			log.info("[" + session.getId() + "]==> Error in thread", e.getMessage());
			e.printStackTrace();
		}
		log.info("[" + session.getId() + "]==> Request recieved in service : {}", employee);
		employee.setName("Basavaraj"); // it will become default entry
		log.info("[" + session.getId() + "]==> Processed request {}", employee);
		return employee;
	}

	public Employee process1(Employee employee) {
		log.info("[" + session.getId() + "]==> Request recieved in service : {}", employee);
		employee.setAccType("SB");
		log.info("[" + session.getId() + "]==> Process Request : {}", employee);
		return employee;
	}
}
