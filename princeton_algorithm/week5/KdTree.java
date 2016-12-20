import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;


public class KdTree {
    private KdNode root;
    private int size;

    private class KdNode {
        public Point2D point;
        public boolean vertical = true;
        public KdNode left = null;
        public KdNode right = null;

        public KdNode(Point2D p) {
            point = p;
        }

        public String toString() {
            return "Point = " + point + "\n" + "Vertical = " + vertical + "\n";
        }
    }

    // construct an empty set of points 
    public KdTree() {
        root = null;
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

    private boolean isLeft(Point2D p1, Point2D p2) {
        return p1.x() < p2.x();
    }

    private boolean isDown(Point2D p1, Point2D p2) {
        return p1.y() < p2.y();
    }

    // add the point to the set (if it is not already in the set)
    private KdNode findParent(KdNode node, Point2D p) {
        if (node.vertical) {
            if (isLeft(p, node.point)) {
                if (node.left == null) return node;
                return findParent(node.left, p);
            } else {
                if (node.right == null) return node;
                return findParent(node.right, p);
            }
        } else {
            if (isDown(p, node.point)) {
                if (node.left == null) return node;
                return findParent(node.left, p);
            } else {
                if (node.right == null) return node;
                return findParent(node.right, p);
            }
        }
    }

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (isEmpty()) {
            root = new KdNode(p);
        } else {
            KdNode parent = findParent(root, p);
            if (parent.point.equals(p)) return;

            KdNode node = new KdNode(p);
            node.vertical = !parent.vertical;
            if (parent.vertical) {
                if (isLeft(node.point, parent.point)) parent.left = node;
                else parent.right = node;
            } else {
                if (isDown(node.point, parent.point)) parent.left = node;
                else parent.right = node;
            }
        }
        size++;
    }


    // does the set contain point p? 
    private boolean search(KdNode node, Point2D p) {
        if (node == null) return false;
        if (node.point.equals(p)) return true;
        return search(node.left, p) || search(node.right, p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return search(root, p);
    }

    // draw all points to standard draw 
    private void draw(KdNode node) {
        if (node == null) return;
        node.point.draw();
        draw(node.left);
        draw(node.right);
    }

    public void draw() {
        draw(root);
    }

    public void range(KdNode node, RectHV rect, Stack<Point2D> pointsInRange) {
        if (node == null) return;
        if (node.point.x() >= rect.xmin() && node.point.x() <= rect.xmax() &&
            node.point.y() >= rect.ymin() && node.point.y() <= rect.ymax()) {
            pointsInRange.push(node.point);
            range(node.left, rect, pointsInRange);
            range(node.right, rect, pointsInRange);
        } else {
            if (node.vertical) {
                if (node.point.x() > rect.xmax()) range(node.left, rect, pointsInRange);
                else if (node.point.x() < rect.xmin()) range(node.right, rect, pointsInRange);
                else {
                    range(node.left, rect, pointsInRange);
                    range(node.right, rect, pointsInRange);
                }
            } else {
                if (node.point.y() > rect.ymax()) range(node.left, rect, pointsInRange);
                else if (node.point.y() < rect.ymax()) range(node.right, rect, pointsInRange);
                else {
                    range(node.left, rect, pointsInRange);
                    range(node.right, rect, pointsInRange);
                }
            }
        }
    }

    // all points that are inside the rectangle 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        Stack<Point2D> pointsInRange = new Stack<Point2D>();
        range(root, rect, pointsInRange);

        return pointsInRange;
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(KdNode node, Point2D p, double nearest) {
        if (node.left == null && node.right == null) return node.point;

        if (node.left != null && node.right != null) {
            double dist_left = node.left.point.distanceTo(p);
            double dist_right = node.right.point.distanceTo(p);
            if (Math.min(dist_left, dist_right) < nearest) {
                if (dist_left < dist_right) return nearest(node.left, p, dist_left);
                else return nearest(node.right, p, dist_right);
            } else {
                Point2D pl = nearest(node.left, p, nearest);
                Point2D pr = nearest(node.right, p, nearest);
                double dl = pl.distanceTo(p);
                double dr = pr.distanceTo(p);
                if (Math.min(dl, dr) < nearest) {
                    if (dl < dr) return pl;
                    else return pr;
                }
                else return node.point;
            }
        }

        else if (node.left == null) {
            double dist = node.right.point.distanceTo(p);
            return nearest(node.right, p, Math.min(dist, nearest));
        }
        else if (node.right == null) {
            double dist = node.left.point.distanceTo(p);
            return nearest(node.left, p, Math.min(dist, nearest));
        }
        return node.point;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        double nearest = Double.MAX_VALUE;
        
        Point2D nearestPoint = nearest(root, p, nearest);

        return nearestPoint;
    }

    // unit testing of the methods (optional) 
    public static void main(String[] args) {
    }
}
