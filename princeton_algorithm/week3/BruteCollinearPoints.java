
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

import edu.princeton.cs.algs4.Stopwatch;


public class BruteCollinearPoints {
    private Point[] points;
    private LineSegment[] lineSegments;
    private int lineSegIdx = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new NullPointerException();
            this.points[i] = points[i];
        }

        Arrays.sort(this.points);
        for (int i = 0; i < points.length - 1; i++) {
            if (this.points[i].compareTo(this.points[i+1]) == 0)
                throw new IllegalArgumentException();
        }
        lineSegments = new LineSegment[points.length * points.length / 4];
        findAllLineSegments();
    }

    private void findAllLineSegments() {

        for (int i = 0; i < points.length; i++) {
            double oldSlope = Double.NEGATIVE_INFINITY;
            int idx = 0;
            Point[] lineSegPoints;

            for (int j = i; j < points.length; j++) {
                lineSegPoints = new Point[3];

                double slope = points[i].slopeTo(points[j]);
                if (slope == oldSlope) continue;
                lineSegPoints[idx++] = points[j];

                for (int k = j+1; k < points.length; k++) {
                    if (lineSegPoints[2] != null) break;
                    if (points[i].slopeTo(points[k]) == slope) {
                        lineSegPoints[idx++] = points[k];
                    }
                }

                if (lineSegPoints[2] != null) {
                    LineSegment lineSegment = 
                        new LineSegment(points[i], lineSegPoints[2]);
                    lineSegments[lineSegIdx++] = lineSegment;
                    oldSlope = slope;
                }
                idx = 0;
                lineSegPoints = null;
            }
        }
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return lineSegIdx;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] lineSegs = new LineSegment[lineSegIdx];
        for (int i = 0; i < lineSegIdx; i++)
            lineSegs[i] = lineSegments[i];
        return lineSegs;
    }


    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        Stopwatch watch = new Stopwatch();

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdOut.println("Running time: " + watch.elapsedTime());

        StdDraw.show();
    }
}
