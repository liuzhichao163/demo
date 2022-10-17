package com.example.demo.service.mq;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 模块名称
 * 模块简单说明
 *
 * @ClassName Consumer_01
 * @Author: ytc
 * @Create: 2022/10/17 15:15
 * @Version: v1.0
 */
@Component
@RocketMQMessageListener(
		topic = "test_01",                          // 1.topic：消息的发送者使用同一个topic
		consumerGroup = "test-group",               // 2.group：不用和生产者group相同 ( 在RocketMQ中消费者和发送者组没有关系 )
		selectorExpression = "*",                   // 3.tag：设置为 * 时，表示全部。
		messageModel = MessageModel.CLUSTERING      // 4.消费模式：默认 CLUSTERING （ CLUSTERING：负载均衡 ）（ BROADCASTING：广播机制 ）
		)
public class Consumer_01 implements RocketMQListener<String> {

	@Override
	public void onMessage(String s) {
		try {
			// 睡眠五十毫秒，确保消息成功接收（演示专用，勿喷）
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Consumer_01 监听到的消息："+s);
	}
}