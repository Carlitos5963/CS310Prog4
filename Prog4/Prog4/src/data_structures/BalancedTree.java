/*  Jose Carlos Gomez
    cssc0898
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.TreeMap;

public class BalancedTree<K extends Comparable<K>, V> implements DictionaryADT<K, V> {

	private TreeMap<K,V> balancedTree;
	private K key;
	private V value;


	public BalancedTree() {
		balancedTree = new TreeMap();
		key = null;
		value = null;
	}


	public boolean contains(K key) {
		return balancedTree.containsKey(key);
	}

	public boolean add(K key, V value) {
		if(contains(key))
			return false;
		balancedTree.put(key, value);
		return true;
	}

	public boolean delete(K key) {
		if(!contains(key))
			return false;
		balancedTree.remove(key);
		return true;
	}

	public V getValue(K key) {
		return balancedTree.get(key);
	}

	public K getKey(V value) {
		Iterator<K> itrK = keys();
		Iterator<V> itrV = values();
		while(itrK.hasNext() && itrV.hasNext()) {
			this.key = itrK.next();
			this.value = itrV.next();
			if(((Comparable<V>)this.value).compareTo(value) == 0)
				return key;
		}
		return null;
	}

	public int size() {
		return balancedTree.size();
	}


	public boolean isFull() {
		return false;
	}

	public boolean isEmpty() {
		return balancedTree.size() == 0;
	}

	public void clear() {
		balancedTree.clear();
	}

	public Iterator<K> keys() {
		return balancedTree.keySet().iterator();
	}


	public Iterator<V> values() {
		return balancedTree.values().iterator();
	}

}
