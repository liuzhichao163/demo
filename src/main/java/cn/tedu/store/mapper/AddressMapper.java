package cn.tedu.store.mapper;

import cn.tedu.store.entity.Address;

public interface AddressMapper {
	/**
	 * 新增数货地址
	 * @param address
	 * @return 返回受影响的行数
	 */
	Integer insert(Address address);

}
