package com.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.mybatis.entity.Employee;

public interface EmployeeMapper {
	
	
	Employee getEmpById(Integer id);
	
	Employee getEmpByIdAndlastName(@Param("id") Integer id,@Param("lastName") String lastName);
	
	List<Employee> listEmps();
	
	//返回一条记录的map，key就是列名，值就是对应的值
	Map<String,Object> getEmpByIdReturnMap(Integer id);
	
	/*
	 * 多条记录封装一个map，键是这条记录的主键，值是记录封装后的javaBean
	 * @MapKey：告诉mybatis封装这个map的时候使用哪个属性作为主键
	 */
	@MapKey("id")
	Map<Integer,Employee> listEmpsReturnMap();
	
	int insertEmp(Employee employee);
	
	boolean deleteEmpById(Integer id);
	
	void updateEmpById(Employee empoyee);
	

}
