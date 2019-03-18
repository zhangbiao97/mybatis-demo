package com.mybatis.dao;

import org.apache.ibatis.annotations.Select;

import com.mybatis.entity.Employee;

public interface EmployeeAnnotationMapper {

	@Select("select * from employee where id=#{id}")
	Employee getEmpById(Integer id); 
	
	
}
