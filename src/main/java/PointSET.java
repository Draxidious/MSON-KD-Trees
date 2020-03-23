/******************************************************************************
 *  Name:    J.D. DeVaughn-Brown
 *  NetID:   jddevaug
 *  Precept: P05
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *  
 *  Compilation:  javac-algs4 PointSET.java
 *  Execution:    java-algs4 PointSET
 *  Dependencies: Point2D.java RectHV.java 
 * 
 *  Description: Represents a set of points in the unit square 
 *  (all points have x- and y-coordinates between 0 and 1) 
 *  using a red-black BST to support range search 
 *  (find all of the points contained in a query rectangle) 
 *  and nearest-neighbor search (find a closest point to a query point).
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;


public class PointSET {
	SET<Point2D> pointSet;
	// construct an empty set of points
	public PointSET() {
		pointSet = new SET<>();
	}
	// is the set empty? 
	public boolean isEmpty() {
		return pointSet.isEmpty();
	}
	// number of points in the set 
	public int size() {
		return pointSet.size();
	}

	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		pointSet.add(p);
	}

	// does the set contain point p? 
	public boolean contains(Point2D p) {
		return pointSet.contains(p);
	}

	// draw all points to standard draw 
	public void draw() {
		for (Point2D next : pointSet) {
			StdDraw.point(next.x(),next.y());
		}
	}

	// all points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		//lopp through all the points in pointSet
			//if rect contains it then add it to a list
		//return list
		Iterator<Point2D> iter = pointSet.iterator();
		ArrayList<Point2D> ret = new ArrayList<>();
		while (iter.hasNext())
		{
			Point2D next = iter.next();
			if(rect.contains(next))
			{
				ret.add(next);
			}
		}

		return ret;
	}

	// a nearest neighbor in the set to point p; null if the set is empty 
	public Point2D nearest(Point2D p) {
		if(isEmpty()) return null;
		Point2D nearestPoint = pointSet.min();
		double curdist = nearestPoint.distanceTo(p);
		for (Point2D next : pointSet) {
			if (next.distanceTo(p) < curdist) {
				curdist = next.distanceTo(p);
				nearestPoint = next;
			}
		}
		return nearestPoint;
	}

	// unit testing of the methods (optional) 
	public static void main(String[] args) {

	}
}


