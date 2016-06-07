package DAS.Logic.A4;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Emily on 6/6/2016.
 */
public class Confidence {
    private static HashMap<Pair<Integer, Double>, Double> ttable = null;

    public static Double ttableLookup(Integer freedom, Double alpha) {
        if (ttable == null) Confidence.initTable();
        return ttable.get(new Pair<>(freedom,alpha));
    }

    public static Double[] calcBounds(ArrayList<Integer> values, double alpha) {
        Double[] bounds = new Double[2];

        double mean = values.stream().mapToDouble(Integer::intValue).sum() / values.size();

        double variance = 0;
        for (Integer i : values) {
            variance += (i-mean)*(i-mean);
        }
        variance = variance/values.size();

        double tvalue = Confidence.ttableLookup(values.size()-1, alpha);

        bounds[0] = mean - tvalue * (Math.sqrt(variance)/Math.sqrt(values.size()));
        bounds[1] = mean + tvalue * (Math.sqrt(variance)/Math.sqrt(values.size()));

        return bounds;
    }

    public static void initTable() {
        ttable = new HashMap<>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream("src/DAS/Data/ttable.csv"),  "UTF-8") );
            String line = br.readLine();
            line = br.readLine(); // ignore header line

            Integer fcounter = 0;

            while (line != null) {						// while there are lines
                String[] values = line.split(",");
                assert (values.length == 7);

                ttable.put(new Pair<>(fcounter,.80),Double.parseDouble(values[1]));
                ttable.put(new Pair<>(fcounter,.90),Double.parseDouble(values[2]));
                ttable.put(new Pair<>(fcounter,.95),Double.parseDouble(values[3]));
                ttable.put(new Pair<>(fcounter,.975),Double.parseDouble(values[4]));
                ttable.put(new Pair<>(fcounter,.99),Double.parseDouble(values[5]));
                ttable.put(new Pair<>(fcounter,.995),Double.parseDouble(values[6]));

                line = br.readLine();
                fcounter++;
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
