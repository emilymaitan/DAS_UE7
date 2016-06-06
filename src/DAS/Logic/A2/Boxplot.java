package DAS.Logic.A2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import static java.lang.Math.abs;

/**
 * Created by Emily on 6/4/2016.
 */
public class Boxplot {
    private Integer minimum;
    private Double quartile25;
    private Double quartile50;  // = median
    private Double quartile75;
    private Integer maximum;
    private ArrayList<Integer> sortedValues;

    public Boxplot (ArrayList<Integer> values) {
        this.sortedValues = new ArrayList<>(values);
        Collections.sort(sortedValues);

        this.minimum = sortedValues.get(0);
        this.maximum = sortedValues.get(sortedValues.size()-1);
        this.quartile25 = calculateQuantile(0.25);
        this.quartile50 = calculateQuantile(0.5);
        this.quartile75 = calculateQuantile(0.75);
    }

    public Double calculateQuantile (double alpha) {
        double k = sortedValues.size() * alpha;
        if (Math.floor(k) == k) {       // if the number is whole
            return 0.5*(sortedValues.get((int)k-1) + sortedValues.get((int)k)); // array starts with zero
        } else {
            return (double) sortedValues.get((int)Math.ceil(k));
        }
    }

    // only works for positive values by now
    public double calcUpperBound() {
        double boxlength = quartile75-quartile25;
        return Math.min(maximum,quartile75+(1.5*(boxlength)));
    }

    // only works for positive values by now
    public double calcLowerBound() {
        double boxlength = quartile75 - quartile25;
        return Math.max(minimum,quartile25-(1.5*(boxlength)));
    }

    public Integer getMinimum() {
        return minimum;
    }

    public Double getQuartile25() {
        return quartile25;
    }

    public Double getQuartile50() {
        return quartile50;
    }

    public Double getQuartile75() {
        return quartile75;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public ArrayList<Integer> getSortedValues() {
        return sortedValues;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH,
                "Minimum: %d\n25%%-Quartile: %f\n50%%-Quartile: %f\n75%%-Quartile: %f\nMaximum: %d",
                minimum, quartile25, quartile50, quartile75, maximum
                );
    }
}
