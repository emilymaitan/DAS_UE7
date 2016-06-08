package DAS.Logic.A5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Emily on 6/8/2016.
 */
public class BoxDifferences {
    public static ArrayList<Integer> getDifferences(ArrayList<Integer> values) {
        ArrayList<Integer> res = new ArrayList<>(10);
        ArrayList<Integer> sorted = new ArrayList<>(values);
        Collections.sort(sorted);

        int range = sorted.get(sorted.size()-1) - sorted.get(0);

        return res;
    }
}
