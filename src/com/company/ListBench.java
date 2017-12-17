package com.company;

import java.util.*;

public class ListBench {

    private static LinkedHashMap<Integer, Integer> utilMap1M = new LinkedHashMap<>();
    private static CustomLinkedHashMap<Integer, Integer> customMap1M = new CustomLinkedHashMap<>();

    public static void main(String args[]) {
        System.out.println("-------------------------------------------------");
        customHashMapCreate1M();
        hashMapCreate1M();

        System.out.println("-------------------------------------------------");
        customHashMapGet1M();
        utilMapGet1M();

        System.out.println("-------------------------------------------------");
        customHashMapRemove1M();
        utilMapRemove1M();

    }

    public static void customHashMapCreate1M() {
        long startFreeMem = Runtime.getRuntime().freeMemory();
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            customMap1M.put(i,i);
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("customMap1M create 1 million execution time: " + (endTime - startTime) + "ms. Current size: " + customMap1M.size());
        System.out.println("customMap1M passes " + (startFreeMem-Runtime.getRuntime().freeMemory()));
    }

    public static void hashMapCreate1M() {
        long startFreeMem = Runtime.getRuntime().freeMemory();
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            utilMap1M.put(i,i);
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("utilMap1M create 1 million execution time: " + (endTime - startTime) + "ms. Current size: " + utilMap1M.size());
        System.out.println("utilMap1M passes " + (startFreeMem-Runtime.getRuntime().freeMemory()));
    }

    public static void customHashMapRemove1M() {
        long startFreeMem = Runtime.getRuntime().freeMemory();
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            customMap1M.remove(i);
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("customMap1M remove 1 million execution time: " + (endTime - startTime) + "ms. Current size: " + customMap1M.size());
        System.out.println("customMap1M passes " + (startFreeMem-Runtime.getRuntime().freeMemory()));
    }

    public static void utilMapRemove1M() {
        long startFreeMem = Runtime.getRuntime().freeMemory();
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            utilMap1M.remove(i);
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("utilMap1M remove 1 million execution time: " + (endTime - startTime) + "ms. Current size: " + utilMap1M.size());
        System.out.println("utilMap1M passes " + (startFreeMem-Runtime.getRuntime().freeMemory()));
    }

    public static void customHashMapGet1M() {
        long startFreeMem = Runtime.getRuntime().freeMemory();
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            customMap1M.get(i);
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("customMap1M get 1 million execution time: " + (endTime - startTime) + "ms. Current size: " + customMap1M.size());
        System.out.println("customMap1M passes " + (startFreeMem-Runtime.getRuntime().freeMemory()));
    }

    public static void utilMapGet1M() {
        long startFreeMem = Runtime.getRuntime().freeMemory();
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            utilMap1M.get(i);
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("utilMap1M get 1 million execution time: " + (endTime - startTime) + "ms. Current size: " + utilMap1M.size());
        System.out.println("utilMap1M passes " + (startFreeMem-Runtime.getRuntime().freeMemory()));
    }



}
