package com.example.demo.service.mq;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 模块名称
 * 模块简单说明
 *
 * @ClassName Consumer_03
 * @Author: ytc
 * @Create: 2022/10/17 15:24
 * @Version: v1.0
 */
@Component
@RocketMQMessageListener(
		topic = "test_03",
		consumerGroup="test-group-03",
		selectorExpression = "*",
		messageModel = MessageModel.CLUSTERING
)
public class Consumer_03 implements RocketMQListener<String> {

	@Override
	public void onMessage(String s) {
		System.out.println("Consumer_03 监听到的消息："+ s +",当前时间："+new Date());
	}
}