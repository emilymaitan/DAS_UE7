package DAS.Logic.A6;

import java.util.ArrayList;

/**
 * Created by Emily on 6/7/2016.
 */
public class Differences {
    public static ArrayList<Integer> calcDifferences(ArrayList<Integer> set1, ArrayList<Integer> set2) {
        if (set1.size() != set2.size()) return null;

        ArrayList<Integer> res = new ArrayList<>(set1.size());
        for (int i = 0; i < set1.size(); i++) {
            //res.add(i,Math.abs(set1.get(i)-set2.get(i)));
            res.add(i,(set1.get(i)-set2.get(i)));
        }
        return res;
    }
}
