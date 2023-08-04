package com.ems.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.springboot.exception.ResourseNotFoundException;
import com.ems.springboot.model.Employee;
import com.ems.springboot.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all rest api
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee>  getEmployeebyID (@PathVariable Long id) {
		Employee employee = employeeRepository.findById(id).
				orElseThrow(() -> new ResourseNotFoundException("employee not exist with id" + id));
		return ResponseEntity.ok(employee);
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee( @PathVariable long id , @RequestBody Employee employeedetails){
		Employee employee = employeeRepository.findById(id).
				orElseThrow(() -> new ResourseNotFoundException("employee not exist with id" + id));
		employee.setFirstname(employeedetails.getFirstname());
		employee.setLastname(employeedetails.getLastname());
		employee.setEmailId(employeedetails.getEmailId());
		
		Employee updateEmployee =employeeRepository.save(employee);
		
		return ResponseEntity.ok(updateEmployee);
		
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity< Map<String	, Boolean> >deleteEmployee(@PathVariable long id){
		Employee employee = employeeRepository.findById(id).
				orElseThrow(() -> new ResourseNotFoundException("employee not exist with id" + id));
		
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted	", Boolean.TRUE);
		return ResponseEntity.ok(response);

}
}
