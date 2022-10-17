package com.example.demo.service.mq;

import com.example.demo.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 模块名称
 * 模块简单说明
 *
 * @ClassName MqTest
 * @Author: ytc
 * @Create: 2022/10/17 13:38
 * @Version: v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class MqTest {

	@Autowired
	private SpringBootProducer springBootProducer;

	/**
	 * 普通字符串消息
	 */
	@Test
	public void sendMsg(){
		String topic = "test_01";
		String msg = "你好！";
		springBootProducer.sendMessage(topic,msg);
		System.out.println("消息发送完毕");
	}

	/**
	 * 同步消息
	 */
	@Test
	public void syncSend() {
		String topic = "test_01";
		String msg = "这是同步消息！";
		springBootProducer.syncSendMessage(topic,msg);
		System.out.println("消息发送完毕");
	}

	/**
	 * 异步消息
	 */
	@Test
	public void asyncSend() {
		String topic = "test_02";
		String msg = "这是异步消息！";
		springBootProducer.asyncSendMessage(topic,msg);
		System.out.println("消息发送完毕");
	}

	/**
	 * 单向消息
	 */
	@Test
	public void onewaySend() {
		String topic = "test_01";
		String msg = "这是单向消息！";
		springBootProducer.onewaySend(topic,msg);
		System.out.println("消息发送完毕");
	}

	/**
	 * 延时消息
	 */
	@Test
	public void sendDelayedSend() {
		String topic = "test_03";
		String msg = "这是延时消息！";
		long timeout = 3000;
		int delayLevel = 3;
		springBootProducer.sendDelayedMessage(topic,msg,timeout,delayLevel);
		System.out.println("消息发送完毕");
	}

}