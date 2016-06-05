package DAS.Functions.A3;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Emily on 6/5/2016.
 */
public class Correlation {
    private ArrayList<Integer> values;

    public Correlation(ArrayList<Integer> values ) {
        this.values = values;
    }

    public static Double corrCoeff(Correlation set1, Correlation set2) {
        return Correlation.covariance(set1,set2)/(set1.stdDev()*set2.stdDev());
    }

    public static Double covariance(Correlation set1, Correlation set2) {
        double cov = 0, mean1 = set1.sampleMean(), mean2 = set2.sampleMean();
        if (set1.getValues().size() != set2.getValues().size()) return null;
        for (int i = 0; i < set1.getValues().size(); i++) {
            cov += (set1.getValues().get(i)-mean1)*(set2.getValues().get(i)-mean2);
        }

        return cov/set1.getValues().size();
    }

    public Double variance() {
        double mean = this.sampleMean(), variance = 0;
        for (Integer i : values) {
            variance += (i-mean)*(i-mean);
        }

        return variance/values.size();
    }

    public Double stdDev() {
        return Math.sqrt(variance());
    }


    public Double sampleMean() {
        double sum = values.stream().mapToDouble(Integer::intValue).sum();
        return sum/ values.size();
    }

    public ArrayList<Integer> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH,
                "Sample Mean : %f\nVariance: %f\nStandard Deviation: %f\n",
                sampleMean(), variance(), stdDev()
        );
    }
}
