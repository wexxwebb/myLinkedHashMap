package com.company;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        MyLinkedHashMap<String, Integer> map = new MyLinkedHashMap<>();

        Random random = new Random(41);
        for (int i = 0; i < 50; i++) {
            map.put("string" + i, random.nextInt(100));
        }

        map.put("string45", 100);

//        System.out.println();
        map.show();
        System.out.println();

//        //чтение
        for (MyLinkedHashMap.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println();
        System.out.println("Size test: size: " + map.size());
        String testString = "string25";
        System.out.printf("Get test: get(\"%s\"): " + map.get(testString) + "\n", testString);
        testString = "other string";
        System.out.printf("Get test: get(\"%s\"): " + map.get(testString) + "\n", testString);
        testString = "string5";
        System.out.printf("Contains key test: ContainsKey(\"%s\"): " + map.containsKey(testString) + "\n", testString);
        testString = "other string";
        System.out.printf("Contains key test: ContainsKey(\"%s\"): " + map.containsKey(testString) + "\n", testString);
        int testInt = 100;
        System.out.printf("Contains value test (value: %d): " + map.containsValue(testInt) + "\n", testInt);
        testInt = 120;
        System.out.printf("Contains value test (value: %d): " + map.containsValue(testInt) + "\n", testInt);
        testString = "string47";
        System.out.printf("Remove by key test: remove(\"%s\"); value: %d\n", testString, map.remove((Object)testString));

    }
}
