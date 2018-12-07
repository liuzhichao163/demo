package cn.tedu.store.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.PasswordConflictException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;

public class UserTset {
	
	//插入用户信息
	@Test
	public void insertTest() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("spring-mapper.xml");
		UserMapper userMapper = ac.getBean("userMapper",UserMapper.class);
		User user = new User();
		user.setUsername("liucangsong");
		user.setPassword("123456");
		user.setGender(1);
		user.setPhone("13969862746");
		user.setEmail("liuzc@163.com");
		user.setAvatar("");
		user.setCreatedUser("liucangsong");
		user.setCreatedTime(new Date());
		user.setModifiedUser("liucangsong");
		user.setModifiedTime(new Date());
		
		
		Integer result = userMapper.insert(user);
		System.out.println("改变行数："+result);
		System.out.println("新增id:"+user.getId());
	}
	//根据用户名查询用户信息
	@Test
	public void findUserByusernameTest() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("spring-mapper.xml");
		UserMapper userMapper = ac.getBean("userMapper",UserMapper.class);
		User user = new User();
		user.setUsername("Tom");
		
		User result = userMapper.findUserByUsername(user.getUsername());
		System.out.println("用户信息："+result);
	}
	
	@Test
	public void regTest() {
		try {
			AbstractApplicationContext ac = new ClassPathXmlApplicationContext("spring-mapper.xml","spring-service.xml");
			IUserService userService = ac.getBean("userService",IUserService.class);
			User user = new User();
			user.setUsername("xxx");
			user.setPassword("123456");
			user.setGender(1);
			user.setPhone("13969862746");
			user.setEmail("liuzc@163.com");
			user.setAvatar("");
			user.setUuid("123456");
			user.setCreatedUser("xxx");
			user.setCreatedTime(new Date());
			user.setModifiedUser("xxx");
			user.setModifiedTime(new Date());
			
			User result = userService.reg(user);
			System.out.println("注册成功，信息："+result);
			
		}catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	@Test
	public void loginTest() {
		try {
			AbstractApplicationContext ac = new ClassPathXmlApplicationContext("spring-mapper.xml","spring-service.xml");
			IUserService userMapper = ac.getBean("userService",IUserService.class);
			
			User result = userMapper.login("jk","123456");
			System.out.println(result);
		}catch (UsernameConflictException e) {
			System.out.println(e.getMessage());
		}catch (PasswordConflictException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void changePassword() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("spring-mapper.xml");
		UserMapper userMapper = ac.getBean("userMapper",UserMapper.class);
		Integer result = userMapper.changePassword(25, "111111","lilei",new Date());
		System.out.println(result);
	}
	
	@Test
	public void findPassword() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("spring-mapper.xml");
		UserMapper userMapper = ac.getBean("userMapper",UserMapper.class);
		User user = userMapper.findUserById(25);
		System.out.println(user.getPassword());
		
	}
	
	@Test
	public void serviceChagePwdTest() {
		try {
			AbstractApplicationContext ac = new ClassPathXmlApplicationContext("spring-mapper.xml","spring-service.xml");
			IUserService userService = ac.getBean("userService",IUserService.class);
			
			
			Integer result = userService.changePassword(30, "123456", "111111");
			System.out.println("影响行数："+result);
			
		}catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
		
	}
	@Test
	public void changeInfoPassword() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("spring-mapper.xml");
		UserMapper userMapper = ac.getBean("userMapper",UserMapper.class);
		User user = new User();
		user.setId(30);
		user.setUsername("lilei");
		user.setGender(0);
		user.setPhone("13969862749");
		user.setEmail("nnn@163.com");
		user.setModifiedUser(user.getUsername());
		user.setModifiedTime(new Date());
		Integer result = userMapper.changeInfo(user);
		System.out.println("行数："+result);
		
	}
	
	@Test
	public void changeInfoTest() {
		try {
			AbstractApplicationContext ac = new ClassPathXmlApplicationContext("spring-mapper.xml","spring-service.xml");
			IUserService userService = ac.getBean("userService",IUserService.class);
			
			
			//Integer result = userService.changeInfo(31, "999", 1, "13969862111", "999@163.com");
			//System.out.println("影响行数："+result);
			
		}catch (UserNotFoundException e) {
			System.out.println(e.getMessage());
		}catch (UsernameConflictException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
