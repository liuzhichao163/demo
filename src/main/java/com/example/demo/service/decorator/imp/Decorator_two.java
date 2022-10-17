package com.example.demo.service.decorator.imp;

import com.example.demo.service.decorator.Decorator;
import com.example.demo.service.decorator.Human;
import org.springframework.stereotype.Service;

/**
 * 模块名称
 * 模块简单说明
 *
 * @ClassName Decorator_two
 * @Author: ytc
 * @Create: 2022/9/19 14:36
 * @Version: v1.0
 */
public class Decorator_two extends Decorator {

	public Decorator_two(Human human) {
		super(human);
	}

	@Override
	public void wearClothes() {
		super.wearClothes();
		findClothes();
	}

	@Override
	public void walkToWhere() {
		super.walkToWhere();
		findTheTarget();
	}

	public void findClothes() {
		System.out.println("找到一件D&G。。");

	}

	public void findTheTarget() {
		System.out.println("在Map上找到神秘花园和城堡。。");
	}

}