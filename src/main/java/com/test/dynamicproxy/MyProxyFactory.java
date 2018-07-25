package com.test.dynamicproxy;

import org.springframework.beans.factory.FactoryBean;

public class MyProxyFactory<T> implements FactoryBean {

    private Class<T> interfaceClass;

    public Class<T> getInterfaceClass(){
        return interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass){
        this.interfaceClass = interfaceClass;
    }

    public T getObject() throws Exception {
        return (T) new MyProxy().bind(interfaceClass);
    }

    public Class<?> getObjectType() {
        return interfaceClass;
    }

    public boolean isSingleton() {
        return true;
    }
}
