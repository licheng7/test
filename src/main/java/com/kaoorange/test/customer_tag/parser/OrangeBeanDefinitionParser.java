package com.kaoorange.test.customer_tag.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created with IDEA
 * author:licheng
 * Date:2019/9/19
 * Time:下午1:40
 **/
public class OrangeBeanDefinitionParser implements BeanDefinitionParser {

    private final Class<?> beanClass;

    public OrangeBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        beanDefinition.getPropertyValues().add("param1", element.getAttribute("param1"));
        beanDefinition.getPropertyValues().add("param2", element.getAttribute("param2"));
        BeanDefinitionRegistry beanDefinitionRegistry = parserContext.getRegistry();
        beanDefinitionRegistry.registerBeanDefinition(beanClass.getName(), beanDefinition); //注册bean到BeanDefinitionRegistry中
        return beanDefinition;
    }
}
