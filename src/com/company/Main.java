package com.company;

import java.util.Map;
import java.util.Random;

@SuppressWarnings("unchecked")
public class Main {

    public static void main(String[] args) {

        CustomLinkedHashMap<String, Integer> map = new CustomLinkedHashMap<>();

        Random random = new Random(42);
        for (int i = 0; i < 50; i++) {
            map.put("string" + i, i);
        }


        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println("Size Test: size: " + map.size());
        String testString = "string25";
        System.out.printf("Get Test: get(\"%s\"): " + map.get(testString) + "\n", testString);
        testString = "other string";
        System.out.printf("Get Test: get(\"%s\"): " + map.get(testString) + "\n", testString);
        testString = "string5";
        System.out.printf("Contains key Test: ContainsKey(\"%s\"): " + map.containsKey(testString) + "\n", testString);
        testString = "other string";
        System.out.printf("Contains key Test: ContainsKey(\"%s\"): " + map.containsKey(testString) + "\n", testString);
        int testInt = 13;
        System.out.printf("Contains value Test (value: %d): " + map.containsValue(testInt) + "\n", testInt);
        testInt = 120;
        System.out.printf("Contains value Test (value: %d): " + map.containsValue(testInt) + "\n", testInt);
        testString = "string0";
        System.out.printf("Remove by key Test: remove(\"%s\"); value: %s\n", testString, map.remove(testString));
        testInt = 0;
        System.out.printf("Contains value Test (value: %d): " + map.containsValue(testInt) + "\n", testInt);

        Map<String, Integer> map2 = new CustomLinkedHashMap<>();
        for (int i = 200; i < 250; i++) {
            map2.put("string" + i, i);
        }
        map.putAll(map2);

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }
}
