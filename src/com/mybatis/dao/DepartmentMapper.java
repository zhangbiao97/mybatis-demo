package com.mybatis.dao;

import com.mybatis.entity.Department;

public interface DepartmentMapper {

	Department getDeptById(Integer id);
	
	Department getDeptByIdPlus(Integer id);
	
	Department getDeptByIdStep(Integer id);
	
}
