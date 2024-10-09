package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


@Slf4j
public class Main {


    public static void main(String[] args) {
        lesson2();
        lesson3();
    }

    public static void lesson2() {
        log.debug("Application started");
        ObjectMapper mapper = new ObjectMapper();
        String[] jsonFiles = {"city.json", "city-error.json"};

        for (String jsonFile : jsonFiles) {
            try {
                JsonNode rootNode = mapper.readTree(new File(jsonFile));
                if (rootNode.has("coords") && rootNode.has("slug")) {
                    String jsonString = rootNode.toString();
                    log.info(jsonString);
                    City city = mapper.readValue(jsonString, City.class);
                    log.info("Successfully parsed city from {}: {}", jsonFile, city);

                    try {
                        String xml = city.toXML();
                        Files.writeString(Path.of("city.xml"), xml, StandardCharsets.UTF_8);
                        log.info("XML saved to file: city.xml");
                    } catch (Exception e) {
                        log.error("Error converting city to XML or saving to file: {}", e.getMessage(), e);
                    }
                } else {
                    log.warn("Invalid JSON structure in file: {}. Missing 'coords' or 'slug' field.", jsonFile);
                }
            } catch (IOException e) {
                log.error("Error parsing JSON file: {} - {}", jsonFile, e.getMessage(), e);
            }
        }
        log.debug("Application ended");
    }
    public static void lesson3() {
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