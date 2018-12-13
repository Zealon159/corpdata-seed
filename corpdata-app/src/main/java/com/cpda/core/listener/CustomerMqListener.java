package com.cpda.core.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 客户更新队列处理
 *
 * @Author: zealon
 * @Version: 1.0
 */
@Component
public class CustomerMqListener {

    //定义消费者监听
    @JmsListener(destination = "order.queue",containerFactory = "myFactory")
    public void receiveQueue(final TextMessage message, Session session) throws JMSException {
        try {

            String text = message.getText();

            System.out.println(Thread.currentThread().getName() + " 消费消息：" + text);

            //消息确认
            message.acknowledge();

        } catch (JMSException e) {
            e.printStackTrace();
            //异常重试
            session.recover();
        }
    }
}
