import java.util.Iterator;

import losev.lib.Bag;
import losev.lib.Digraph;
import losev.lib.LinkedStack;

public class DigraphCycleFinder {

	// Has small tester

	private OpenNodeLinkedList eulerCycle;
	private LinkedStack<Integer> hamiltonCycle;
	private boolean[][] marked;
	private int[] numOfNotMarked;
	private boolean[] reachable;
	private int numOfNotReached;

	public DigraphCycleFinder(Digraph g) {
		reachable = new boolean[g.V()];
		int i = 0;
		boolean isEulerian = true;
		boolean isHamiltonian = true;
		Degrees deg = new Degrees(g);
		for (; i < g.V(); i++) {
			if (deg.outdegree(i) == 0 || deg.indegree(i) == 0) {
				isHamiltonian = false;
			} else {
				dfs(g, i);
				break;
			}
		}
		for (int j = i; j < g.V(); j++) {
			if ((deg.outdegree(j) != deg.indegree(j)) || (!reachable[j] && deg.indegree(i) != 0)) {
				isEulerian = false;
				break;
			}
		}
		if (isEulerian)
			eulerCycle(g);
		if (isHamiltonian)
			hamiltonCycle(g);
	}

	private void dfs(Digraph g, int v) {
		reachable[v] = true;
		for (int w : g.adj(v)) {
			if (!reachable[w]) {
				dfs(g, w);
			}
		}
	}

	public Iterable<Integer> eulerCycle() {
		return eulerCycle;
	}

	public Iterable<Integer> hamiltonCycle() {
		return hamiltonCycle;
	}

	private void eulerCycle(Digraph g) {
		marked = new boolean[g.V()][];
		numOfNotMarked = new int[g.V()];
		int i = 0;
		for (Bag<Integer> x : g) {
			marked[i] = new boolean[x.size()];
			numOfNotMarked[i++] = x.size();
		}
		eulerCycle = new OpenNodeLinkedList(0);
		Iterator<Node> it = eulerCycle.nodeIterator();
		Node currentNode;
		while (it.hasNext()) {
			currentNode = it.next();
			while (!full(currentNode.value))
				partialEulerCycle(g, currentNode);
		}
	}

	private void partialEulerCycle(Digraph g, Node startNode) {
		int index = startNode.value;
		int i = index;
		int numOfEdgeInI;
		Iterable<Integer> x;
		do {
			numOfEdgeInI = 0;
			x = g.adj(i);
			for (int next : x) {
				if (!marked[i][numOfEdgeInI]) {
					marked[i][numOfEdgeInI] = true;
					numOfNotMarked[i]--;
					startNode = eulerCycle.insert(startNode, next);
					i = next;
					break;
				}
				numOfEdgeInI++;
			}
		} while (i != index);
	}

	private boolean full(int i) {
		return numOfNotMarked[i] <= 0;
	}

	private void hamiltonCycle(Digraph g) {
		numOfNotReached = g.V();
		reachable = new boolean[numOfNotReached];
		hamiltonCycle = new LinkedStack<Integer>();
		reachable[0] = true;
		numOfNotReached--;
		if (findHamiltonCycle(g, 0))
			hamiltonCycle.push(0);
		else
			hamiltonCycle = null;
	}

	private boolean findHamiltonCycle(Digraph g, int index) {
		if (full()) {
			for (int x : g.adj(index))
				if (x == 0) {
					hamiltonCycle.push(0);
					return true;
				}
			return false;
		}
		for (int x : g.adj(index))
			if (!reachable[x]) {
				reachable[x] = true;
				numOfNotReached--;
				if (findHamiltonCycle(g, x)) {
					hamiltonCycle.push(x);
					return true;
				}
				reachable[x] = false;
				numOfNotReached++;
			}
		return false;
	}

	private boolean full() {
		return numOfNotReached <= 0;
	}

	private class Node {

		private Node next;
		private int value;

		private Node(int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "[" + value + "]";
		}

	}

	private class OpenNodeLinkedList implements Iterable<Integer> {

		private Node first;
		private Node last;

		private OpenNodeLinkedList(int value) {
			first = new Node(value);
			last = null;
			first.next = last;
		}

		private Node add(int value) {
			Node newNode = new Node(value);
			if (last != null)
				last.next = newNode;
			last = newNode;
			return newNode;
		}

		private Node insert(Node start, int value) {
			if (start == null)
				return add(value);
			Node newNode = new Node(value);
			newNode.next = start.next;
			start.next = newNode;
			return newNode;
		}

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			res.append('[');
			for (int x : this) {
				res.append(x);
				res.append(", ");
			}
			int length = res.length();
			if (length > 2)
				res.delete(length - 2, length);
			res.append(']');
			return res.toString();
		}

		@Override
		public Iterator<Integer> iterator() {
			return new LinkedListIterator();
		}

		public Iterator<Node> nodeIterator() {
			return new NodeIterator();
		}

		private class LinkedListIterator implements Iterator<Integer> {
			private Node current = first;
			private boolean isFirst = true;

			@Override
			public boolean hasNext() {
				if (isFirst)
					return current != null;
				return current.next != null;
			}

			@Override
			public Integer next() {
				if (isFirst) {
					isFirst = false;
					return first.value;
				}
				current = current.next;
				return current.value;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

		}

		private class NodeIterator implements Iterator<Node> {
			private Node current = first;
			private boolean isFirst = true;

			@Override
			public boolean hasNext() {
				if (isFirst)
					return current != null;
				return current.next != null;
			}

			@Override
			public Node next() {
				if (isFirst) {
					isFirst = false;
					return first;
				}
				current = current.next;
				return current;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

		}

	}

	public static void main(String[] args) {
		Digraph g = new Digraph(11);
		g.addEdge(0, 1);
		g.addEdge(1, 2);
		g.addEdge(2, 3);
		g.addEdge(3, 4);
		g.addEdge(4, 5);
		g.addEdge(5, 3);
		g.addEdge(3, 1);
		g.addEdge(1, 4);
		g.addEdge(4, 2);
		g.addEdge(2, 0);
		g.addEdge(5, 6);
		g.addEdge(10, 0/* 5 */);
		g.addEdge(6, 7);
		g.addEdge(6, 9);
		g.addEdge(7, 8);
		g.addEdge(9, 7);
		g.addEdge(7, 10);
		g.addEdge(8, 9);
		g.addEdge(9, 10);
		g.addEdge(10, 6);
		DigraphCycleFinder e = new DigraphCycleFinder(g);
		System.out.println(e.eulerCycle());
		System.out.println(e.hamiltonCycle());
	}

}
