package com.example.demo.service.mq;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 模块名称
 * 模块简单说明
 *
 * @ClassName Consumer_02
 * @Author: ytc
 * @Create: 2022/10/17 15:22
 * @Version: v1.0
 */
@Component
@RocketMQMessageListener(
        topic = "test_02",
        consumerGroup="test-group-02",
        selectorExpression = "*",
        messageModel = MessageModel.CLUSTERING
)
public class Consumer_02 implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        try {
            // 睡眠五十毫秒，确保消息成功接收（演示专用，勿喷）
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Consumer_02 监听到的消息："+s);
    }
}