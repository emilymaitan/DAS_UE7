package DAS.IntegerFileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Emily on 6/4/2016.
 */
public class IntegerFileReader {

    public static ArrayList<Integer> readFromFile(String url) throws IOException {

        ArrayList<Integer> res = new ArrayList<>();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(new File(url)),  "UTF-8") );

        String line = br.readLine();

        while (line != null) {
            res.add(Integer.parseInt(line));
            line = br.readLine();
        }

        br.close();

        return res;
    }
}
