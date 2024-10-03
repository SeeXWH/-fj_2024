import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.example.linkedList.CustomLinkedList;

public class Main {
    public static void main(String[] args) {
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        list.add(1);
        System.out.println("Get (0): " + list.get(0)); // 1

        list.remove(0);
        list.add(2);
        list.add(3);
        System.out.println("Contains (2): " + list.contains(2)); // true

        List<Integer> newList = Arrays.asList(4, 5, 6);
        list.addAll(newList);

        System.out.print("List contents: ");
        for (int i = 0; i < list.getSize(); i++) {
            System.out.print(list.get(i) + " "); // 2 3 4 5 6
        }

        Stream<Integer> stringStream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        CustomLinkedList<Integer> linkedList = CustomLinkedList.fromStream(stringStream);
        System.out.println(linkedList.toString());
    }
}