package org.example;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.Properties;
import java.io.*;

/**
 * Аннотация для пометки полей в которые необходимо внедрить зависимости
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface AutoInjectable{
}

/**
 * Интерфейс с методом doSomething
 */
interface SomeInterface{
    void doSomething();
}

/**
 * Другой интерфейс с методом doSomething
 */
interface SomeOtherInterface{
    void doSomething();
}

/**
 * Реализация SomeInterface, которая выводит А
 */
class SomeImpl implements SomeInterface
{
    public void doSomething()
    {
        System.out.println("A");
    }
}

/**
 * Альтернативная реализация SomeInterface, которая выводит B
 */
class OtherImpl implements SomeInterface {
    public void doSomething()
    {
        System.out.println("B");
    }
}

/**
 * Реализация SomeOtherInterface, которая выводит С
 */
class SODoer implements SomeOtherInterface
{
    public void doSomething()
    {
        System.out.println("C");
    }
}

/**
 * Класс с полями в которые будут внедряться зависимости
 */
class SomeBean {
    @AutoInjectable
    private SomeInterface field1;

    @AutoInjectable
    private SomeOtherInterface field2;

    /**
     * Метод демонстрации работы внедренных зависимостей
     */
    public void foo() {
        field1.doSomething();
        field2.doSomething();
    }
}

/**
 * Класс выполняющий автоматическое внедрение зависимостей в поля помеченные
 * аннотацией @AutoInjectable
 */
class Injector {
    private Properties properties;

    /**
     * загружает конфигурацию из файла
     * @param fileName имя файла
     */
    public Injector(String fileName) {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Выполняет внедрение зависимостей в обьект
     * @param obj обьект
     * @return тот же обьект но с заполненными полями
     * @param <T> тип обьекта
     */
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

/**
 * Клас для демоснтрации работы программы
 */
public class Main {
    /**
     * Вход в программу. Создает SomeBean внедряет в него зависимости и вызывает метод foo
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Injector injector = new Injector("config.properties");
        SomeBean bean = injector.inject(new SomeBean());
        bean.foo();
    }
}