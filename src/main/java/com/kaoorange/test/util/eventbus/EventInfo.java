package com.kaoorange.test.util.eventbus;

/**
 * Created with IDEA
 * author:licheng
 * Date:2019/9/5
 * Time:下午7:34
 **/
public class EventInfo {

    public String eventName;

    public Object eventObj;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Object getEventObj() {
        return eventObj;
    }

    public void setEventObj(Object eventObj) {
        this.eventObj = eventObj;
    }
}
