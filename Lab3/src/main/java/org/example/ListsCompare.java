package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс для сравнения производительности Array List и Linked List.
 * Тестирование показывает время выполнения add, get, remove для заданного количества итераций
 */
public class ListsCompare {
    /**
     * Метод в котором создаются списки и вызывается фунцкия сравнения
     * @param args
     */
    public static void main(String[] args)
    {
        int iters = 500;

        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        compare(arrayList, iters, "Array List");
        compare(linkedList, iters, "Linked List");
    }

    /**
     * Метод сравнения на методах add, get, remove
     * @param list - Сам список, который мы передаем
     * @param iters - Количество итераций
     * @param listName - Название списка для отчетности
     */
    public static void compare(List<Integer> list, int iters, String listName)
    {
        long startTime = System.nanoTime();
        for (int i = 0; i < iters; i++)
        {
            list.add(i);
        }
        long addTimer = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        for (int i = 0; i < iters; i++)
        {
            list.get(i);
        }
        long getTimer = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        for (int i = iters - 1; i >= 0; i--)
        {
            list.remove(i);
        }
        long removeTimer = System.nanoTime() - startTime;

        System.out.println("Testing for " + listName + " on " + iters + " iterations");
        System.out.println("Add time = " + addTimer);
        System.out.println("Get time = " + getTimer);
        System.out.println("Remove time = " + removeTimer);
    }
}
