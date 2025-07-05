package hashmap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private double loadFac;
    private int defaultCapacity = 16;
    private int resizeFac = 2;
    Set<K> keyset = new HashSet<>();

    /** Constructors */
    public MyHashMap() {
        loadFac = 0.75;
        buckets = (Collection<Node>[])new Collection[defaultCapacity];
        for (int i = 0; i < defaultCapacity; i++) {
            buckets[i] = createBucket();
        }
    }

    public MyHashMap(int initialCapacity) {
        loadFac = 0.75;
        buckets = (Collection<Node>[])new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        loadFac = loadFactor;
        buckets = (Collection<Node>[])new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    @Override
    public Iterator<K> iterator() {
        return new MapIterator();
    }

    private class MapIterator implements Iterator<K> {
        private int count = 0;
        private int currBucketIndex = 0;
        private Iterator<Node> iter;

        public MapIterator() {
            iter = buckets[0].iterator();
        }

        @Override
        public boolean hasNext() {
            return count < size();
        }

        @Override
        public K next() {
            while (!iter.hasNext()) {
                currBucketIndex++;
                if (currBucketIndex > buckets.length - 1) {
                    throw new NoSuchElementException();
                }
                iter = buckets[currBucketIndex].iterator();
            }
            count++;
            return iter.next().key;
        }
    }

    @Override
    public void put(K key, V value) {
        // handle if key exists
        Node oldNode = getNode(key);
        if (oldNode != null) {
            oldNode.value = value;
            return;
        }

        double newSize = size() + 1;
        double bucketsCounts = buckets.length;
        // handle resize
        if ((newSize / bucketsCounts) >= loadFac) {
            int newBucketsCount = buckets.length * resizeFac;
            Collection<Node>[] newBuckets = (Collection<Node>[])new Collection[newBucketsCount];
            for (int i = 0; i < newBucketsCount; i++) {
                newBuckets[i] = createBucket();
            }
            for (Collection<Node> bucket:buckets) {
                for (Node node:bucket) {
                    int newHashCode = getHashCode(node.key, newBucketsCount);
                    newBuckets[newHashCode].add(node);
                }
            }
            buckets = newBuckets;
        }

        Node newNode = new Node(key, value);
        int hashCode = getHashCode(key);
        buckets[hashCode].add(newNode);
        keyset.add(key);
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        if (node != null) {
            return node.value;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return keyset.contains(key);
    }

    @Override
    public int size() {
        return keyset.size();
    }

    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
        keyset = new HashSet<>();
    }

    @Override
    public Set<K> keySet() {
        return keyset;
    }

    @Override
    public V remove(K key) {
        Node node = getNode(key);
        if (node != null) {
            int hashCode = getHashCode(key);
            buckets[hashCode].remove(node);
            keyset.remove(key);
            return node.value;
        }
        return null;
    }

    private int getHashCode(K key) {
        return getHashCode(key, buckets.length);
    }

    private int getHashCode(K key, int capacity) {
        return Math.abs(key.hashCode() % capacity);
    }

    private Node getNode(K key) {
        if (containsKey(key)) {
            int hashCode = getHashCode(key);
            Collection<Node> thisBucket = buckets[hashCode];
            for (Node node:thisBucket) {
                if (node.key.equals(key)) {
                    return node;
                }
            }
        }
        return null;
    }
}
