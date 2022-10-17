package com.example.demo.service.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 模块名称
 * 模块简单说明
 *
 * @ClassName SpringBootProducer
 * @Author: ytc
 * @Create: 2022/10/17 13:36
 * @Version: v1.0
 */
@Slf4j
@Component
public class SpringBootProducer {

	@Autowired
	private RocketMQTemplate rocketMQTemplate;

	/**
	 * 普通字符串消息
	 */
	public void sendMessage(String topic, String msg) {
		rocketMQTemplate.convertAndSend(topic, msg);
	}

	/**
	 * 同步消息
	 */
	public void syncSendMessage(String topic, String msg){
		SendResult sendMessage = rocketMQTemplate.syncSend(topic, msg);
		System.out.println(sendMessage);
	}

	/**
	 * 异步消息
	 */
	public void asyncSendMessage(String topic, String msg){
		SendCallback callback = new SendCallback() {
			@Override
			public void onSuccess(SendResult sendResult) {
				System.out.println("消息发送成功");
			}

			@Override
			public void onException(Throwable throwable) {
				System.out.println("消息发送失败");
			}
		};
		rocketMQTemplate.asyncSend(topic, msg, callback);
	}

	/**
	 * 单向消息
	 */
	public void onewaySend(String topic, String msg) {
		rocketMQTemplate.sendOneWay(topic, msg);
	}

	/**
	 * 延时消息
	 * timeout：发送消息的超时时间
	 * //delayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h 对应定时的延时时间
	 * 如果delayLevel=3，那么就是延时10s
	 */
	public void sendDelayedMessage(String topic, String msg, long timeout, int delayLevel){
		SendResult sendResult = rocketMQTemplate.syncSend(
				topic,
				MessageBuilder.withPayload(
						msg + new Date().getTime()).build(),
				timeout,
				delayLevel);
		log.info("消息延时推送成功,{},{}", sendResult.getSendStatus(), new Date());
	}

}