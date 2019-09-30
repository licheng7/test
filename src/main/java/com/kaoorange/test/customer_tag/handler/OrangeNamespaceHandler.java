package com.kaoorange.test.customer_tag.handler;

import com.kaoorange.test.customer_tag.entity.OrangeTag;
import com.kaoorange.test.customer_tag.parser.OrangeBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created with IDEA
 * author:licheng
 * Date:2019/9/19
 * Time:下午1:37
 **/
public class OrangeNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("orange", new OrangeBeanDefinitionParser(OrangeTag.class));
    }
}
