package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.Province;

public interface ProvinceMapper {
	
	/**
	 * 查询省
	 * @return 
	 */
	List<Province> getProvince();
	
	
	Province getProvinceByCode(String provinceCode);

}
