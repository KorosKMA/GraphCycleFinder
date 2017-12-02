import losev.lib.ArrayQueue;
import losev.lib.Digraph;

public class Degrees {

	private int[] indegrees;
	private int[] outdegrees;
	
	Degrees(Digraph g) {
		indegrees = new int[g.V()];
		outdegrees = new int[g.V()];
		for(int i=0; i<g.V(); i++){
			outdegrees[i] = g.outdegree(i);
			for(int j: g.adj(i)){
				indegrees[j]++;
			}
		}
	}

	int indegree(int v) {
		return indegrees[v];
	}

	int outdegree(int v) {
		return outdegrees[v];
	}

	Iterable<Integer> sources() {
		ArrayQueue<Integer> res = new ArrayQueue<Integer>();
		for(int i=0; i<indegrees.length; i++)
			if(indegrees[i]==0)
				res.enqueue(i);
		return res;
	}

	Iterable<Integer> sinks() {
		ArrayQueue<Integer> res = new ArrayQueue<Integer>();
		for(int i=0; i<outdegrees.length; i++)
			if(outdegrees[i]==0)
				res.enqueue(i);
		return res;
	}

	boolean isMap() {
		for(int x: outdegrees)
			if(x>1)
				return false;
		return true;
	}

	public static void main(String[] args){
		Digraph g = new Digraph(11);
		g.addEdge(8, 9);
		g.addEdge(1, 4);
		g.addEdge(2, 3);
		g.addEdge(2, 4);
		g.addEdge(6, 7);
		g.addEdge(0, 1);
		g.addEdge(0, 2);
		g.addEdge(1, 2);
		g.addEdge(1, 3);
		g.addEdge(7, 8);
		g.addEdge(7, 9);
		g.addEdge(3, 4);
		g.addEdge(3, 5);
		g.addEdge(4, 5);
		g.addEdge(6, 10);
		g.addEdge(5, 6);
		g.addEdge(5, 10);
		g.addEdge(7, 10);
		g.addEdge(6, 9);
		g.addEdge(9, 10);/**/
		Degrees deg = new Degrees(g);
		for(int i=0; i<g.V(); i++){
			System.out.println(i+":\n Indegree = "+deg.indegree(i)+";\n Outdegree= "+deg.outdegree(i)+";");
		}
		System.out.println("Sinks:\n "+deg.sinks());
		System.out.println("Sources:\n "+deg.sources());
		System.out.println("Is map: "+deg.isMap());
	}
	
}
