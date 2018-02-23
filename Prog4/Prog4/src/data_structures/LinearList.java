/*  Jose Carlos Gomez
    cssc0898
*/

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinearList<E extends Comparable<E>> implements LinearListADT<E> {

	private node<E> head, tail;
	private int currentSize;
	private long modCounter;
	
	public class node<T>{
		T data;
		node<T> next, prev;
		node(T obj){
			data = obj;
			next = prev = null;
		}
	}
	
	public LinearList() {
		head = tail = null;
		int currentSize = 0;
		long modCounter = 0;
	}
	
	//Adds the Object obj to the beginning of list and returns true if the list is not full.
	//returns false and aborts the insertion if the list is full.
	public boolean addFirst(E obj) {
		node<E> newNode = new node<E>(obj);
		if(head == null)
			head = tail = newNode;
		else {
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		}
		currentSize++;
		modCounter++;
		return true;
	} 

	//Adds the Object obj to the end of list and returns true if the list is not full.
	//returns false and aborts the insertion if the list is full..  
	public boolean addLast(E obj) { 
		node<E> newNode = new node<E>(obj);
		if(head == null)
			head = tail = newNode;
		else {
			newNode.prev = tail;
			tail.next = newNode;
			tail = newNode;
		}
		currentSize++;
		modCounter++;
		return true;
	} 
	

	//Removes and returns the parameter object obj in first position in list 
	//if the list is not empty,  
	//null if the list is empty. 
	public E removeFirst() {
		if(isEmpty()) 
			return null;
		E tmp = head.data;
		if(currentSize == 1)
			clear();
		else {
			head.next.prev = null;
			head = head.next;
			modCounter++;
			currentSize--;
		}
		return tmp;
	}

	//Removes and returns the parameter object obj in last position in list 
	//if the list is not empty, 
	//null if the list is empty. 
	public E removeLast() {
		if(isEmpty()) 
			return null;
		E tmp = tail.data;
		if(currentSize == 1)
			clear();
		else {
			tail.prev.next = null;
			tail = tail.prev;
			modCounter++;
			currentSize--;
		}
		return tmp;
	}

	//Removes and returns the parameter object obj from the list if the list 
	//contains it, null otherwise. The ordering of the list is preserved.  The list 
	//may contain duplicate elements.  This method removes and returns the first 
	//matching element found when traversing the list from first position.
	//Note that you may have to shift elements to fill in the slot where the deleted 
	//element was located.
	public E remove(E obj) {
		node<E> current = head;
		while(current != null && obj.compareTo(current.data)!= 0)
			current = current.next;
		if(current == null) 
			return null;
		if(current == head) 
			return removeFirst();
		if(current == tail)
			return removeLast();
		current.prev.next = current.next;
		current.next.prev = current.prev;
		currentSize--;
		modCounter++;
		return current.data;
	}    

	//Returns the first element in the list, null if the list is empty.
	//The list is not modified.
	public E peekFirst() {
		if(isEmpty())
			return null;
		return head.data;
	}

	//Returns the last element in the list, null if the list is empty.
	//The list is not modified.
	public E peekLast() {
		if(isEmpty()) 
			return null;
		return tail.data;
	}                 

	//Returns true if the parameter object obj is in the list, false otherwise.
	//The list is not modified.
	public boolean contains(E obj) { 
		return find(obj) != null;
	}

	//Returns the element matching obj if it is in the list, null otherwise.
	//In the case of duplicates, this method returns the element closest to front.
	//The list is not modified.
	public E find(E obj) {
		E tmp;
		Iterator<E> iterator = iterator();
		while(iterator.hasNext()) {
			if((obj).compareTo(tmp = iterator.next()) == 0)
				return tmp;
		}
		return null; 
	}    

	//The list is returned to an empty state.
	public void clear() {
		head = tail = null;
		currentSize = 0;
		modCounter++;
	}

	//Returns true if the list is empty, otherwise false
	public boolean isEmpty() { 
		return currentSize == 0;
	}

	//Returns true if the list is full, otherwise false
	public boolean isFull() { 
		return false;
	}

	//Returns the number of Objects currently in the list.
	public int size() {
		return currentSize;
	}

	//Returns an Iterator of the values in the list, presented in
	//the same order as the underlying order of the list. (front first, rear last)
	public Iterator<E> iterator() {
		return new IteratorHelper();
		}
		class IteratorHelper implements Iterator<E>{
			private node<E> iterPtr;
			private long modCheck;
			
			public IteratorHelper() {
				modCheck = modCounter;
				iterPtr = head;
			}
			
			public boolean hasNext() {
				if(modCheck != modCounter)
					throw new ConcurrentModificationException();
				return iterPtr != null;
			}
			
			public E next() {
				if(!hasNext())
					throw new NoSuchElementException();
				E temp = iterPtr.data;
				iterPtr = iterPtr.next;
				return temp;
			}
			
			public void remove() {
				throw new UnsupportedOperationException();
			}
		}
	}