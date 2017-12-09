package com.company;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        MyLinkedHashMap<String, Integer> map = new MyLinkedHashMap<>();

        Random random = new Random(41);
        for (int i = 0; i < 50; i++) {
            map.put("string" + i, random.nextInt(100));
        }
//        System.out.println();
//        map.show();
//        System.out.println();

        //чтение
        for (MyLinkedHashMap.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println();
        // извлечение из LinkedHashMap
        System.out.println(map.get("string5"));

    }
}
