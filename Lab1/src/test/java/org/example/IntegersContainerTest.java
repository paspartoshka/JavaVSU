package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IntegersContainerTest {

    private IntegersContainer container;

    @BeforeEach
    public void setup() { container = new IntegersContainer(); }

    /**
     * Тест добавления и получения элемента
     */
    @Test
    public void testAddNGetElement()
    {
        container.addElement(1);
        container.addElement(10);
        assertEquals(2, container.getArraySize());
        assertEquals(1, container.getElement(0));
        assertEquals(10, container.getElement(1));
    }


    /**
     * Тест удаления элемента
     */
    @Test
    public void testRemoveElement()
    {
        container.addElement(1);
        container.addElement(5);
        container.addElement(10);
        container.removeElement(1);
        assertEquals(2, container.getArraySize());
        assertEquals(1, container.getElement(0));
        assertEquals(10, container.getElement(1));
    }

    /**
     * Тест расширения массива
     * Массив расширяется вдвое, переменная size будет равна заполенному массиву
     */
    @Test
    public void testResizeArray()
    {
        for (int i = 0; i < 10; i++)
            container.addElement(i);

        container.resizeArray();
        assertEquals(10, container.getArraySize());
        assertEquals(20, container.getArrayLength());
    }

    /**
     * Тест вывода массива
     */
    @Test
    public void testPrintArray()
    {
        container.addElement(1);
        container.addElement(5);
        container.addElement(10);

        assertDoesNotThrow(() -> {
            container.printArray();
        });
    }

    /**
     * Выход за пределы для получения элемента
     */
    @Test
    public void testGetElementOutOfBounds()
    {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            container.getElement(0);
        });
    }

    /**
     * Выход за пределы для удаления элемента
     */
    @Test
    public void testRemoveElementOutOfBounds()
    {
        container.addElement(10);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            container.removeElement(1);
        });
    }
}