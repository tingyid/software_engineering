package edu.upenn.cis573.travelingsalesman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Segments {

    //the color and width for a segment on the screen
    private static final int PAINT_COLOR = Color.RED;
    private static final float PAINT_WIDTH = 10;

    private final Paint paint;
    private ArrayList<LineSegment> segments;

    public Segments() {
        this.segments = new ArrayList<LineSegment>();
        this.paint = new Paint();
        this.paint.setColor(PAINT_COLOR);
        this.paint.setStrokeWidth(PAINT_WIDTH);
    }

    /**
     * Add a segment to the list
     */
    public void addSegment(LineSegment lineSegment) {
        segments.add(lineSegment);
    }

    /**
     * Remove the last segment from a list to
     * undo a user's last action
     */
    public boolean removeLastSegment() {
        if (segments.size() < 1) {
            return false;
        }
        segments.remove(segments.size() - 1);
        return true;
    }

    /**
     * Clear all segments from the list for
     * a user's clear action
     */
    public void removeAllSegments() {
        segments.clear();
    }

    public int size() {
        return segments.size();
    }

    /**
     * Calculates and returns the length of all
     * segments in the list combined
     */
    public double calculateLength() {
        double segmentLength = 0;
        for (LineSegment segment : segments) {
            Point p1 = segment.getStart();
            Point p2 = segment.getEnd();
            segmentLength += LineSegment.distance(p1, p2);
        }
        return segmentLength;
    }

    /**
     * Draws each segment to the screen
     */
    public void draw(Canvas canvas) {
        for (int i = 0; i < segments.size(); i++) {
            LineSegment segment = segments.get(i);
            canvas.drawLine(segment.getStart().x, segment.getStart().y, segment.getEnd().x, segment.getEnd().y, paint);
        }
    }

    /**
     * Checks if the current segments form a singular circuit
     * on the map, returns boolean flag as an indicator
     */
    public boolean isCircuit() {
        if (segments.isEmpty()) {
            return false;
        }

        Set<LineSegment> segmentSet = new HashSet<LineSegment>(segments);
        Point startPoint = segments.get(0).getStart();
        Point currentPoint = segments.get(0).getEnd();
        segmentSet.remove(segments.get(0));

        //iterate until we get back to where we started
        while (!currentPoint.equals(startPoint)) {
            if (segmentSet.size() == 0) {
                return false;
            }

            Iterator<LineSegment> iter = segmentSet.iterator();
            while (iter.hasNext()) {
                LineSegment segment = iter.next();
                if (segment.getStart().equals(currentPoint)) {
                    currentPoint = segment.getEnd();
                    iter.remove();
                    break;
                } else if (segment.getEnd().equals(currentPoint)) {
                    currentPoint = segment.getStart();
                    iter.remove();
                    break;
                }
                else if (!iter.hasNext()) {
                    return false;
                }
            }
        }

        //we should have used every segment to get back to where we are
        return segmentSet.isEmpty();
    }
}
