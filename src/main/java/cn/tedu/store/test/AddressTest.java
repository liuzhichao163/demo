package cn.tedu.store.test;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entity.Address;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.IUserService;

public class AddressTest {
	
	@Test
	public void insertTest() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("spring-mapper.xml");
		AddressMapper addressMapper = ac.getBean("addressMapper",AddressMapper.class);
		Address address = new Address(); 
		address.setUid(31);
		address.setRecvName("wangye");
		address.setRecvCity("110000");
		Integer i = addressMapper.insert(address);
		System.out.println("影响行数："+i);
	}
	
	@Test
	public void insertServiceTest() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("spring-mapper.xml","spring-service.xml");
		IAddressService userService = ac.getBean("addressService",IAddressService.class);//第一个参数些AddressServiceIml类的Service（"addressService"）
		Address add = new Address();
		add.setUid(30);
		add.setRecvName("lilei");
		add.setRecvCity("110011");
		Address  address = userService.add("lilei", add);
		System.out.println(address.toString());
	}

}
