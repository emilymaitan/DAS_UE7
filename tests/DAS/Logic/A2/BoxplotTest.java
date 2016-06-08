package DAS.Logic.A2;

import javafx.scene.chart.BarChart;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Emily on 6/8/2016.
 */
public class BoxplotTest {
    private ArrayList<Integer> neg = new ArrayList<>(Arrays.asList(-1, -10, -5, 10, -50));      // two neg quartiles
    private ArrayList<Integer> testvalues = new ArrayList<>(Arrays.asList(0,1,5,10,20,100));    // all pos
    private ArrayList<Integer> mixed = new ArrayList<>(Arrays.asList(-100,-80,-50,30,1));       // one pos, one neg

    @Test
    public void calculateQuantile() throws Exception {

    }

    @Test
    public void calcUpperBound() throws Exception {
        Boxplot boxplot = new Boxplot(testvalues);
        assertEquals(48.5,boxplot.calcUpperBound(),0);

        Boxplot boxneg = new Boxplot(neg);
        assertEquals(10,boxneg.calcUpperBound(),0);

        Boxplot boxmix = new Boxplot(mixed);
        assertEquals(30,boxmix.calcUpperBound(),0);

        Boxplot differences = new Boxplot(new ArrayList<>(Arrays.asList(-18,-48,-94,-10,10,99,7,1,-32,10,0,-58,-41,-49,-16,3,-31,-37,-18,-9)));
        assertEquals(differences.getQuartile75()+1.5*(differences.getQuartile75()-differences.getQuartile25()),differences.calcUpperBound(),0.5);
    }

    @Test
    public void calcLowerBound() throws Exception {
        Boxplot boxplot = new Boxplot(testvalues);
        assertEquals(0, boxplot.calcLowerBound(),0);

        Boxplot boxmix = new Boxplot(mixed);
        assertEquals(-100,boxmix.calcLowerBound(),0);

        Boxplot differences = new Boxplot(new ArrayList<>(Arrays.asList(-18,-48,-94,-10,10,99,7,1,-32,10,0,-58,-41,-49,-16,3,-31,-37,-18,-9)));
        assertEquals(-94,differences.calcLowerBound(),0);
    }

    @Test
    public void getMinimum() throws Exception {
        Boxplot boxplot = new Boxplot(testvalues);
        assertEquals(0,boxplot.getMinimum(),0);

        Boxplot boxneg = new Boxplot(neg);
        assertEquals(-50,boxneg.getMinimum(),0);
    }

    @Test
    public void getQuartile25() throws Exception {
        Boxplot boxplot = new Boxplot(testvalues);
        assertEquals(1,boxplot.getQuartile25(),0);

        Boxplot boxneg = new Boxplot(neg);
        assertEquals(-10,boxneg.getQuartile25(),0);

        Boxplot boxmix = new Boxplot(mixed);
        assertEquals(-80,boxmix.getQuartile25(),0);
    }

    @Test
    public void getQuartile50() throws Exception {
        Boxplot boxplot = new Boxplot(testvalues);
        assertEquals(7.5,boxplot.getQuartile50(),0);

        Boxplot boxneg = new Boxplot(neg);
        assertEquals(-5,boxneg.getQuartile50(),0.01);

        Boxplot boxmix = new Boxplot(mixed);
        assertEquals(-50,boxmix.getQuartile50(),0);
    }

    @Test
    public void getQuartile75() throws Exception {
        Boxplot boxplot = new Boxplot(testvalues);
        assertEquals(20,boxplot.getQuartile75(),0);

        Boxplot boxneg = new Boxplot(neg);
        assertEquals(-1,boxneg.getQuartile75(),0);

        Boxplot boxmix = new Boxplot(mixed);
        assertEquals(1,boxmix.getQuartile75(),0);
    }

    @Test
    public void getMaximum() throws Exception {
        Boxplot boxplot = new Boxplot(testvalues);
        assertEquals(100,boxplot.getMaximum(),0);

        Boxplot boxneg = new Boxplot(neg);
        assertEquals(10,boxneg.getMaximum(),0);
    }

    @Test
    public void getSortedValues() throws Exception {
        Boxplot boxplot = new Boxplot(testvalues);
        assertArrayEquals(testvalues.toArray(),boxplot.getSortedValues().toArray());
    }

}