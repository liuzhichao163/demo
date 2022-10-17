package com.example.demo.service.decorator.imp;

import com.example.demo.service.decorator.Decorator;
import com.example.demo.service.decorator.Human;
import org.springframework.stereotype.Service;

/**
 * 具体装饰（ConcreteDecorator）角色
 * 实现抽象装饰的相关方法，并给具体构件对象添加附加的责任
 * @ClassName Decorator_zero
 * @Author: ytc
 * @Create: 2022/9/19 14:27
 * @Version: v1.0
 */
public class Decorator_zero extends Decorator {

    public Decorator_zero(Human human) {
        super(human);
    }

    @Override
    public void wearClothes() {
        super.wearClothes();
        getHome();
    }

    @Override
    public void walkToWhere() {
        super.walkToWhere();
        findMap();
    }


    public void getHome(){
        System.out.println("进房子。。。");
    }

    public void findMap(){
        System.out.println("书房找Map。。。");
    }

}