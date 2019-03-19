package com.mybatis.dao;

import java.util.List;

import com.mybatis.entity.Employee;

public interface EmployeeMapperPlus {

	Employee getEmpById(Integer id);
	
	Employee getEmpByIdStep(Integer id);
	
	List<Employee> listEmpsByDeptId(Integer deptId);
	
}
