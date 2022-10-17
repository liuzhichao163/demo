package com.example.demo.service.decorator;

/**
 * 定义装饰者
 * 抽象装饰（Decorator）角色
 * 继承抽象构件，并包含具体构件的实例，可以通过其子类扩展具体构件的功能
 *
 * @ClassName Decorator
 * @Create: 2022/9/19 14:25
 */
public abstract class Decorator implements Human{

	private Human human;

	public Decorator(Human human) {
		this.human = human;
	}

	public void wearClothes(){
		human.wearClothes();
	};

	public void walkToWhere(){
		human.walkToWhere();
	};


}