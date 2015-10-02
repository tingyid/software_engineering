package edu.upenn.cis573.travelingsalesman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.util.Log;
import android.widget.Toast;

public class GameView extends View {

    protected Stroke stroke = new Stroke();
    protected Segments segments = new Segments();
    protected int attempt = 0;
    protected static final Point[] mapPositions;
    protected Point[] mapPoints;

    // these points are all hardcoded to fit the UPenn campus map on a Nexus 5
    static {
        mapPositions = new Point[13];
        mapPositions[0] = new Point(475, 134);
        mapPositions[1] = new Point(141, 271);
        mapPositions[2] = new Point(272, 518);
        mapPositions[3] = new Point(509, 636);
        mapPositions[4] = new Point(1324, 402);
        mapPositions[5] = new Point(1452, 243);
        mapPositions[6] = new Point(1667, 253);
        mapPositions[7] = new Point(750,  670);
        mapPositions[8] = new Point(1020, 380);
        mapPositions[9] = new Point(870, 250);
        mapPositions[10] = new Point(540, 477);
        mapPositions[11] = new Point(828, 424);
        mapPositions[12] = new Point(1427, 66);
    }

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public static double calculatePathDistance(ArrayList<Point> points) {
        double total = 0;

        // get the distance between the intermediate points
        for (int i = 0; i < points.size()-1; i++) {
            total += LineSegment.distance(points.get(i), points.get(i + 1));
        }

        // then need to go back to the beginning
        total += LineSegment.distance(points.get(points.size() - 1), points.get(0));
        return total;
    }

    /**
     * Initializes the background map and the points
     * on the map for the game
     */
    protected void init(int spinnerNum) {
        setBackgroundResource(R.drawable.campus);

        Log.v("GAME VIEW", "init");

        mapPoints = new Point[spinnerNum];

        /*
         * We randomly choose the points to be placed on the map from mapPositions. We
         * use a set to ensure we don't use any point more than once.
         */
        Set<Integer> set = new HashSet<Integer>();
        Random rn = new Random();
        for (int i = 0; i < spinnerNum; i++) {
            int randomNum = rn.nextInt(mapPositions.length);
            while (set.contains(randomNum)) {
                randomNum = rn.nextInt(mapPositions.length);
            }
            set.add(randomNum);
            mapPoints[i] = mapPositions[randomNum];
        }
    }

    /**
     * This method is automatically invoked when the View is displayed.
     * It is also called after you call "invalidate" on this object.
     */
    protected void onDraw(Canvas canvas) {
        stroke.drawStroke(canvas);
        segments.draw(canvas);

        // draws the points on the map
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        for (int i = 0; i < mapPoints.length; i++) {
            int x = mapPoints[i].x;
            int y = mapPoints[i].y;
            canvas.drawRect(x, y, x+20, y+20, paint);
        }

        // detects whether the segments form a circuit
        boolean isCircuit = segments.isCircuit();

        // see if user has solved the problem
        if (segments.size() == mapPoints.length && isCircuit) {
            ArrayList<Point> shortestPath = ShortestPath.shortestPath(mapPoints);
            double shortestPathLength = calculatePathDistance(shortestPath);
            double myPathLength = segments.calculateLength();

            Log.v("RESULT", "Shortest path length is " + shortestPathLength + "; my path is " + myPathLength);

            double diff = shortestPathLength - myPathLength;
            if (Math.abs(diff) < 0.01) {
                Toast.makeText(getContext(), "You found the shortest path!", Toast.LENGTH_LONG).show();
                attempt = 0;
            }
            else {
                attempt++;
                // after the 3rd failed attempt, show the solution
                if (attempt >= 3) {
                    // draw the solution
                    for (int i = 0; i < shortestPath.size() - 1; i++) {
                        Point a = shortestPath.get(i);
                        Point b = shortestPath.get(i + 1);
                        paint.setColor(Color.YELLOW);
                        paint.setStrokeWidth(10);
                        canvas.drawLine(a.x+10, a.y+10, b.x+10, b.y+10, paint);
                    }
                    Point a = shortestPath.get(shortestPath.size()-1);
                    Point b = shortestPath.get(0);
                    paint.setColor(Color.YELLOW);
                    paint.setStrokeWidth(10);
                    canvas.drawLine(a.x+10, a.y+10, b.x+10, b.y+10, paint);

                    Toast.makeText(getContext(), "Nope, sorry! Here's the solution.", Toast.LENGTH_LONG).show();
                }
                else {
                    int offset = (int) (Math.abs(diff) / shortestPathLength * 100);
                    // so that we don't say that the path is 0% too long
                    if (offset == 0) {
                        offset = 1;
                    }
                    Toast.makeText(getContext(), "Nope, not quite! Your path is about " + offset + "% too long.", Toast.LENGTH_LONG).show();
                }
            }
        }
        else if (segments.size() == mapPoints.length && !isCircuit) {
            Toast.makeText(getContext(), "That's not a circuit! Select Clear from the menu and start over", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method is automatically called when the user touches the screen.
     */
    public boolean onTouchEvent(MotionEvent event) {

        //the point where the screen was touched
        Point p = new Point();
        p.x = ((int)event.getX());
        p.y = ((int)event.getY());

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // only add the segment if the touch point is within 30 of any of the other points
            stroke.startStrokeIfValid(mapPoints, p);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            stroke.addPointIfValid(p);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            LineSegment segment = stroke.endStrokeIfValid(mapPoints, p);
            if (segment != null) {
                segments.addSegment(segment);
            }
        } else {
            return false;
        }

        // forces a redraw of the View
        invalidate();

        return true;
    }

    /**
     * Used to clear all the segments from the map
     * A rest for the user
     */
    public void clearView() {
        segments.removeAllSegments();
        invalidate();
    }

    /**
     * Used to undo the last move made by the user
     */
    public void undoLast() {
        if (!segments.removeLastSegment()) {
            Toast.makeText(getContext(), "There's nothing to undo.", Toast.LENGTH_LONG).show();
        }
        invalidate();
    }
}
