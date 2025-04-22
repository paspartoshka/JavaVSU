package org.example;

/**
 * Класс с полями в которые будут внедряться зависимости
 */
public class SomeBean {
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
