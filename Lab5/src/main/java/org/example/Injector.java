package org.example;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Properties; /**
 * Класс выполняющий автоматическое внедрение зависимостей в поля помеченные
 * аннотацией @AutoInjectable
 */
public class Injector {
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
