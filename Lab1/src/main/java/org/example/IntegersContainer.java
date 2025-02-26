package org.example;

/**
 * Задание: Создать класс контейнер, позволяющий хранить произвольное количество целых чисел.
 * Использование встроенных коллекций запрещено.
 * Задание можно реализовать с помощью массива или связанного списка.
 * Контейнер должен позволять добавлять, извлекать, удалять элементы
 * @author Илья Морозов
 */


/**
 * Класс IntegersContainer представляет собой контейнер для хранения массива целых чисел.
 * В нём реализованы методы добавления, удаления, получения и вывода элементов массива.
 */

public class IntegersContainer {
    /** Поле массив */
    private int[] array;

    /** Поле размер массива */
    private int size;

    /**
     * Конструктор по умолчанию
     */
    public IntegersContainer() {
        array = new int [5];
        size = 0;
    }


    /**
     * Добавляет элемент в контейнер. Если массив заполнен, его размер увеличивается функцией resizeArray()
     * @param data элемент, который необходимо добавить в контейнер
     */
    public void addElement(int data)
    {
        if (size == array.length)
        {
            resizeArray();
        }
        array[size++] = data;
    }

    /**
     * Возвращает элемент по указанному индексу
     *
     * @param index индекс элемента, который нужно вернуть
     * @return элемент по указанному индексу
     * @throws IndexOutOfBoundsException если индекс выходит за пределы массива.
     */
    public int getElement(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index " + index + ", size " + size);

        return array[index];
    }


    /**
     * Удаляет элемент по указанному индексу. Все последующие элементы сдвигаются на одну позицию влево.
     * @param index индекс элемента, который мы хотим удалить
     * @throws IndexOutOfBoundsException если индекс выходит за пределы массива
     */
    public void removeElement(int index)
    {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index " + index + ", size " + size);

        for (int i = index; i < size; i++)
            array[i] = array[i+1];

        size--;
    }


    /**
     * Увеличивает размер массива в два раза
     */
    public void resizeArray()
    {
        int newSize = array.length * 2;
        int[] newArray = new int[newSize];

        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    /**
     * Вывод всех элементов массива на экран
     */
    public void printArray()
    {
        for (int i = 0; i < size; i++)
            System.out.print(array[i] + " ");
        System.out.println();
    }

    /**
     * Метод для базового тестирования функциональности класса.
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args)
    {
        IntegersContainer container = new IntegersContainer();

        container.addElement(10);
        container.addElement(20);
        container.addElement(30);

        System.out.println("Array elements: ");
        container.printArray();

        System.out.println("Deleting 1nd element = " + container.getElement(1) + "...");
        container.removeElement(1);

        System.out.println("Array after delete operation: ");
        container.printArray();


    }
}

