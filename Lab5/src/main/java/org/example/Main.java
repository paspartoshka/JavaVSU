package org.example;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.Properties;
import java.io.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface AutoInjectable{
}


interface SomeInterface{
    void doSomething();
}

interface SomeOtherInterface{
    void doSomething();
}

class SomeImpl implements SomeInterface
{
    public void doSomething()
    {
        System.out.println("A");
    }
}

class OtherImpl implements SomeInterface {
    public void doSomething()
    {
        System.out.println("B");
    }
}

class SODoer implements SomeOtherInterface
{
    public void doSomething()
    {
        System.out.println("C");
    }
}

class SomeBean {
    @AutoInjectable
    private SomeInterface field1;

    @AutoInjectable
    private SomeOtherInterface field2;

    public void foo() {
        field1.doSomething();
        field2.doSomething();
    }
}

class Injector {
    private Properties properties;

    public Injector(String fileName) {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> T inject(T obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                String implClassName = properties.getProperty(field.getType().getName());
                if (implClassName != null) {
                    try {
                        Class<?> implClass = Class.forName(implClassName);
                        Object implInstance = implClass.getDeclaredConstructor().newInstance();
                        field.setAccessible(true);
                        field.set(obj, implInstance);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return obj;
    }
}

public class Main {
    public static void main(String[] args) {
        Injector injector = new Injector("config.properties");
        SomeBean bean = injector.inject(new SomeBean());
        bean.foo();
    }
}