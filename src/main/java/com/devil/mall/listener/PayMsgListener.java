package com.devil.mall.listener;

import com.devil.mall.pojo.PayInfo;
import com.devil.mall.service.IOrderService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author Hanyanjiao
 * @date 2020/6/3
 * pay项目提供一个client.jar mall项目引用
 */
@Slf4j
@Component
public class PayMsgListener {

    @Autowired
    private IOrderService orderService;

    @RabbitListener(queues = "payNotify", containerFactory="rabbitListenerContainerFactory")
    public void process(@Payload String msg){
        log.info("接受到的消息是 =>{}",msg);

        PayInfo payInfo = new Gson().fromJson(msg, PayInfo.class);
        if(payInfo.getPlatformStatus().equals("SUCCESS")){
            //修改订单里的状态
            orderService.paid(payInfo.getOrderNo());
        }
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
}
