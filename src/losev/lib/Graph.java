package losev.lib;
import java.util.Iterator;

public class Graph implements Iterable<Bag<Integer>> {

	private final int V;
	private int E;
	private Bag<Integer>[] adj;

	/**
	 * Створюємо порожній граф розмірності V ініціалізуємо масив порожніми
	 * списками типу Bag
	 * 
	 * @param V
	 *            - кількість вершин
	 */
	@SuppressWarnings("unchecked")
	public Graph(int V) {
		this.V = V;
		adj = (Bag<Integer>[]) new Bag[V];
		for (int v = 0; v < V; v++)
			adj[v] = new Bag<Integer>();
	}

	/**
	 * додаємо ребро між двома вершинами
	 * 
	 * @param v
	 *            - вершина
	 * @param w
	 *            - вершина
	 */
	public void addEdge(int v, int w) {
		adj[v].add(w);
		adj[w].add(v);
		E++;
	}

	/**
	 * @param v
	 *            - вершина графу
	 * @return - ступінь вершини графу v
	 */
	public int degree(int v) {
		return adj[v].size();
	}

	/**
	 * ітератор по вершинам суміжним з вершиною v
	 * 
	 * @param v
	 *            - вершина
	 * @return - ітератор по мішку суміжних з v вершин
	 */
	public Iterable<Integer> adj(int v) {
		return adj[v];
	}

	/**
	 * @return - кількість вершин
	 */
	public int V() {
		return V;
	}

	/**
	 * @return - кількість ребер
	 */
	public int E() {
		return E;
	}

	public Graph clone(){
		Graph clone = new Graph(V);
		for(int i=0; i<V; i++)
			for(int j: adj(i))
				clone.addEdge(i, j);
		return clone;
	}
	
	@Override
	public Iterator<Bag<Integer>> iterator() {
		return new VerticiesIterator();
	}

	private class VerticiesIterator implements Iterator<Bag<Integer>> {
		private int current = 0;

		@Override
		public boolean hasNext() {
			return current < V;
		}

		@Override
		public Bag<Integer> next() {
			return adj[current++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}
