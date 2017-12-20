package com.company;

import java.util.*;

@SuppressWarnings("unchecked")
public class CustomLinkedHashMap<K, V> implements Map {

    private class Bucket {

        int hash;
        private Entry head;

        private Bucket(Entry head, int hash) {
            this.head = head;
            this.hash = hash;
        }
    }

    public class Entry<K, V> implements Map.Entry<K, V> {

        private K key;
        private V value;
        private  Entry next;
        private Entry before;
        private Entry after;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public V setValue(Object value) {
            this.value = (V) value;
            return null;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    private class HashIndex {

        private int hash;
        private int index;

        public HashIndex(int hash, int index) {
            this.hash = hash;
            this.index = index;
        }
    }

    private int getHash(K key) {
        int hashCode = key.hashCode();
        return hashCode ^ (hashCode >>> 16);
    }

    private HashIndex getHashIndex(K key) {
        int hash = getHash(key);
        return new HashIndex(hash, hash & (capacity - 1));
    }

    private int capacity;
    private Object[] buckets;
    private float  loadFactor = 0.75f;
    private int count = 0;
    private Entry head = null;
    private Entry tail = null;

    private void initBuckets(Object[] buckets) {
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new Bucket(null, 0);
        }
    }

    public CustomLinkedHashMap() {
        capacity = 16;
        buckets = new Object[capacity];
        initBuckets(buckets);
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    private Entry<K, V> findByKey(K key) {
        HashIndex hashIndex = getHashIndex(key);
        if (buckets[hashIndex.index] != null) {
            Entry link = ((Bucket) buckets[hashIndex.index]).head;
            while (link != null) {
                if (link.key.equals(key)) {
                    return link;
                }
                link = link.next;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return findByKey((K) key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < capacity; i++) {
            if (((Bucket) buckets[i]).head != null) {
                Entry link = ((Bucket) buckets[i]).head;
                while (link != null) {
                    if (link.value.equals(value)) {
                        return true;
                    }
                    link = link.next;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        Entry<K, V> result = findByKey((K) key);
        if (result != null) {
            return result.value;
        }
        return null;
    }

    private void checkSize() {
        if ((int)(count * loadFactor) >= capacity) {
            capacity *= 2;
            Object[] newBuckets = new Object[capacity];
            initBuckets(newBuckets);
            Entry<K, V> link = head;
            while (link != null) {
                link.next = null;
                addToHashTable(newBuckets, getHashIndex(link.key), link);
                link = link.after;
            }
            buckets = newBuckets;
        }
    }

    private Entry addToHashTable(Object[] buckets, HashIndex hashIndex, Entry entry) {
        Bucket bucket = (Bucket) buckets[hashIndex.index];
        Entry link = bucket.head;
        while (link != null) {
            if (entry.key.equals(link.key)) {
                link.setValue(entry.value);
                return entry;
            }
            if (link.next == null) {
                link.next = entry;
                return entry;
            }
            link = link.next;
        }
        bucket.head = entry;
        return entry;
    }

    private void addToLinkedList(Entry entry) {
        if (head == null) {
            head = entry;
            tail = entry;
        } else {
            tail.after = entry;
            entry.before = tail;
            tail = entry;
        }
    }

    @Override
    public Entry put(Object key, Object value) {
        checkSize();
        HashIndex hashIndex = getHashIndex((K) key);
        Entry entry = addToHashTable(buckets, hashIndex, new Entry(key, (V) value));
        addToLinkedList(entry);
        count++;
        return entry;
    }

    private void delFromTable(Bucket bucket, Entry previous, Entry entry) {
        if (previous == null) {
            bucket.head = bucket.head.next;
        } else {
            previous.next = entry.next;
        }
    }

    private void delFromList(Entry entry) {
        if (head == tail) {
            head = null;
            tail = null;
            return;
        }
        if (head == entry) {
            head.after.before = null;
            head = head.after;
            return;
        }
        if (tail == entry) {
            tail.before.after = null;
            tail = tail.before;
            return;
        }
        entry.before.after = entry.after;
        entry.after.before = entry.before;
    }

    @Override
    public Object remove(Object key) {
        Bucket bucket = ((Bucket) buckets[getHashIndex((K) key).index]);
        Entry link = bucket.head;
        Entry previous = null;
        while (link != null) {
            if (link.key.equals(key)) {
                delFromTable(bucket, previous, link);
                delFromList(link);
                count--;
                return link;
            }
            previous = link;
            link = link.next;
        }
        return null;
    }


    @Override
    public void putAll(Map m) {
        for (Object entry : m.entrySet()) {
            Entry<K,V> castedEntry = (Entry<K,V>)entry;
            put(castedEntry.key, castedEntry.value);
        }
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        capacity = 16;
        buckets = new Object[capacity];
        initBuckets(buckets);
        count = 0;
    }

    @Override
    public Set keySet() {
        Set<K> result = new LinkedHashSet<>();
        Entry link = head;
        while (link != null) {
            result.add((K)link.getKey());
            link = link.after;
        }
        return result;
    }

    @Override
    public Collection values() {
        Collection<V> result = new ArrayList<>();
        Entry link = head;
        while (link != null) {
            result.add((V)link.getValue());
            link = link.after;
        }
        return result;
    }

    @Override
    public Set<Entry> entrySet() {
        Set<Entry> result = new LinkedHashSet<>();
        Entry link = head;
        while (link != null) {
            result.add(link);
            link = link.after;
        }
        return result;
    }
}
