package com.test.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyProxy implements InvocationHandler{

    private Class<?> interfaceClass;

    public Object bind(Class<?> cls){
        this.interfaceClass = cls;
        return Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{interfaceClass}, this);
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("this is dynamic object");
        return "hello world";
    }
}
