package DAS.Logic.A2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Emily on 6/8/2016.
 */
public class BoxplotTest {
    private ArrayList<Integer> neg = new ArrayList<>(Arrays.asList(-1, -10, -5, 10, -50));

    @Test
    public void calculateQuantile() throws Exception {

    }

    @Test
    public void calcUpperBound() throws Exception {

    }

    @Test
    public void calcLowerBound() throws Exception {

    }

    @Test
    public void getMinimum() throws Exception {
        Boxplot boxneg = new Boxplot(neg);
        assertEquals(-50,boxneg.getMinimum(),0);
    }

    @Test
    public void getQuartile25() throws Exception {

    }

    @Test
    public void getQuartile50() throws Exception {
        Boxplot boxneg = new Boxplot(neg);
        assertEquals(-5,boxneg.getQuartile50(),0.01);
    }

    @Test
    public void getQuartile75() throws Exception {

    }

    @Test
    public void getMaximum() throws Exception {
        Boxplot boxneg = new Boxplot(neg);
        assertEquals(10,boxneg.getMaximum(),0);
    }

    @Test
    public void getSortedValues() throws Exception {

    }

}