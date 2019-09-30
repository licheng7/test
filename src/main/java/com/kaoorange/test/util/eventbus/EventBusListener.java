package com.kaoorange.test.util.eventbus;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IDEA
 * author:licheng
 * Date:2019/9/5
 * Time:下午8:11
 **/
public interface EventBusListener {

    Logger logger = LoggerFactory.getLogger(EventBusListener.class);

    @Subscribe
    default void listener(EventInfo info) {
        logger.info("~~~~~~~~~~~~~~~~receive msg:" + info.getEventName());
    }
}
