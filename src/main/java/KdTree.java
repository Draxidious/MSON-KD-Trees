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
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;


public class KdTree {
    private static class Node {
        private Point2D p;  // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree
        boolean isVert;

        public Node(Point2D point, RectHV r, boolean vert) {
            p = point;
            rect = r;
            this.lb = null;
            this.rt = null;
            isVert = vert;
        }

    }

    private int size;
    private Node head;
    private final boolean VERTICAL = true;
    private final boolean HORIZONTAL = false;


    // construct an empty set of points
    public KdTree() {
        head = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Tried to insert null element into tree");
        head = insert(head, p, VERTICAL, 0.0, 0.0, 1.0, 1.0);
    }

    public Node insert(Node cur, Point2D p, boolean isVert,
                       double xmin, double ymin, double xmax, double ymax) {
        if (cur == null) {
            size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax), isVert);
        }
        if (isVert) {
            if (p.x() < cur.p.x()) {
                cur.lb = insert(cur.lb, p, HORIZONTAL, xmin, ymin, cur.p.x(), ymax);
                return cur;

            } else {
                cur.rt = insert(cur.rt, p, HORIZONTAL, cur.p.x(), ymin, xmax, ymax);
                return cur;
            }
        } else {
            if (p.y() < cur.p.y()) {
                cur.lb = insert(cur.lb, p, VERTICAL, xmin, ymin, xmax, cur.p.y());
                return cur;
            } else {
                cur.rt = insert(cur.rt, p, VERTICAL, xmin, cur.p.y(), xmax, ymax);
                return cur;
            }
        }
    }

    // does the set contain point p?
    // you dont need to use the rectangle
    public boolean contains(Point2D p) {
        if (isEmpty()) return false;
        return contains(head, p, VERTICAL);
    }

    private boolean contains(Node cur, Point2D p, boolean isVert) {
        if (cur == null) return false;
        if (cur.p.equals(p)) {
            return true;
        }
        if (isVert) {
            if (p.x() < cur.p.x()) {
                return contains(cur.lb, p, HORIZONTAL);
            } else {
                return contains(cur.rt, p, HORIZONTAL);
            }
        } else {
            if (p.y() < cur.p.y()) {
                return contains(cur.lb, p, VERTICAL);
            } else {
                return contains(cur.rt, p, VERTICAL);
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        Iterator<Point2D> it = new KDIterator(head);
        for (; it.hasNext(); ) {
            Point2D next = it.next();
            StdDraw.point(next.x(), next.y());
        }
    }

    static class KDIterator implements Iterator<Point2D> {
        /**
         * Stack to use for iterator.
         */
        private final Deque<Node> stack;
        /**
         * Current node visiting.
         */
        private Node curnode;

        public KDIterator(Node node) {
            stack = new LinkedList<>();
            curnode = node;
        }


        @Override
        public boolean hasNext() {
            return !stack.isEmpty() || curnode != null;
        }

        @Override
        public Point2D next() {
            while (curnode != null && curnode.lb != null) {
                stack.push(curnode);
                curnode = curnode.lb;
            }
            if (curnode == null) {
                curnode = stack.pop();
            }
            Point2D result = curnode.p;

            if (curnode.rt != null) {
                curnode = curnode.rt;
            } else {
                curnode = null;
            }
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (isEmpty()) return null;
        ArrayList<Point2D> arr = new ArrayList<>();
        return range(head, rect, arr);
    }

    private ArrayList<Point2D> range(Node cur, RectHV rect, ArrayList<Point2D> arr) {
        ArrayList<Point2D> ret = arr;
        if (cur != null && cur.rect.intersects(rect)) {
            ret.add(cur.p);
            ret = range(cur.lb, rect, ret);
            ret = range(cur.rt, rect, ret);
        }
        return ret;

    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        Node close = head;
        double closest = p.distanceTo(head.p);
        return nearest(p, head, closest, close).p;
    }

    private Node nearest(Point2D p, Node cur, double closest, Node close) {
        double thisclosest = closest;
        Node thisclose = close;
        double newdist = cur.p.distanceTo(p);
        if (newdist < closest) { // update best node and dist value if cur is best
            thisclosest = newdist;
            thisclose = cur;
        }
        if (cur.lb != null && cur.rt != null) {
            Node rtbest = nearest(p, cur.rt, thisclosest, thisclose); // take best node from rt subtree
            Node lbbest = nearest(p, cur.lb, thisclosest, thisclose); // take best node from lb subtree
            double rtdist = rtbest.p.distanceTo(p); // get distances to compare
            double lbdist = lbbest.p.distanceTo(p);
            if (rtdist < lbdist) return rtbest; // return best
            else return lbbest;
        } else if (cur.lb != null) {
            return nearest(p, cur.lb, thisclosest, thisclose);
        } else if (cur.rt != null) {
            return nearest(p, cur.rt, thisclosest, thisclose);
        } else {
            return thisclose;
        }
    }

    public String toString() {
        Iterator<Point2D> it = new KDIterator(head);
        StringBuilder ret = new StringBuilder("[");
        for (; it.hasNext(); ) {
            Point2D next = it.next();
            ret.append(next).append(":");
        }
        ret.append("]");
        return ret.toString();
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}


