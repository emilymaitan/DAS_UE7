package DAS.Data;

import DAS.IntegerFileReader.IntegerFileReader;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Emily on 6/4/2016.
 * Quasi-Singleton class holding the integers read from the files.
 */
public class Integers {
    private static ArrayList<Integer> integers1 = null;
    private static ArrayList<Integer> integers2 = null;

    private Integers () {}

    public static void initialize() throws IOException {
        if (integers1 == null || integers2 == null ) {
            integers1 = IntegerFileReader.readFromFile("src/DAS/Data/integers1.txt");
            integers2 = IntegerFileReader.readFromFile("src/DAS/Data/integers2.txt");
        }
    }

    public static ArrayList<Integer> getIntegers1() {
        return integers1;
    }

    public static ArrayList<Integer> getIntegers2() {
        return integers2;
    }
}
