package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


public class IntegersContainer {
    private int[] array;
    private int size;

    public IntegersContainer() {
        array = new int [5];
        size = 0;
    }

    public void addElement(int data)
    {
        if (size == array.length)
        {
            resizeArray();
        }
        array[size++] = data;
    }

    public int getElement(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index " + index + ", size " + size);

        return array[index];
    }

    public void removeElement(int index)
    {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index " + index + ", size " + size);

        for (int i = index; i < size; i++)
            array[i] = array[i+1];

        size--;
    }

    public void resizeArray()
    {
        int newSize = array.length * 2;
        int[] newArray = new int[newSize];

        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    public void printArray()
    {
        for (int i = 0; i < size; i++)
            System.out.print(array[i] + " ");
        System.out.println();
    }

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

