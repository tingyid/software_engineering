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
        String msg = "Failed to create the segment from two points on the map";

        Point startPoint = view.mapPoints[0];
        Point endPoint = view.mapPoints[1];
        MotionEvent down = createMotionEvent(MotionEvent.ACTION_DOWN, startPoint.x, startPoint.y);
        MotionEvent up = createMotionEvent(MotionEvent.ACTION_UP, endPoint.x, endPoint.y);

        view.dispatchTouchEvent(down);
        view.dispatchTouchEvent(up);

        assertEquals(msg, expectedSegmentSize, view.segments.size());
    }

    /*
     Tests that a segment is not created between two points
     when the first point is not close to a map location
     */
    @Test
    public void testDontCreateSegmentFirstPointNotCloseToMapLocation() {
        int expectedSegmentSize = 0;
        String msg = "Created the segment from two points on the map when shouldn't have";

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
        // IMPLEMENT THIS TEST!
    }
    
    /*
     Tests that a segment is not created between two points
     when the first and last point are the same points
     */
    @Test
    public void testDontCreateSegmentFirstAndLastPointSame() {
        // IMPLEMENT THIS TEST!
    }

    /*
     Tests that the line segments don't create a circuit
     */
    @Test
    public void testDontCreateCircuit() {
        // it's okay to just manipulate the segments and not rely on the touch events

        // IMPLEMENT THIS TEST!
    }

    /*
     Tests that the line segments create a circuit but it's not the shortest path
     */
    @Test
    public void testCreateCircuitNotShortestPath() {
        // it's okay to just manipulate the segments and not rely on the touch events

        // IMPLEMENT THIS TEST!

    }

    /*
     Tests that the line segments create a circuit that is the shortest path
     */
    @Test
    public void testCreateCircuitShortestPath() {
        // it's okay to just manipulate the segments and not rely on the touch events

        // IMPLEMENT THIS TEST!

    }

    private MotionEvent createMotionEvent(int type, int x, int y) {
        return MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(), type, x, y, 0);
    }
}