package losev.lib;
import java.util.Iterator;

public class Digraph implements Iterable<Bag<Integer>> {

	private final int V;
	private int E;
	private Bag<Integer>[] adj;

	@SuppressWarnings("unchecked")
	public Digraph(int V) {
		this.V = V;
		adj = (Bag<Integer>[]) new Bag[V];
		for (int v = 0; v < V; v++)
			adj[v] = new Bag<Integer>();
	}

	public void addEdge(int v, int w) {
		adj[v].add(w);
		E++;
	}

	public int outdegree(int v) {
		return adj[v].size();
	}

	public Iterable<Integer> adj(int v) {
		return adj[v];
	}
	
	public Digraph clone(){
		Digraph clone = new Digraph(V);
		for(int i=0; i<V; i++)
			for(int j: adj(i))
				clone.addEdge(i, j);
		return clone;
	}

	public int V() {
		return V;
	}

	public int E() {
		return E;
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
