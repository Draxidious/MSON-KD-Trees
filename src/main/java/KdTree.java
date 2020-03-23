/******************************************************************************
 *  Name:    J.D. DeVaughn-Brown
 *  NetID:   jddevaug
 *  Precept: P05
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *  
 *  Compilation:  javac-algs4 KdTree.java
 *  Execution:    java-algs4 KdTree
 *  Dependencies: Point2D.java RectHV.java 
 * 
 *  Description: Represents a set of points in the unit square 
 *  (all points have x- and y-coordinates between 0 and 1) 
 *  using a 2d-tree to support efficient range search 
 *  (find all of the points contained in a query rectangle) 
 *  and nearest-neighbor search (find a closest point to a query point).
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;


public class KdTree {
  private static class Node{
    private Point2D p;  // the point
    private RectHV rect; // the axis-aligned rectangle corresponding to
    private Node lb; // the left/bottom subtree
    private Node rt; // the right/top subtree

  }
  // construct an empty set of points
  public KdTree() {

  }
  // is the set empty? 
  public boolean isEmpty() {
    return false;
  }
  // number of points in the set 
  public int size() {
    return 0;
  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {

  }

  // does the set contain point p? 
  public boolean contains(Point2D p) {
    return false;
  }

  // draw all points to standard draw 
  public void draw() {
  
  }

  // all points that are inside the rectangle
  public Iterable<Point2D> range(RectHV rect) {
    return null;
  }

  // a nearest neighbor in the set to point p; null if the set is empty 
  public Point2D nearest(Point2D p) {
    return null;
  }

  // unit testing of the methods (optional) 
  public static void main(String[] args) {

  }
}


