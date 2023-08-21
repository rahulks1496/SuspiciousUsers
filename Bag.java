package twitterinfo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Bag {

    static String[][] array = new String[100][2];

    static String md = "://.//&//!// ";

    public TreeSet<String> callBag(String[] susp, String[][] array) {

        TreeSet<String> hs = new TreeSet<String>();

        Arrays.sort(array, new Comparator<String[]>() {
            @Override
            // arguments to this method represent the arrays to be sorted
            public int compare(String[] o1, String[] o2) {
                // get the item ids which are at index 0 of the array
                String itemIdOne = o1[0];
                String itemIdTwo = o2[0];
                // sort on item id
                if (itemIdOne != null && itemIdTwo != null)
                    return itemIdOne.compareTo(itemIdTwo);
                else
                    return 0;
            }
        });

        sortinfile(array);
        for (int i = 0; i < array.length; i++) {
            ArrayList<String> itemList = new ArrayList<>();
            
            String str = array[i][1];
            if (str != null) {
                StringTokenizer splitstring = new StringTokenizer(str, md);
                while (splitstring.hasMoreTokens()) {
                    itemList.add(splitstring.nextToken());
                }
            }
            else
                break;

            for (int j = 0; j < susp.length; j++) {
                if (itemList.contains(susp[j])) {
                    if (hs.add(array[i][0])) {
                        System.out.println(array[i][0] + " is suspicious");
                    }
                    break;
                }
            }
        }
        return hs;
    }

    private static void sortinfile(String[][] array) {
        try {

            FileWriter fw = new FileWriter("output.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < array.length; i++) {
                if (array[i][0] != null) {
                    bw.write(array[i][0] + " ----- " + array[i][1] + "\n");
                }
            }
            bw.close();
        }
        catch (Exception e) {
            System.out.println("File output " + e);
        }
    }
}
