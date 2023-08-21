package twitterinfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class Gamma {

    static String[][] array = new String[100][2];
    static HashMap<String, Integer> hm = new HashMap<String, Integer>();
    static HashMap<String, Integer> hm1 = new HashMap<String, Integer>();
    static HashMap<String, Integer> hm2 = new HashMap<String, Integer>();
    static int threshold = 5;
    static String md = "://.//&//!// ";

    TreeSet<String> hs = new TreeSet<String>();

    public TreeSet<String> callgamma(String [] susp,String[][] array) 
    {
        int n = 0;
        int count = 0;
        int count1 = 1;
        int numsusp=0;

        for (int i = 0; i < array.length; i++) 
        {
            ArrayList<String> itemList = new ArrayList<>();
            String[] items = new String[50];
            String name = array[i][0];
            String str = array[i][1];
            if (str != null) 
            {
                StringTokenizer splitstring = new StringTokenizer(str, md);
                while (splitstring.hasMoreTokens()) 
                {
                    itemList.add(splitstring.nextToken());
                }

            }
            else
                break;

            for (int j = 0; j < susp.length; j++) 
            {
                if (itemList.contains(susp[j])) 
                {
                    numsusp=1;
                    int x = Collections.frequency(itemList, susp[j]);
                    count += x;
                }
            }
            if (hm.containsKey(name)) 
            {
                int val = hm.get(name);
                count += val;
                hm.put(name, count);
            }
            else
                hm.put(name, count);
            
            if (hm2.containsKey(name)) 
            {
               
                int val = hm2.get(name);
                
                numsusp += val;
                //val+=numsusp
                hm2.put(name, numsusp);
            }
            else
                hm2.put(name, numsusp);
            
            

            if (hm1.containsKey(name)) 
            {
                int val = hm1.get(name);
                count1 = val + 1;
                hm1.put(name, count1);

            }
            else
                hm1.put(name, count1);

            count = 0;
            count1 = 1;
            numsusp=0;
        }
        System.out.println(hm);
        System.out.println(hm1);
        System.out.println(hm2);
        double thresholdword=0;
        double thresholdsusp=0;
        Iterator it = hm.entrySet().iterator();
        Iterator it1 = hm1.entrySet().iterator();
        Iterator it2 = hm2.entrySet().iterator();
        while (it.hasNext()) 
        {
            Map.Entry pair1 = (Map.Entry)it1.next();
            int numtweet=(int)pair1.getValue();
            thresholdword=numtweet*0.6;
            //System.out.println((int)threshold);
            Map.Entry pair2 = (Map.Entry)it2.next();
            int numsuspicious=(int)pair2.getValue();
            thresholdsusp=numtweet*0.25;
            
            
            
            Map.Entry pair = (Map.Entry)it.next();
            int v = (int)pair.getValue();
            if (v >= (int)thresholdword && numsuspicious >= (int)thresholdsusp ) 
            {
                hs.add(pair.getKey().toString());
                System.out.println(pair.getKey() + "is suspicious");
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        //System.out.println(hs);
        return hs;
    }

}
