package com.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mybatis.entity.Employee;

public interface EmployeeDynamicSQLMapper {

	/**
	 * 动态SQL查询，使用if标签
	 * @param employee
	 * @return
	 */
	List<Employee> listEmpsByConditionIf(Employee employee);
	
	/**
	 * 动态SQL查询，使用trim标签
	 * @param employee
	 * @return
	 */
	List<Employee> listEmpsByConditionTrim(Employee employee);
	
	/**
	 * 动态SQL查询，使用choose标签
	 * @param employee
	 * @return
	 */
	List<Employee> listEmpsByConditionChoose(Employee employee);
	
	/**
	 * 动态SQL修改，使用set标签
	 * @param employee
	 */
	void updateEmp(Employee employee);
	
	/**
	 * 动态SQL查询，使用foreach标签
	 * @param ids
	 * @return
	 */
	List<Employee> listEmpsByConditionForeach(@Param("ids") List<Integer> ids);
	
	/**
	 * 批量添加，使用foreach标签
	 * @param emps
	 */
	void insertEmps(@Param("emps") List<Employee> emps);
	
	/**
	 * 动态SQL查询，使用mybatis提供的内置参数
	 * @param employee
	 * @return
	 */
	List<Employee> listEmpsByConditionInnerParameter(Employee employee);
}
