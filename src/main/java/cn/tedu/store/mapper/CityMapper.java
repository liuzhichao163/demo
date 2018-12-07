package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.City;

public interface CityMapper {
	
	List<City> getCityListByProvinceCode(String provinceCode);
	
	City getCityByCode(String cityCode);

}
