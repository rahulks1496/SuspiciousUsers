package twitterinfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

public class Result {

    public Map<String, Integer> calcfinal(TreeSet<String> bagset, TreeSet<String> ngramset,
            TreeSet<String> gammaset) {

        TreeSet<String> allset = new TreeSet<String>();
        allset.addAll(bagset);
        allset.addAll(ngramset);
        allset.addAll(gammaset);

        HashMap<String, Integer> finalset = new HashMap<>();
        Iterator<String> itr = allset.iterator();

        while (itr.hasNext()) {

            int score = 0;
            String str = itr.next();
            if (bagset.contains(str))
                score += 25;
            if (ngramset.contains(str))
                score += 35;
            if (gammaset.contains(str))
                score += 40;

            if (score >= 60)
                finalset.put(str, score);
        }

        return finalset;
    }
}
