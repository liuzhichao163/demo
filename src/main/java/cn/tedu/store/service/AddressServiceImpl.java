package cn.tedu.store.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Address;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.ex.InsertDataException;

@Service("addressService")
public class AddressServiceImpl implements IAddressService {
	//声明持久层对象
	@Autowired
	private AddressMapper addressMapper;
	
	//新增用户地址信息
	public Address add(String username,Address address) throws InsertDataException{
		//判断address中的uid和revcName是否为null
		
		//封装日志信息
		Date now = new  Date();
		address.setCreatedUser(username);
		address.setModifiedUser(username);
		address.setCreatedTime(now);
		address.setModifiedTime(now);
		//单次的增删改操作都应该抛出异常
		//执行增加
		Integer rows = addressMapper.insert(address);
		if(rows==1) {
			//增加成功
			return address;
		}else {
			//增加失败，抛出异常
			throw new InsertDataException("增加收货地址失败");
		}
		
	}

}
