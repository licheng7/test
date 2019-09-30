package com.kaoorange.test.threadTest.eventbus;

import com.kaoorange.test.util.eventbus.EventBusListener;
import com.kaoorange.test.util.eventbus.EventBusUtil;
import com.kaoorange.test.util.eventbus.EventInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IDEA
 * author:licheng
 * Date:2019/9/5
 * Time:下午7:56
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class EventBusTest {

    private static final Logger logger = LoggerFactory.getLogger(EventBusTest.class);

    @Autowired
    private EventBusUtil eventBusUtil;

    @Autowired
    private EventBusListener eventBusListenerImpl;

    @Autowired
    private EventBusListener eventBusListenerImpl2;

    @Test
    public void eventBusTest() {
        EventInfo info = new EventInfo();
        info.setEventName("orabge");
        info.setEventObj("kao");

        eventBusUtil.register(eventBusListenerImpl);
        eventBusUtil.register(eventBusListenerImpl2);
        eventBusUtil.send(info);
    }
}
