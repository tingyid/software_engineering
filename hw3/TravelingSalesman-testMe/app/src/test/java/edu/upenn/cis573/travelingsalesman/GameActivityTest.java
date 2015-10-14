package edu.upenn.cis573.travelingsalesman;

import android.graphics.Point;
import android.os.SystemClock;
import android.test.ActivityUnitTestCase;
import android.view.MotionEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertNotEquals;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 18)

public class GameActivityTest extends ActivityUnitTestCase<GameActivity> {

    protected GameActivity activity; // the Activity to test
    protected GameView view; // its GameView

    /* 
     Set up this test class
     */
    public GameActivityTest() {
        super(GameActivity.class);
    }

    /*
     This sets up all the objects you need for the tests.
     Don't change it!
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        activity = Robolectric.setupActivity(GameActivity.class);
        assertNotNull(activity);

        view = (GameView) (activity.findViewById(R.id.gameView));
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {

    }
    
    /*
     Just to make sure everything is set up correctly
     */
    @Test
    public void testSetupIsCorrect() {
        
        assertTrue(true);
    }


    /*
     Tests that a segment is properly created between two points
     */
    @Test
    public void testCreateSegment() {

        int expectedSegmentSize = 1;
        String msg = "Failed to create the segment from two valid points on the map";

        Point startPoint = view.mapPoints[0];
        Point endPoint = view.mapPoints[1];

        MotionEvent down = createMotionEvent(MotionEvent.ACTION_DOWN, startPoint.x, startPoint.y);
        MotionEvent up = createMotionEvent(MotionEvent.ACTION_UP, endPoint.x, endPoint.y);

        view.dispatchTouchEvent(down);
        view.dispatchTouchEvent(up);

        assertEquals(msg, expectedSegmentSize, view.segments.size());


        Point startPointCentered = getCenterPoint(startPoint);
        msg = "Segment was created, but the startPoint is not correct.";

        assertEquals(msg, startPointCentered, view.segments.get(0)[0]);


        Point endPointCentered = getCenterPoint(endPoint);
        msg = "Segment was created, but the endPoint is not correct.";

        assertEquals(msg, endPointCentered, view.segments.get(0)[1]);
    }

    /*
     Tests that a segment is not created between two points
     when the first point is not close to a map location
     */
    @Test
    public void testDontCreateSegmentFirstPointNotCloseToMapLocation() {
        int expectedSegmentSize = 0;
        String msg = "Shouldn't have created segment. Start point not close enough.";

        Point startPoint = view.mapPoints[0];
        Point endPoint = view.mapPoints[1];

        MotionEvent down = createMotionEvent(MotionEvent.ACTION_DOWN, startPoint.x+30, startPoint.y+30);
        MotionEvent up = createMotionEvent(MotionEvent.ACTION_UP, endPoint.x, endPoint.y);

        view.dispatchTouchEvent(down);
        view.dispatchTouchEvent(up);

        assertEquals(msg, expectedSegmentSize, view.segments.size());
    }

    /*
     Tests that a segment is not created between two points
     when the last point is not close to a map location
     */
    @Test
    public void testDontCreateSegmentLastPointNotCloseToMapLocation() {
        int expectedSegmentSize = 0;
        String msg = "Shouldn't have created segment. End point not close enough.";

        Point startPoint = view.mapPoints[0];
        Point endPoint = view.mapPoints[1];

        MotionEvent down = createMotionEvent(MotionEvent.ACTION_DOWN, startPoint.x, startPoint.y);
        MotionEvent up = createMotionEvent(MotionEvent.ACTION_UP, endPoint.x + 30, endPoint.y + 30);

        view.dispatchTouchEvent(down);
        view.dispatchTouchEvent(up);

        assertEquals(msg, expectedSegmentSize, view.segments.size());
    }
    
    /*
     Tests that a segment is not created between two points
     when the first and last point are the same points
     */
    @Test
    public void testDontCreateSegmentFirstAndLastPointSame() {
        // IMPLEMENT THIS TEST!
        int expectedSegmentSize = 0;
        String msg = "Shouldn't have created segment. Start and end points are the same.";

        Point startPoint = view.mapPoints[0];

        MotionEvent down = createMotionEvent(MotionEvent.ACTION_DOWN, startPoint.x, startPoint.y);
        MotionEvent up = createMotionEvent(MotionEvent.ACTION_UP, startPoint.x, startPoint.y);

        view.dispatchTouchEvent(down);
        view.dispatchTouchEvent(up);

        assertEquals(msg, expectedSegmentSize, view.segments.size());
    }

    /*
     Tests that the line segments don't create a circuit
     */
    @Test
    public void testDontCreateCircuit() {
        String msg = "Should have returned false. Segments don't form a single circuit";

        ArrayList<Point> points = new ArrayList<Point>(Arrays.asList(view.mapPoints));
        Point firstPoint = points.remove(0); //can't create a circuit without one of the points

        for(int i=0; i < points.size()-1; i++) {
            Point[] seg = {points.get(i), points.get(i+1)};
            view.segments.add(seg);
        }

        Point[] seg = {points.get(points.size()-1), points.get(0)};
        view.segments.add(seg);
        Point[] finalSeg = {points.get(0), firstPoint};
        view.segments.add(finalSeg);

        assertFalse(msg, view.detectCircuit());
    }

    /*
     Tests that the line segments create a circuit but it's not the shortest path
     */
    @Test
    public void testCreateCircuitNotShortestPath() {
        String msg = "Should still detect circuit even if not optimal solution";

        ArrayList<Point> points = new ArrayList<Point>(Arrays.asList(view.mapPoints));
        Point firstPoint = points.get(0);
        Point currentPoint = points.remove(0);

        while(!points.isEmpty()) {
            Point furthest = findFurthestPoint(currentPoint, points);
            Point[] newSegment = {currentPoint, furthest};
            view.segments.add(newSegment);
            points.remove(furthest);
            currentPoint = furthest;
        }

        Point[] finalSegment = {firstPoint, currentPoint};
        view.segments.add(finalSegment);

        assertTrue(msg, view.detectCircuit());
    }

    /*
     Tests that the line segments create a circuit that is the shortest path
     */
    @Test
    public void testCreateCircuitShortestPath() {
        String msg = "Should be the shortest path";

        ArrayList<Point> shortestPath = ShortestPath.shortestPath(view.mapPoints);
        for (int i=0; i < shortestPath.size()-1; i++) {
            Point[] segment = {shortestPath.get(i), shortestPath.get(i+1)};
            view.segments.add(segment);
        }

        Point[] segment = {shortestPath.get(shortestPath.size()-1), shortestPath.get(0)};
        view.segments.add(segment);

        assertTrue(msg, view.detectCircuit());

        double diff = Math.abs(view.calculatePathDistance(shortestPath)-view.myPathLength());

        assertTrue(msg, diff < 0.01);
    }

    private MotionEvent createMotionEvent(int type, int x, int y) {
        return MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(), type, x, y, 0);
    }

    private Point getCenterPoint(Point a) {
        return new Point(a.x+10, a.y+10);
    }

    private Point findFurthestPoint(Point startPoint, ArrayList<Point> points) {
        Point furthestPoint = points.get(0);
        double dist = distance(startPoint, furthestPoint);
        for (Point p : points) {
            double newDist = distance(startPoint, p);
            if (newDist > dist) {
                furthestPoint = p;
                dist = newDist;
            }
        }

        return furthestPoint;
    }

    private double distance(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}