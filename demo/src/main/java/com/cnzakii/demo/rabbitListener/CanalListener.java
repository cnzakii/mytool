package com.cnzakii.demo.rabbitListener;

import com.cnzakii.core.Canal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * canal监听器
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-06-04
 **/
@Component
@Slf4j
public class CanalListener {



    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "recipe.canal.queue"),
            exchange = @Exchange(name = "ppeng.topic.exchange", type = ExchangeTypes.TOPIC),
            key = "canal.data"
    ))
    public void listenCanalQueue(String json) {
        log.info("MQ收到消息了。。。。");

        Canal.exec(json);
    }


}
