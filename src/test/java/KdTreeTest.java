import static org.junit.Assert.*;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class KdTreeTest {
  KdTree kd;
  @Before
  public void setUp() throws Exception {
    kd = new KdTree();
  }

  @Test
  public void testInsert() {
    //basic insert
    assertTrue(kd.isEmpty());
    kd.insert(new Point2D(.2,.3));
    assertEquals(kd.size(),1);
    assertFalse(kd.isEmpty());
    assertTrue(kd.contains(new Point2D(.2,.3)));

    //real insert test
    kd.insert(new Point2D(.1,.5));
    kd.insert(new Point2D(.2,.4));
    kd.insert(new Point2D(.7,.6));

    assertTrue(kd.contains(new Point2D(.1,.5)));
    assertTrue(kd.contains(new Point2D(.2,.4)));
    assertTrue(kd.contains(new Point2D(.2,.3)));
    assertTrue(kd.contains(new Point2D(.7,.6)));

    assertEquals(kd.size(),4);
  }


  @Test
  public void testRange() {
    ArrayList<Point2D> arr = new ArrayList<>();
    arr.add(new Point2D(.3,.4));
    kd.insert(new Point2D(.3,.4));
    assertEquals(arr,kd.range(new RectHV(0.0,0.0,1.0,1.0)));

    kd.insert(new Point2D(.2,.6));
    kd.insert(new Point2D(.5,.2));
    kd.insert(new Point2D(.8,.1));

  }


  @Test
  public void testNearest() {
    // tree example
    kd.insert(new Point2D(.7,.2));
    kd.insert(new Point2D(.5,.4));
    kd.insert(new Point2D(.9,.6));
    kd.insert(new Point2D(.2,.3));
    kd.insert(new Point2D(.4,.7));


    assertEquals(new Point2D(.9,.6),kd.nearest(new Point2D(.959,.839)));



  }

}
