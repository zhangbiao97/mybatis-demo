package com.mybatis.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import com.mybatis.dao.EmployeeMapper;
import com.mybatis.entity.Employee;

class MybatisCacheTest {

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}

	/**
	 *	≤‚ ‘“ªº∂ª∫¥Ê
	 * @throws IOException
	 */
	@Test
	public void test01() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = employeeMapper.getEmpById(2);
			System.out.println(employee);
			openSession.clearCache();
			Employee employee2 = employeeMapper.getEmpById(2);
			System.out.println(employee2);
			System.out.println(employee == employee2);
		} finally {
			openSession.close();
		}
	}
	
	/**
	 * 	≤‚ ‘∂˛º∂ª∫¥Ê
	 * @throws IOException
	 */
	@Test
	public void test02() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		SqlSession openSession2 = sqlSessionFactory.openSession();
		try {
			EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
			Employee emp01 = employeeMapper.getEmpById(2);
			System.out.println(emp01);
			
			openSession.close();
			
			EmployeeMapper employeeMapper2 = openSession2.getMapper(EmployeeMapper.class);
			Employee emp02 = employeeMapper2.getEmpById(2);
			System.out.println(emp02);
			
			openSession2.close();
		} finally {
			
		}
	}

}
