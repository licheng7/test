package com.kaoorange.test.util.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IDEA
 * author:licheng
 * Date:2019/9/5
 * Time:下午7:34
 **/
@Component
public class EventBusUtil {

    @Autowired
    private AsyncEventBus asyncEventBus;

    //注册这个监听器
    public void register(EventBusListener eventBusListener) {
        asyncEventBus.register(eventBusListener);
    }

    public void send(EventInfo event) {
        asyncEventBus.post(event);
    }
}
