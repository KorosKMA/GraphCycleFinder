package losev.lib;

import princeton.lib.StdDraw;

public class Point2D implements Comparable<Point2D> {

	final double x;
	final double y;

	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void draw() {
		StdDraw.point(x, y);
	}

	public void drawTo(Point2D that) {
		if(that!=null)
			StdDraw.line(this.x, this.y, that.x, that.y);
	}
	
	public static void show(int delay){
		StdDraw.show(delay);
	}

	public static void show(){
		StdDraw.show();
	}

	public int compareTo(Point2D that) {
		if (this.y < that.y)
			return -1;
		if (this.y > that.y)
			return 1;
		if (this.x < that.x)
			return 1;
		if (this.x > that.x)
			return -1;
		return 0;
	}

	@Override
	public boolean equals(Object other) {
		if (other.getClass() == Point2D.class)
			if (x == ((Point2D) other).x && y == ((Point2D) other).y)
				return true;
		return false;
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}