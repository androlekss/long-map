package de.comparus.opensource.longmap;

import java.util.*;

public class LongMapImpl<V> implements LongMap<V> {

    private HashNode<Long, V>[] hashTable;
    private int size;
    private float threshold;

    public LongMapImpl() {
        hashTable = new HashNode[16];
        threshold = hashTable.length * 0.75F;

    }

    public V put(long key, V value) {

        if (size + 1 >= threshold) {
            resize();
        }

        HashNode<Long, V> newNode = new HashNode<>(key, value);

        int hash = hash(key);
        int index = indexFor(hash, hashTable.length);


        if (hashTable[index] == null) {

            addNode(index, newNode);
            return value;
        }

        List<HashNode<Long, V>> hashNodes = hashTable[index].getNodes();
        for (HashNode<Long, V> h : hashNodes) {
            if (keyExist(h, newNode.key)) {
                h.value = newNode.value;
                return value;
            }
        }

        hashNodes.add(newNode);
        ++size;

        return value;
    }

    private void addNode(int i, HashNode<Long, V> hashNode) {
        hashTable[i] = new HashNode<Long, V>(null, null);
        hashTable[i].getNodes().add(hashNode);
        ++size;

    }


    public V get(long key) {

        int hash = hash(key);
        int index = indexFor(hash, hashTable.length);
        if (hashTable[index] == null) {
            return null;
        }

        List<HashNode<Long, V>> hashNodes = hashTable[index].getNodes();
        for (HashNode<Long, V> h : hashNodes) {
            if (keyExist(h, key)) {
                return h.value;
            }
        }

        return null;
    }

    public V remove(long key) {

        int hash = hash(key);
        int index = indexFor(hash, hashTable.length);
        if (hashTable[index] == null) {
            return null;
        }

        List<HashNode<Long, V>> hashNodes = hashTable[index].getNodes();
        Iterator<HashNode<Long, V>> iterator = hashNodes.iterator();
        while (iterator.hasNext()) {
            HashNode<Long, V> temp = iterator.next();
            if (keyExist(temp, key)) {
                iterator.remove();
                --size;
                return temp.value;

            }
        }


        return null;
    }

    private boolean keyExist(HashNode<Long, V> hashNodeFromList, Long key) {

        return hashNodeFromList.key.equals(key);
    }


    public boolean isEmpty() {

        return size == 0;
    }

    public boolean containsKey(long key) {
        long[] keys = keys();
        for(long k : keys){
            if(k == key){
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(V value) {

        V[] values = values();
        for(V v : values){
            if(v == value){
                return true;
            }
        }
        return false;

    }

    public long[] keys() {
        List<Long> longList = new ArrayList<>();
        for (HashNode<Long, V> hashNode : hashTable) {
            if (hashNode != null)
                for (HashNode<Long, V> hashNode1 : hashNode.getNodes()) {
                    longList.add(hashNode1.key);
                }

        }

        long[] keys = new long[longList.size()];
        for (int i = 0; i < keys.length; i++)
            keys[i] = longList.get(i);
        return keys;

    }

    public V[] values() {

        List<V> longList = new ArrayList<>();
        for (HashNode<Long, V> hashNode : hashTable) {
            if (hashNode != null)
                for (HashNode<Long, V> hashNode1 : hashNode.getNodes()) {
                    longList.add(hashNode1.value);
                }

        }

        V[] values = (V[]) (new Object[longList.size()]);
        return longList.toArray(values);
    }

    public long size() {
        return size;
    }

    public void clear() {

        if (hashTable != null && size > 0) {
            size = 0;
            for (int i = 0; i < hashTable.length; ++i)
                hashTable[i] = null;
        }
    }

    private void resize() {

        HashNode<Long, V>[] oldTable = hashTable;

        int oldCap = oldTable.length;
        float oldthreshold = threshold;

        float newThreshold = oldthreshold * 2;
        int newSize = 0;

        HashNode<Long, V>[] newHashTable = new HashNode[oldCap * 2];

        long[] keys = keys();

        for (long k : keys) {

            V value = get(k);
            HashNode<Long, V> newNode = new HashNode<>(k, value);

            int hash = hash(k);
            int index = indexFor(hash, newHashTable.length);

            if (newHashTable[index] == null) {

                newHashTable[index] = new HashNode<Long, V>(null, null);
                newHashTable[index].getNodes().add(newNode);
                ++newSize;

            } else {

                List<HashNode<Long, V>> hashNodes = newHashTable[index].getNodes();
                hashNodes.add(newNode);
                ++newSize;
            }

        }

        hashTable = newHashTable;
        threshold = newThreshold;
        size = newSize;


    }

    private int hash(Long l) {
        int h = (int) (l ^ (l >>> 32));
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    private int indexFor(int h, int length) {
        return h & (length - 1);
    }

    private class HashNode<Long, V> {
        private List<HashNode<Long, V>> nodes = new LinkedList<>();
        private Long key;
        private V value;

        public HashNode(Long key, V value) {
            this.key = key;
            this.value = value;
        }

        public List<HashNode<Long, V>> getNodes() {
            return nodes;
        }

        public void setNodes(List<HashNode<Long, V>> nodes) {
            this.nodes = nodes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HashNode<?, ?> hashNode = (HashNode<?, ?>) o;
            return Objects.equals(key, hashNode.key);
        }

        @Override
        public int hashCode() {

            return Objects.hash(key);
        }


    }

}
