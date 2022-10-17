package com.example.demo.service.decorator.imp;

import com.example.demo.service.decorator.Decorator;
import com.example.demo.service.decorator.Human;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 模块名称
 * 模块简单说明
 *
 * @ClassName Decorator_first
 * @Author: ytc
 * @Create: 2022/9/19 14:34
 * @Version: v1.0
 */
public class Decorator_first extends Decorator {

	public Decorator_first(Human human) {

		super(human);
	}


	@Override
	public void wearClothes() {
		super.wearClothes();
		goClothespress();
	}

	@Override
	public void walkToWhere() {
		super.walkToWhere();
		findPlaceOnMap();
	}

	public void goClothespress() {
		System.out.println("去衣柜找找看。。");
	}

	public void findPlaceOnMap() {
		System.out.println("在Map上找找。。");
	}



}