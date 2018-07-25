package com.test.dynamicproxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MyRegistryBean implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor{

    private ApplicationContext ctx;

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        Class<?> cls = TestService.class;
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(cls);
        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
        definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
        definition.setBeanClass(MyProxyFactory.class);
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        //注册bean
        beanDefinitionRegistry.registerBeanDefinition("testService", definition);
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
