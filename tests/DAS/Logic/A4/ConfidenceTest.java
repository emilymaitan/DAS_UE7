package DAS.Logic.A4;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Emily on 6/7/2016.
 */
public class ConfidenceTest {
    @Test
    public void ttableLookup() throws Exception {
        assertEquals(1.812,Confidence.ttableLookup(10,0.95),0);
    }

    @Test
    public void calcBounds() throws Exception {
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.add(1050); test.add(505); test.add(921); test.add(630); test.add(753); test.add(887); test.add(112);
        test.add(248); test.add(992); test.add(2499); test.add(15); test.add(503); test.add(503); test.add(399);
        Double[] bounds = Confidence.calcBounds(test,.95);

        Double[] expected = new Double[2];
        expected[0] = 438.18; expected[1] = 992.82;

        //System.out.println(bounds[0] + " " + bounds[1] + " VS " + expected[0] + " " + expected[1]);

        assertEquals(expected[0], bounds[0],2);
        assertEquals(expected[1], bounds[1],2);
    }

}