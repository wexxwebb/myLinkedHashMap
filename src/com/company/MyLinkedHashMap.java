package com.company;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MyLinkedHashMap<K,V> implements Map {

    private float loadFactor = 0.75f;
    private Entry tail = null;
    private Entry head = null;
    private int capacity = 16;
    private int count = 0;
    private Object[] backets;

    public class Entry<K, V> implements Map.Entry {
        private int hash;
        private K key;
        private V value;
        private Entry next = null;
        private Entry before = null;
        private Entry after = null;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        private Entry(K key, V value, int hash) {
            this.key = key;
            this.value = value;
            this.hash = hash;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public Object setValue(Object value) {
            this.value = (V)value;
            return null;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "hash=" + hash +
                    ", key=" + key +
                    ", value=" + value +
                    ", next=" + next +
                    ", before=" + before +
                    ", after=" + after +
                    '}';
        }
    }

    public MyLinkedHashMap() {
        backets = new Object[capacity];
    }

    private int hash(Object key) {
        return key.hashCode() ^ (key.hashCode()>>>16);
    }

    private int indexFor(int hash) {
        return  hash & (capacity - 1);
    }

    public void show() {
        for (int i = 0; i < capacity; i++) {
            if (backets[i] != null) {
                Entry entry = (Entry) backets[i];
                while (entry != null) {
                    System.out.print(i + ": " + entry.key + " >> " + entry.value + "; ");
                    entry = entry.next;
                }
//                System.out.println();
            } else {
                System.out.println(backets[i]);
            }
        }
    }
//
//    public void showAsAdd() {
//        if (head != null) {
//            Entry entry = head;
//            while (entry != null) {
//                System.out.println(entry.key + " >> " + entry.value + "; ");
//                entry = entry.after;
//            }
//        } else {
//            System.out.println("LinkedHashMap is Empty");
//        }
//    }

    private void addIntoLinkedList(Entry entry) {
        tail.after = entry;
        entry.before = tail;
        tail = entry;
    }

    private void checkResize() {
        if ((int)(count * loadFactor) >= capacity) {
            //show();
            //System.out.println();
            capacity = capacity * 2;
            Object[] new_backets = new Object[capacity];
            count = 0;
            Set<Entry<K,V>> currentEntrySet = this.entrySet();
            head = null;
            tail = null;
            for (MyLinkedHashMap.Entry entry : currentEntrySet) {
                addToAnyArray(new_backets, (K)entry.key, (V)entry.value);
                count++;
            }
            backets = new_backets;
            //System.out.println("Rebuild LinkedHashMap; Up to " + capacity + " backets");
        }
    }

    private void addToAnyArray(Object[] backets, K key, V value) {
        int hash = hash(key);
        int index = indexFor(hash);

        Entry entry = new Entry(key, value, hash);
        if (head == null) {
            head = entry;
            tail = entry;
        }

        if (backets[index] == null) {
            backets[index] = entry;
            addIntoLinkedList(entry);
        } else {
            Entry tempFind = (Entry)backets[index];
            while (tempFind.next != null) {
                tempFind = tempFind.next;
            }
            tempFind.next = entry;
            addIntoLinkedList(entry);
        }
    }

    private Entry find(K key) {
        for (int i = 0; i < capacity; i++) {
            Entry temp = (Entry) backets[i];
            if (temp != null && temp.hash == hash(key)) {
                while (temp != null) {
                    if (key.equals(temp.getKey())) {
                        return temp;
                    }
                    temp = temp.next;
                }
            }
        }
        return null;
    }

    @Override
    public Object put(Object key, Object value) {
        Entry entry = find((K)key);
        if (entry != null) {
            entry.setValue((V)value);
        } else {
            checkResize();
            addToAnyArray(backets, (K)key, (V)value);
            count++;
        }
        return null;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (Entry entry : this.entrySet()) {
            if (entry.value.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object get(Object key) {
        Object result = find((K)key);
        if (result != null) {
            return ((Entry)result).getValue();
        } else {
            return null;
        }
    }

    @Override
    public Object remove(Object key) {
        for (int i = 0; i < capacity; i++) {
            Entry temp = (Entry)backets[i];
            Entry previous = null;
            if (temp != null) {
                //System.out.println(temp.hash + " >> " + hash(key));
            }
            if (temp != null && temp.hash == hash(key)) {
                while (temp != null) {
                    if (temp.getKey().equals(key)) {
                        if (previous == null) {
                            backets[i] = temp.next;
                            count--;
                            return temp.getValue();
                        } else {
                            previous.next = temp.next;
                            count--;
                            return temp.getValue();
                        }
                    }
                    previous = temp;
                    temp = temp.next;
                }
            }
        }
        return null;
    }

    @Override
    public void putAll(Map m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set keySet() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }

    public final class EntrySet implements Set {

        Entry head = null;
        Entry tail = null;
        Entry link = null;

        public EntrySet(Entry start) {
            while (start != null) {
                if (head == null) {
                    head = start;
                    tail = start;
                    link = start;
                } else {
                    tail.next = start;
                    tail = start;
                }
                start = start.after;
            }
        }

        class It implements Iterator {
            @Override
            public boolean hasNext() {
                if (link == null) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public Object next() {
                Entry result = link;

                link = link.next;
                return result;
            }
        }

        @Override
        public Spliterator spliterator() {
            return null;
        }

        @Override
        public int size() {
            return count;
        }

        @Override
        public boolean isEmpty() {
            return count == 0;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator iterator() {
            return new It();
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public Object[] toArray(Object[] a) {
            return new Object[0];
        }

        @Override
        public boolean add(Object o) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection c) {
            return false;
        }

        @Override
        public boolean addAll(Collection c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection c) {
            return false;
        }

        @Override
        public void clear() {

        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {

        return new EntrySet(this.head);
    }

    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        return null;
    }

    @Override
    public void forEach(BiConsumer action) {

    }

    @Override
    public void replaceAll(BiFunction function) {

    }

    @Override
    public Object putIfAbsent(Object key, Object value) {
        return null;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public boolean replace(Object key, Object oldValue, Object newValue) {
        return false;
    }

    @Override
    public Object replace(Object key, Object value) {
        return null;
    }

    @Override
    public Object computeIfAbsent(Object key, Function mappingFunction) {
        return null;
    }

    @Override
    public Object computeIfPresent(Object key, BiFunction remappingFunction) {
        return null;
    }

    @Override
    public Object compute(Object key, BiFunction remappingFunction) {
        return null;
    }

    @Override
    public Object merge(Object key, Object value, BiFunction remappingFunction) {
        return null;
    }
}
