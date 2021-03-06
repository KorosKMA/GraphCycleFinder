package losev.lib;

import java.util.Iterator;

public class LinkedStack<Item> implements Iterable<Item>{
	
	private Node first = null;
	private int count=0;
	
	private class Node{
		Item item;
		Node next;
	}
	
	public void push(Item item) {
		Node oldFirst = first;
		first = new Node();
		first.item=item;
		first.next=oldFirst;
		count++;
	}

	public Item pop() {
		Item item = first.item;
		first = first.next;
		count--;
		return item;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return count;
	}

	@Override
	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item>{
		private Node current = first;
		@Override
		public boolean hasNext() {
			return current!=null;
		}

		@Override
		public Item next() {
			Item item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
						
		}
		
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append('[');
		for(Item x: this){
			res.append(x);
			res.append(", ");
		}
		int length = res.length();
		if(length>2)
			res.delete(length-2, length);
		res.append(']');
		return res.toString();
	}
	
}
