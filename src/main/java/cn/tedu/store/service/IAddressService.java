package cn.tedu.store.service;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.InsertDataException;

public interface IAddressService {
	
	/**
	 * 新增用户地址信息
	 * @param username 当前登录的用户名
	 * @param address  收货地址数据
	 * @return 返回
	 */
	Address add(String username,Address address)throws InsertDataException;

}
