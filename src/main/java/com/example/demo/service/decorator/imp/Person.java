package com.example.demo.service.decorator.imp;

import com.example.demo.service.decorator.Human;
import org.springframework.stereotype.Service;

/**
 * 定义被装饰者，被装饰者初始状态有些自己的装饰
 * 具体构件（ConcreteComponent）角色
 * 实现抽象构件，通过装饰角色为其添加一些职责
 *
 * @ClassName Person
 * @Author: ytc
 * @Create: 2022/9/19 14:37
 * @Version: v1.0
 */
@Service
public class Person implements Human {

    @Override
    public void wearClothes() {
        System.out.println("穿什么呢?");
    }

    @Override
    public void walkToWhere() {
        System.out.println("去哪里呢?");
    }
}