
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

import edu.princeton.cs.algs4.Stopwatch;

public class FastCollinearPoints {
    private Point[] points;
    private Point[][] lineSegments;
    private int lineSegIdx = 0;
    private LineSegment[] lineSegs;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
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
        lineSegments = new Point[points.length * points.length / 4][2];
        findAllLineSegments();
    }

    private void findAllLineSegments() {
        Point[] pts = new Point[points.length];
        for (int ii = 0; ii < points.length; ii++)
            pts[ii] = points[ii];

        for (Point p : points) {
            Arrays.sort(pts, p.slopeOrder());

            int j = 1;
            while (j < pts.length - 1) {
                Point min, max;
                if (pts[0].compareTo(pts[j]) < 0) {
                    min = pts[0];
                    max = pts[j];
                } else {
                    min = pts[j];
                    max = pts[0];
                }

                int k = j + 1;

                while (k < pts.length && 
                       pts[0].slopeTo(pts[k]) == pts[0].slopeTo(pts[j])) {
                    if (pts[k].compareTo(min) < 0) min = pts[k];
                    if (pts[k].compareTo(max) > 0) max = pts[k];
                    k++;
                }
                
                // adjust k to the last point of line segment
                if ((k - 1 - j) >= 2) {
                    k--;
                    boolean hasLineSeg = false;
                    for (int m = 0; m < lineSegments.length; m++) {
                        Point phead = lineSegments[m][0];
                        Point ptail = lineSegments[m][1];

                        if (phead == null || ptail == null) break;

                        if (min == phead && max == ptail) {
                            hasLineSeg = true;
                            break;
                        }
                    }
                    if (!hasLineSeg) {
                        lineSegments[lineSegIdx][0] = min;
                        lineSegments[lineSegIdx][1] = max;
                        lineSegIdx++;
                    }
                }
                j = k;
            }
        }

        lineSegs = new LineSegment[lineSegIdx];
        for (int i = 0; i < lineSegIdx; i++) {
            lineSegs[i] = new LineSegment(lineSegments[i][0], lineSegments[i][1]);
        }
    }
 

    // the number of line segments
    public int numberOfSegments() {
        return lineSegs.length;
    }

    // the line segments
    public LineSegment[] segments() {
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

        FastCollinearPoints collinear = new FastCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdOut.println("Line Segments number: " + collinear.numberOfSegments());
        StdOut.println("Running time: " + watch.elapsedTime());

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdOut.println("Line Segments number: " + collinear.numberOfSegments());

        StdDraw.show();
    }
}

