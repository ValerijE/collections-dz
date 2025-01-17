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

    /**
     * Создает пустой список с размером массива списка по умолчанию.
     */

    public EvvArrayList() {
        this.capacity = DEFAULT_CAPACITY;
        @SuppressWarnings("unchecked") // Аннотировано для возможности создания массива типа T[], т.к. нет конструкции new T[]
        T[] tempData = (T[]) new Object[this.capacity];
        this.data = tempData;
    }

    /**
     * Создает пустой список с заданным размером массива списка.
     *
     * @param  capacity  размер массива списка
     * @throws IllegalArgumentException если переданный размер массива списка отрицательный
     */
    public EvvArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Передан неверный размер массива списка: " + capacity);
        }
        this.capacity = capacity;
        @SuppressWarnings("unchecked") // Аннотировано для возможности создания массива типа T[], т.к. нет конструкции new T[]
        T[] tempData = (T[]) new Object[this.capacity];
        this.data = tempData;
    }

    /**
     * Добавляет новый элемент в конец списка.
     *
     * @param elem добавляемый элемент
     * @return true в случае успешного добавления элемента
     */
    public boolean add(T elem) {
        if ((size == capacity - 1) || (capacity == 0)) { // второе условие на случай если список был создан через конструктор с параметром capacity равным нулю
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
        int newSize = (capacity * 3) / 2 + 1;
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

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (this.size != ((EvvArrayList<?>) o).size) return false;

        EvvArrayList<T> that = (EvvArrayList<T>) o;
        for (int i = 0; i < size; i++) {
            T thisElem = data[i];
            T thatElem = that.get(i);
            if (!Objects.equals(thisElem, thatElem)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (int i = 0; i < size; i++) {
            Object e = data[i];
            result = 31 * result + (e == null ? 0 : e.hashCode());
        }
        return result;
    }
}
