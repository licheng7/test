package com.kaoorange.test.customer_tag.entity;

import org.springframework.beans.factory.InitializingBean;

/**
 * Created with IDEA
 * author:licheng
 * Date:2019/9/18
 * Time:下午5:44
 **/
public class OrangeTag implements InitializingBean {

    private String param1;

    private int param2;

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public int getParam2() {
        return param2;
    }

    public void setParam2(int param2) {
        this.param2 = param2;
    }

    @Override
    public String toString() {
        return "OrangeTag{" +
                "param1='" + param1 + '\'' +
                ", param2=" + param2 +
                '}';
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this.toString());
    }
}
