import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Stack;


public class PointSET {
    private SET<Point2D> points;

    // construct an empty set of points 
    public PointSET() {
        points = new SET<Point2D>();
    }

    // is the set empty? 
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set 
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        points.add(p);
    }

    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return points.contains(p);
    }

    // draw all points to standard draw 
    public void draw() {
        for (Point2D p: points) p.draw();
    }

    // all points that are inside the rectangle 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();

        Stack<Point2D> pointsInRange = new Stack<Point2D>();
        for (Point2D p: points) {
            if (p.x() >= rect.xmin() && p.x() <= rect.xmax() &&
                p.y() >= rect.ymin() && p.y() <= rect.ymax()) {
                pointsInRange.push(p);
            }
        }
        return pointsInRange;
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();

        double nearest = -1.0;
        Point2D nearestPoint = p;
        for (Point2D point: points) {
            double distance = p.distanceTo(point);
            if (nearest < 0 || distance < nearest) {
                nearest = distance;
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }

    // unit testing of the methods (optional) 
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // initialize the data structures with N points from standard input
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }

        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();
        StdDraw.show();

        while (true) {

            // user starts to drag a rectangle
            if (StdDraw.mousePressed() && !isDragging) {
                x0 = StdDraw.mouseX();
                y0 = StdDraw.mouseY();
                isDragging = true;
                continue;
            }

            // user is dragging a rectangle
            else if (StdDraw.mousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
                continue;
            }

            // mouse no longer pressed
            else if (!StdDraw.mousePressed() && isDragging) {
                isDragging = false;
            }


            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                                     Math.max(x0, x1), Math.max(y0, y1));
            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();

            // draw the rectangle
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            // draw the range search results for brute-force data structure in red
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            for (Point2D p : brute.range(rect))
                p.draw();

            StdDraw.show();
            StdDraw.pause(40);
        }
    }
}
