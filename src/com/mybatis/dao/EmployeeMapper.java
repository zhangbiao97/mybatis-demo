package com.mybatis.dao;

import org.apache.ibatis.annotations.Param;

import com.mybatis.entity.Employee;

public interface EmployeeMapper {
	
	
	Employee getEmpById(Integer id);
	
	Employee getEmpByIdAndlastName(@Param("id") Integer id,@Param("lastName") String lastName);
	
	int insertEmp(Employee employee);
	
	boolean deleteEmpById(Integer id);
	
	void updateEmpById(Employee empoyee);
	

}
