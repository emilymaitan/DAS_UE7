package DAS.Logic.A4;

import javafx.util.Pair;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by Emily on 6/6/2016.
 */
public class Confidence {
    private static HashMap<Pair<Integer, Double>, Double> ttable = null;

    public static Double ttableLookup(Integer freedom, Double alpha) {
        if (ttable == null) Confidence.initTable();
        return ttable.get(new Pair<>(freedom,alpha));
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
