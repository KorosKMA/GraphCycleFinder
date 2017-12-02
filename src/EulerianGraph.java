import losev.lib.Graph;
import losev.lib.Point2D;
import princeton.lib.StdDraw;

public class EulerianGraph {

	private static final int DELAY = 500;

	private CycleFinder cf;
	private Point2D[] points;

	public EulerianGraph(Graph g, Point2D[] points) {
		if(g.V()!=points.length)
			throw new IllegalArgumentException();
		StdDraw.setXscale(0, 1);
		StdDraw.setYscale(0, 1);
		StdDraw.setPenRadius();
		this.cf = new CycleFinder(g);
		this.points = points.clone();
	}

	public void show(int delay) {
		Point2D previous = null;
		for (int x : cf.eulerCycle()) {
				StdDraw.setPenColor(StdDraw.GREEN);
			points[x].drawTo(previous);
				StdDraw.show(delay/2);
				StdDraw.setPenColor();
			points[x].drawTo(previous);
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.GREEN);
			points[x].draw();
				StdDraw.show(delay/2);
				StdDraw.setPenColor();
			points[x].draw();
				StdDraw.setPenRadius();
			previous = points[x];
		}
		StdDraw.show();
	}

	public static void main(String[] args){
		Graph g = new Graph(11);
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
		g.addEdge(9, 10);
		// |E| = 20
		Point2D[] points = {
			new Point2D(0.55, 0.65),	//0
			new Point2D(0.7, 0.7),		//1
			new Point2D(0.85, 0.65),	//2
			new Point2D(0.85, 0.5),		//3
			new Point2D(0.7, 0.45),		//4
			new Point2D(0.55, 0.5),		//5
			new Point2D(0.4, 0.5),		//6
			new Point2D(0.25, 0.55),	//7
			new Point2D(0.1, 0.5),		//8
			new Point2D(0.1, 0.4),		//9
			new Point2D(0.3, 0.35),		//10
		};
		/* = new Point2D[11];
		for(int i=0; i<11; i++)
			points[i] = new Point2D(Math.random(), Math.random());
		/**/
		EulerianGraph eg = new EulerianGraph(g, points);
		eg.show(DELAY);
	}

}
