package org.example.linkedList;

import lombok.Getter;

import java.util.List;
import java.util.stream.Stream;

public class CustomLinkedList<T> {
    private static class Node<T> {

        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    private Node<T> head;
    @Getter
    private int size;



    public void add(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }

        if (index == 0) {
            T removedData = head.data;
            head = head.next;
            size--;
            return removedData;
        }

        Node<T> previous = head;
        for (int i = 0; i < index - 1; i++) {
            previous = previous.next;
        }

        Node<T> current = previous.next;
        previous.next = current.next;
        size--;
        return current.data;
    }

    public boolean contains(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    public static <T> CustomLinkedList<T> fromStream(Stream<T> stream) {
        return stream.reduce(new CustomLinkedList<>(), (list, element) -> {
            list.add(element);
            return list;
        }, (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        });
    }

    private void addAll(CustomLinkedList<T> otherList) {
        Node<T> current = otherList.head;
        while (current != null) {
            this.add(current.data);
            current = current.next;
        }
    }




}
