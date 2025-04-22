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