package com.evv;

import java.util.Objects;

/**
 * Класс, реализующий функциональность по хранению объектов параметризованного типа T. <br>
 * Внутренним источником данных служит связанный список. <br>
 * Допускается хранение неограниченного количества элементов null. <br>
 * Гарантируется сохранение порядка добавления элементов в список.
 */
public class EvvLinkedList<T> {

    /**
     * Размер списка
     */
    private int size = 0;

    /**
     * Ссылка на головную ячейку данных
     */
    private Node<T> head;

    /**
     * Ссылка на хвостовую ячейку данных
     */
    private Node<T> tail;

    /**
     * Внутренний класс для хранения полей ячейки данных
     */
    private static class Node<T> {

        /**
         * Элемент данных
         */
        private T value;

        /**
         * Ссылка на последующую ячейку данных
         */
        private Node<T> prev;

        /**
         * Ссылка на предыдущую ячейку данных
         */
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * Создает пустой список.
     */
    public EvvLinkedList() {
    }

    /**
     * Добавляет новый элемент в конец списка.
     *
     * @param elem добавляемый элемент
     * @return true в случае успешного добавления элемента
     */
    public boolean add(T elem) {
        Node<T> node;
        if (size == 0) {
            node = new Node<>(elem, null, null);
            head = node;
        } else {
            node = new Node<>(elem, tail, null);
            tail.next = node;
        }
        tail = node;
        size++;
        return true;
    }

    /**
     * Удаляет первое вхождение указанного элемента из списка.
     *
     * @param elem элемент, подлежащий удалению
     * @return true если элемент был найден и удален
     */
    public boolean remove(T elem) {
        Node<T> nodeToRemove = findFistOccurrence(elem);
        if (nodeToRemove != null) {
            if (size == 1) { // значит удаляем единственный элемент
                head = null;
                tail = null;
            } else if (nodeToRemove == head) { // значит удаляем первый элемент
                nodeToRemove.next.prev = null;
                head = nodeToRemove.next;
            } else if (nodeToRemove == tail) { // значит удаляем последний элемент
                nodeToRemove.prev.next = null;
                tail = nodeToRemove.prev;
            } else { // значит удаляем элемент в середине
                nodeToRemove.prev.next = nodeToRemove.next;
                nodeToRemove.next.prev = nodeToRemove.prev;
            }
            size--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Возвращает элемент, располагающийся в списке по указанному индексу.
     *
     * @param index индекс запрашиваемого элемента
     * @return элемент, соответствующий переданному индексу
     * @throws IndexOutOfBoundsException если переданный индекс отрицательный или превосходит размер списка
     */
    public T get(int index) {
        Objects.checkIndex(index, size);
        return getNodeByIndex(index).value;
    }

    /**
     * Замещает элемент списка по указанному индексу.
     *
     * @param index индекс элемента списка, подлежащего замещению
     * @param elem  элемент для замены существующего в списке
     * @return замещенный элемент
     * @throws IndexOutOfBoundsException если переданный индекс отрицательный или превосходит размер списка
     */
    public T set(int index, T elem) {
        Objects.checkIndex(index, size);
        Node<T> node = getNodeByIndex(index);
        T oldElem = node.value;
        node.value = elem;
        return oldElem;
    }

    /**
     * Возвращает новый список из элементов списка с индексами от from включительно до to не включительно.
     *
     * @param from начальная граница диапазона индексов
     * @param to   конечная граница диапазона индексов
     * @return новый список
     * @throws IndexOutOfBoundsException если переданный диапазон индексов противоречивый или одна из границ диапазона
     *                                   превосходит размер списка
     */
    public EvvLinkedList<T> subList(int from, int to) {
        Objects.checkFromToIndex(from, to, size);
        EvvLinkedList<T> newList = new EvvLinkedList<>();

        Node<T> node = getNodeByIndex(from);
        for (int i = from; i < to; i++) {
            newList.add(node.value);
            node = node.next;
        }
        return newList;
    }

    /**
     * Возвращает размер списка
     *
     * @return размер списка
     */
    public int size() {
        return size;
    }

    private Node<T> findFistOccurrence(T elem) {
        Node<T> curNode;
        if (size == 0) {
            return null;
        } else {
            curNode = head;
        }
        for (int i = 0; i < size; i++) {

            if (curNode.value == null) {
                if (elem == null) {
                    return curNode;
                }
            } else if (curNode.value.equals(elem)) {
                return curNode;
            }
            curNode = curNode.next;
        }
        return null;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> curNode;
        if (index < size / 2) { // проход слева направо
            curNode = head;
            for (int i = 0; i < index; i++) {
                curNode = curNode.next;
            }
        } else {                // проход справа налево
            curNode = tail;
            for (int i = size - 1; i > index; i--) {
                curNode = curNode.prev;
            }
        }
        return curNode;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (this.size != ((EvvLinkedList<?>) o).size) return false;

        EvvLinkedList<T> that = (EvvLinkedList<T>) o;
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            T thisElem = node.value;
            T thatElem = that.get(i);
            if (!Objects.equals(thisElem, thatElem)) {
                return false;
            }
            node = node.next;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            Object e = node.value;
            result = 31 * result + (e == null ? 0 : e.hashCode());
            node = node.next;
        }
        return result;
    }
}
