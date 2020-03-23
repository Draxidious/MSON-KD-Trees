import static org.junit.Assert.*;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PointSetTest {
    PointSET ps;

    @Before
    public void setUp() throws Exception {
        ps = new PointSET();
    }

    @Test
    public void testStandardMethods() {
        assertTrue(ps.isEmpty()); // isEmpty test
        ps.insert(new Point2D(3, 1)); // Insert test
        assertTrue(ps.contains(new Point2D(3, 1)));// Contains test
        assertEquals(ps.size(), 1); // Size test
        assertFalse(ps.isEmpty()); // isEmpty test

        ps.insert(new Point2D(5, 2)); // Insert test
        assertTrue(ps.contains(new Point2D(5, 2)));// Contains test
        assertEquals(ps.size(), 2); // Size test

    }
    @Test(expected = IllegalArgumentException.class)
    public void nullInsert() {
        ps.insert(null);
    }

    @Test
    public void testRange() {
        RectHV rect = new RectHV(0, 0, .5, .5);

        Point2D add = new Point2D(.1, .1);// in
        ps.insert(add);
        add = new Point2D(0, .6);// out
        ps.insert(add);
        add = new Point2D(.2, .2);// in
        ps.insert(add);
        add = new Point2D(.3, .3);// in
        ps.insert(add);
        add = new Point2D(.6, .8); // out
        ps.insert(add);
        add = new Point2D(.4, .4); // in
        ps.insert(add);

        ArrayList<Point2D> expected = new ArrayList<>();
        expected.add(new Point2D(.1, .1));
        expected.add(new Point2D(.2, .2));
        expected.add(new Point2D(.3, .3));
        expected.add(new Point2D(.4, .4));
        assertEquals(expected, ps.range(rect));
    }

    @Test
    public void testNearest() {
        Point2D add = new Point2D(.1, .3);
        ps.insert(add);
        add = new Point2D(.4, .6);
        ps.insert(add);
        add = new Point2D(.5, .2);
        ps.insert(add);
        add = new Point2D(.3, .6);
        ps.insert(add);
        add = new Point2D(.4, .2);
        ps.insert(add);
        add = new Point2D(.3, .4);
        ps.insert(add);

        Point2D query = new Point2D(.5, .6);
        Point2D nearest = new Point2D(.4, .6);
        assertEquals(nearest, ps.nearest(query));

        query = new Point2D(0, .3);
        nearest = new Point2D(.1, .3);
        assertEquals(nearest, ps.nearest(query));


    }


}
