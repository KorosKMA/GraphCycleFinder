package losev.lib;
import java.util.Iterator;

public class Graph implements Iterable<Bag<Integer>> {

	private final int V;
	private int E;
	private Bag<Integer>[] adj;

	/**
	 * ��������� ������� ���� ��������� V ���������� ����� ��������
	 * �������� ���� Bag
	 * 
	 * @param V
	 *            - ������� ������
	 */
	@SuppressWarnings("unchecked")
	public Graph(int V) {
		this.V = V;
		adj = (Bag<Integer>[]) new Bag[V];
		for (int v = 0; v < V; v++)
			adj[v] = new Bag<Integer>();
	}

	/**
	 * ������ ����� �� ����� ���������
	 * 
	 * @param v
	 *            - �������
	 * @param w
	 *            - �������
	 */
	public void addEdge(int v, int w) {
		adj[v].add(w);
		adj[w].add(v);
		E++;
	}

	/**
	 * @param v
	 *            - ������� �����
	 * @return - ������ ������� ����� v
	 */
	public int degree(int v) {
		return adj[v].size();
	}

	/**
	 * �������� �� �������� ������� � �������� v
	 * 
	 * @param v
	 *            - �������
	 * @return - �������� �� ���� ������� � v ������
	 */
	public Iterable<Integer> adj(int v) {
		return adj[v];
	}

	/**
	 * @return - ������� ������
	 */
	public int V() {
		return V;
	}

	/**
	 * @return - ������� �����
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
