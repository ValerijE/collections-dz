package com.evv;

import java.util.Arrays;
import java.util.Objects;

/**
 * Класс, реализующий функциональность по хранению объектов параметризованного типа T. <br>
 * Внутренним источником данных служит массив. <br>
 * Допускается хранение неограниченного количества элементов null. <br>
 * Гарантируется сохранение порядка добавления элементов в список.
 */
public class EvvArrayList <T> {

    /**
     * Размер списка по умолчанию
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Массив элементов списка
     */
    private T[] data;

    /**
     * Размер массива списка
     */
    private int capacity;

    /**
     * Размер списка
     */
    private int size = 0;

    @SuppressWarnings("unchecked")
    public EvvArrayList() {
        this.capacity = DEFAULT_CAPACITY;
        this.data = (T[]) new Object[this.capacity];
    }

    @SuppressWarnings("unchecked")
    public EvvArrayList(int capacity) {
        this.capacity = capacity;
        this.data = (T[]) new Object[this.capacity];
    }

    /**
     * Добавляет новый элемент в конец списка.
     *
     * @param elem добавляемый элемент
     * @return true в случае успешного добавления элемента
     */
    public boolean add(T elem) {
        if (size == capacity - 1) {
            increaseSize();
        }
        data[size++] = elem;
        return true;
    }

    /**
     * Удаляет первое вхождение указанного элемента из списка.
     *
     * @param elem элемент, подлежащий удалению
     * @return true если элемент был найден и удален
     */
    public boolean remove(T elem) {
        int idx = findFistOccurrence(elem);
        if (idx != -1) {
            shiftDownDataTail(idx + 1);
            size--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Возвращает элемент, располагающийся в списке по указанному индексу.
     * @param index индекс запрашиваемого элемента
     * @return элемент, соответствующий переданному индексу
     * @throws IndexOutOfBoundsException если переданный индекс отрицательный или превосходит размер списка
     */
    public T get(int index) {
        Objects.checkIndex(index, size);
        return data[index];
    }

    /**
     * Замещает элемент списка по указанному индексу.
     * @param index индекс элемента списка, подлежащего замещению
     * @param elem элемент для замены существующего в списке
     * @return замещенный элемент
     * @throws IndexOutOfBoundsException если переданный индекс отрицательный или превосходит размер списка
     */
    public T set(int index, T elem) {
        Objects.checkIndex(index, size);
        T oldElem = data[index];
        data[index] = elem;
        return oldElem;
    }

    /**
     * Возвращает новый список из элементов списка с индексами от from включительно до to не включительно.
     * @param from начальная граница диапазона индексов
     * @param to конечная граница диапазона индексов
     * @return новый список
     * @throws IndexOutOfBoundsException если переданный диапазон индексов противоречивый или одна из границ диапазона
     * превосходит размер списка
     */
    public EvvArrayList<T> subList(int from, int to) {
        Objects.checkFromToIndex(from, to, size);
        int newListCapacity = (to - from) * 3 / 2 + 1;
        EvvArrayList<T> newList = new EvvArrayList<>(newListCapacity);
        for (int i = from; i < to; i++) {
            newList.add(data[i]);
        }
        return newList;
    }

    /**
     * Возвращает размер списка
     * @return размер списка
     */
    public int size() {
        return size;
    }


    private void increaseSize() {
        int newSize = (data.length * 3) / 2 + 1;
        data = Arrays.copyOf(data, newSize);
        capacity = newSize;
    }


    private int findFistOccurrence(T elem) {
        for (int i = 0; i < size; i++) {
            if (data[i] == null) {
                if (elem == null) {
                    return i;
                }
            } else if (data[i].equals(elem)) {
                return i;
            }
        }
        return -1;
    }

    private void shiftDownDataTail(int from) {
        for (int i = from; i < size; i++) {
            data[i - 1] = data[i];
        }
        data[size - 1] = null;
    }

}
