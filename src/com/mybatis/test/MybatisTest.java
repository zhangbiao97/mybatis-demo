package com.mybatis.test;


import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.javassist.bytecode.stackmap.TypeData.ClassName;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import com.mybatis.dao.DepartmentMapper;
import com.mybatis.dao.EmployeeAnnotationMapper;
import com.mybatis.dao.EmployeeMapper;
import com.mybatis.dao.EmployeeMapperPlus;
import com.mybatis.entity.Department;
import com.mybatis.entity.Employee;

/**
 * 
 * 1、接口式编程
 * 		原生：Dao	->		DaoImpl
 * 		mybatis：Mapper	->	xxxMapper.xml
 * 2、SqlSession代表和数据库的一次会话，用完必须关闭
 *  SqlSession和connection一样它都是非线程安全，每次使用都
 *  应该去获取新的对象；
 * 3、Mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象（将接口和xml进行绑定）
 * 		EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
 * 4、两个重要的配置文件：
 * 		mybatis的全局配置文件：包含数据库连接池信息，事务管理器信息等；
 * 		sql映射文件：保存了每一个sql语句的映射信息
 * 
 * @author Administrator
 *
 */
class MybatisTest {
	
	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}

	/**
	 * 
	 * 1、根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象，有数据源等运行环境的信息；
	 * 2、sql映射文件，配置了每一个sql以及sql的封装规则等；
	 * 3、将sql映射文件注册在配置文件中；
	 * 4、写代码：
	 * 		1)、根据全局配置文件得到SqlSessionFactory；
	 * 		2)、使用sqlSession工厂，获取到sqlSession对象使用它来执行增删改查
	 * 			一个sqlSession就是代表和数据库的一次会话，用完关闭；
	 * 		3)、使用sql的唯一标识来告诉MyBatis执行哪个sql，sql都是保存在sql映射文件中的；
	 * 
	 * @throws IOException
	 */
	@Test
	public void test01() throws IOException {
		//1、获取SqlSessionFactory
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		//2、获取sqlSession实例，能直接执行已经映射的sql语句
		SqlSession openSession = sqlSessionFactory.openSession();
		//sql的唯一标识，执行sql要用的参数
		Employee employee = openSession.selectOne("com.mybatis.EmployeeMapper.selectEmp", 1);
		System.out.println(employee);
		
		openSession.close();
	}

	
	@Test
	public void test02() throws IOException {
		//获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		//获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			//获取借口的实现类对象
			//会为借口自动的创建一个代理对象，代理对象去执行增删改查的方法
			EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = employeeMapper.getEmpById(1);
			System.out.println(employee);
		} finally {
			openSession.close();
		}
	}
	
	@Test
	public void test03() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeAnnotationMapper mapper = openSession.getMapper(EmployeeAnnotationMapper.class);
			Employee empById = mapper.getEmpById(1);
			System.out.println(empById);
		}finally {
			openSession.close();
		}
	}
	
	/**
	 * 
	  *  测试增删改查
	 * 1、mybatis允许增删改直接定义以下类型返回值
	 * 		Integer、Long、Boolean、void
	 * 2、需要我们手动提交数据
	 * 		sqlSession.openSession(); -> 手动提交
	 * 		sqlSession.openSession(true); -> 自动提交
	 * 
	 * @throws IOException
	 */
	@Test
	public void test04() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		//1、获取无参构造的SqlSession是不会自动提交的，需要手动提交
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);

			//测试增加
			Employee employee = new Employee(null, "lisi", 1, "lisi@qq.com");
			int insertEmp = employeeMapper.insertEmp(employee);
			System.out.println(insertEmp);
			System.out.println(employee);
			
			//测试删除
			/*boolean deleteEmpById = employeeMapper.deleteEmpById(1);
			System.out.println(deleteEmpById);*/
			
			//测试修改
			//employeeMapper.updateEmpById(new Employee(2, "zhangsan", 1, "zhangsan@qq.com"));
			
			
			
			//2、手动提交
			openSession.commit();
		}finally {
			openSession.close();
		}
	}
	
	@Test
	public void test05() throws IOException {
		//获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		//获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			//获取借口的实现类对象
			//会为借口自动的创建一个代理对象，代理对象去执行增删改查的方法
			EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = employeeMapper.getEmpByIdAndlastName(2, "zhangsan");
			System.out.println(employee);
		} finally {
			openSession.close();
		}
	}
	
	/**
	 * 测试返回List
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@Test
	public void test06() throws ClassNotFoundException, IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
			List<Employee> listEmps = employeeMapper.listEmps();
			listEmps.forEach(System.out::println);
		}finally {
			openSession.close();
		}
	}
	
	/**
	 * 测试返回map
	 * @throws IOException
	 */
	@Test
	public void test07() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
			//Map<String, Object> empByIdReturnMap = employeeMapper.getEmpByIdReturnMap(2);
			Map<Integer, Employee> listEmpsReturnMap = employeeMapper.listEmpsReturnMap();
			for(Entry<Integer, Employee> item:listEmpsReturnMap.entrySet()) {
				System.out.println("key:"+item.getKey()+",value:"+item.getValue());
			}
		}finally {
			openSession.close();
		}
	}
	
	/**
	 * 测试返回resultMap
	 * @throws IOException
	 */
	@Test
	public void test08() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapperPlus employeeMapperPlus = openSession.getMapper(EmployeeMapperPlus.class);
			Employee employee = employeeMapperPlus.getEmpById(2);
			System.out.println(employee);
		}finally {
			openSession.close();
		}
	}
	
	/**
	 * 测试association
	 * @throws IOException
	 */
	@Test
	public void test09() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapperPlus employeeMapperPlus = openSession.getMapper(EmployeeMapperPlus.class);
			Employee employee = employeeMapperPlus.getEmpByIdStep(3);
			System.out.println(employee.getLastName());
			System.out.println(employee.getDept());
		}finally {
			openSession.close();
		}
	}
	
	/**
	 * 测试collection
	 * @throws IOException
	 */
	@Test
	public void test10() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			DepartmentMapper departmentMapper = openSession.getMapper(DepartmentMapper.class);
			//Department department = departmentMapper.getDeptById(1);
			Department department = departmentMapper.getDeptByIdStep(1);
			System.out.println(department.getDeptName());
			department.getEmps()
				.forEach(System.out::println);
		}finally {
			openSession.close();
		}
	}
	
}
