package twitterinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Ngram {

    static String[][] array = new String[100][2];

    static String[] negations = {"no", "not"};

    static String md = "://.//&//!// ";

    public TreeSet<String> ngram(String[] susp, String[][] array) {
        TreeSet<String> out = new TreeSet<String>();
        for (int j = 0; j < array.length; j++) {
            String str1 = array[j][1];
            List<String> itemList = new ArrayList<String>();

            if (str1 != null) {
                str1.toLowerCase();
                StringTokenizer splitstring = new StringTokenizer(str1, md);
                while (splitstring.hasMoreTokens()) {
                    itemList.add(splitstring.nextToken());
                }
                String name = array[j][0];

                String o = getNgram(itemList, name, susp);
                if (o != null) {
                    out.add(o);
                }
            }
        }

        for (String o : out) {
            System.out.println(o);
        }
        return out;
    }

    private static String getNgram(List<String> itemList, String name, String[] susp) {
        int index = 0;
        int iter = 0;
        boolean suspicious = false;
        List<String> ng = new ArrayList<String>();
        int count = 0;

        for (int i = 0; i < susp.length; i++) {
            count += Collections.frequency(itemList, susp[i]);
        }

        for (int i = 0; i < susp.length; i++) {
            index = itemList.indexOf(susp[i]);

            if (index > 2) {
                iter = 3;
            }
            else {
                iter = index;
            }
            if (iter > 0) {
                for (int n = 1; n <= iter; n++) {
                    for (String ngram : ngrams(n, itemList, susp[i])) {
                        ng.add(ngram);
                    }
                }
                suspicious = true;
                break;
            }
            else if (iter == 0) {
                return name;
            }
        }
        if (suspicious) {
            if (classify(ng, negations)) {
                return name;
            }
            else if (count > 1) {
                Iterator itr = itemList.iterator();
                while (itr.hasNext() && index >= 0) {
                    itr.next();
                    itr.remove();
                    index--;
                }
                return getNgram(itemList, name, susp);
            }
        }
        return null;
    }

    private static List<String> ngrams(int n, List<String> word, String sus) {
        List<String> ngrams = new ArrayList<String>();
        int index = word.indexOf(sus);
        for (int i = index - n; i < index - n + 1; i++)
            ngrams.add(concat(word, i, i + n));
        return ngrams;
    }

    private static String concat(List<String> words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words.get(i));
        return sb.toString();
    }

    private static boolean classify(List<String> ng, String[] negations) {
        boolean suspicious = true;
        for (String ngram : ng) {
            if (suspicious) {
                for (int i = 0; i < negations.length; i++) {
                    if (ngram.indexOf(negations[i].toLowerCase()) == -1) {
                        suspicious = true;
                    }
                    else {
                        suspicious = false;
                        break;
                    }
                }
            }
        }
        return suspicious;
    }
}
