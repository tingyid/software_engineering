package edu.upenn.cis573.travelingsalesman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;

public class Stroke {

    // the color and width for a stroke on the screen
    private static final int PAINT_COLOR = Color.YELLOW;
    private static final float PAINT_WIDTH = 10;

    private final Paint paint;
    private final ArrayList<Point> points;
    private boolean isValidStroke;
    private Point firstPoint;

    public Stroke() {
        this.paint = new Paint();
        this.paint.setColor(PAINT_COLOR);
        this.paint.setStrokeWidth(PAINT_WIDTH);
        this.points = new ArrayList<Point>();
        this.isValidStroke = false;
    }

    /**
     * Draws a stroke to the screen if it is valid
     */
    public void drawStroke(Canvas canvas) {
        if (isValidStroke && points.size() > 1) {
            for (int i = 0; i < points.size()-1; i++) {
                int x1 = points.get(i).x;
                int y1 = points.get(i).y;
                int x2 = points.get(i+1).x;
                int y2 = points.get(i+1).y;
                canvas.drawLine(x1, y1, x2, y2, paint);
            }
        }
    }

    /**
     * Adds a point to the stroke as the
     * user drags their finger on the screen
     */
    public void addPointIfValid(Point point) {
        if (isValidStroke) {
            points.add(point);
        }
    }

    /**
     * Creates a new stroke and marks it valid if the stroke
     * starts near one of the points on the map
     */
    public void startStrokeIfValid(Point[] mapPoints, Point startPoint) {
        // only add the segment if the touch point is within 30 of any of the other points
        Point validPoint = getClosestPoint(mapPoints, startPoint);
        if (validPoint != null) {
            points.add(validPoint);
            firstPoint = validPoint;
            isValidStroke = true;
        }
    }

    /**
     * Ends a new stroke and creates a segment representing
     * the stroke if it is valid and ends near a point
     * on the map
     */
    public LineSegment endStrokeIfValid(Point[] mapPoints, Point endPoint) {
        if (isValidStroke) {
            points.clear();
            Point validPoint = getClosestPoint(mapPoints, endPoint);
            if (validPoint != null) {
                //make sure first point and current point aren't the same
                if (firstPoint.x != validPoint.x && firstPoint.y != validPoint.y) {
                    return new LineSegment(firstPoint, validPoint);
                }
            }
        }
        isValidStroke = false;
        return null;
    }

    /**
     * Helper method for finding the closest point
     * on the map for a given point. Returns null
     * if no point is within 30px
     */
    private Point getClosestPoint(Point[] mapPoints, Point newPoint) {
        // only add the segment if the release point is within 30 of any of the other points
        for (int i = 0; i < mapPoints.length; i++) {
            if (LineSegment.distance(newPoint, mapPoints[i]) < 30) {
                // the "+10" part is a bit of a fudge factor because the point itself is the
                // upper-left corner of the little red box but we want the center
                newPoint.x = mapPoints[i].x + 10;
                newPoint.y = mapPoints[i].y + 10;
                return newPoint;
            }
        }
        return null;
    }
}
