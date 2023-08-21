/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterinfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.net.ssl.SSLEngineResult.Status;

import org.json.JSONArray;

import twitter4j.Paging;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Lenovo
 */
public class TwitterInfo {
    private String callUrlAndParseResult(String langFrom, String langTo, String word)
            throws Exception {

        String url = "https://translate.googleapis.com/translate_a/single?" + "client=gtx&" + "sl=" + langFrom
                + "&tl=" + langTo + "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return parseResult(response.toString());
    }

    private String parseResult(String inputJson)
            throws Exception {

        JSONArray jsonArray = new JSONArray(inputJson);
        JSONArray jsonArray2 = (JSONArray)jsonArray.get(0);
        JSONArray jsonArray3 = (JSONArray)jsonArray2.get(0);

        return jsonArray3.get(0).toString();
    }

    public String[][] callTwit()
            throws TwitterException, FileNotFoundException, IOException, Exception {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled(true).setOAuthConsumerKey("NdpqDqTu4IMPhSggfMNpYL15a")
                .setOAuthConsumerSecret("nCmCchRPHVTqoBacDrrCAHqx1hxjtfFTltBP0f1wV6I3g3sIfv")
                .setOAuthAccessToken("960813845550804992-VpxjrJ3Z1mKf9uGw221T72aAO5w6sim")
                .setOAuthAccessTokenSecret("hCIj0CcOUuHIDwTmYK7240ATikJH17vUfemUQoUABZGRK");

        TwitterFactory tf = new TwitterFactory(configurationBuilder.build());
        twitter4j.Twitter twitter = tf.getInstance();
        Paging paging;
        paging = new Paging();
        paging.setCount(1);
        List<Status> status = twitter.getHomeTimeline(paging);
        ArrayList<String> contentList = new ArrayList();
        String content = null;

        ArrayList<String> contentList2 = new ArrayList();
        String content2 = null;

        File file = new File("RetrievedLang.txt");
        File file1 = new File("RemovedDuplicatesLang.txt");
        File file2 = new File("RetrievedEnglish.txt");
        File file3 = new File("RemovedDuplicatesEnglish.txt");
        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);

        FileWriter fw1 = new FileWriter(file2.getAbsoluteFile(), true);
        BufferedWriter bw2 = new BufferedWriter(fw1);
        String data3;

        for (Status s : status) {
            // content = s.getUser().getScreenName() + "------" + s.getText();
            content = s.getUser().getName() + "------" + s.getText();
            contentList.add(content);
            String data = s.getUser().getName() + "      ";
            String data2 = s.getText() + "        ";
            data3 = s.getLang();
            content2 = data3;
            contentList2.add(content2);

            TwitterInfo http = new TwitterInfo();
            // String word = http.callUrlAndParseResult(data3, "en", content);
            String namelang = "";
            String tweetlang = "";
            for (int j = 0; j < content.length(); j++) {
                char c = content.charAt(j);
                if (c != '-') {
                    namelang += c;
                }
                else {
                    tweetlang = content.substring(j + 6);
                    break;
                }
            }

            try {
                String nameEnglish = http.callUrlAndParseResult(data3, "en", namelang);

                bw.write(namelang + "------");
                bw.write(tweetlang);
                bw.newLine();

                String tweetEnglish = http.callUrlAndParseResult(data3, "en", tweetlang);

                bw2.write(nameEnglish + "------");
                bw2.write(tweetEnglish);
                bw2.newLine();
            }
            catch (Exception e) {
                System.out.println("Exc");
            }

        }

        if (bw != null)
            bw.close();

        if (fw != null)
            fw.close();

        if (bw2 != null)
            bw2.close();

        if (fw1 != null)
            fw1.close();

        FileWriter pw = new FileWriter(file1.getAbsoluteFile());
        BufferedReader br1 = new BufferedReader(new FileReader("RetrievedLang.txt"));

        String line = br1.readLine();

        HashSet<String> hs = new HashSet<String>();

        while (line != null) {

            if (hs.add(line)) {
                pw.write(line);
                pw.write(System.lineSeparator());
            }
            line = br1.readLine();
        }
        br1.close();
        pw.close();

        FileWriter xw = new FileWriter(file3.getAbsoluteFile());
        BufferedReader xr1 = new BufferedReader(new FileReader("RetrievedEnglish.txt"));

        String line3 = xr1.readLine();

        // set store unique values
        HashSet<String> hs1 = new HashSet<String>();

        // loop for each line of input.txt
        while (line3 != null) {
            // write only if not
            // present in hashset
            if (hs1.add(line3)) {
                xw.write(line3);
                xw.write(System.lineSeparator());
            }
            line3 = xr1.readLine();
        }
        xr1.close();
        xw.close();

        int count = 0;
        String[][] array = new String[100][2];
        FileInputStream fstream = new FileInputStream("RemovedDuplicatesEnglish.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            if (strLine.indexOf("-----") != -1) {
                String s1 = "";
                String s2 = "";
                int length = strLine.length();
                for (int i = 0; i < length; i++) {
                    char c = strLine.charAt(i);
                    if (c != '-')
                        s1 += c;
                    else {
                        s2 = strLine.substring(i + 6, length);
                        array[count][1] = s2;
                        break;
                    }

                }
                array[count][0] = s1;
                count++;
            }
        }
        br.close();

        TreeSet<String> bagset = new TreeSet<>();
        TreeSet<String> ngramset = new TreeSet<>();
        TreeSet<String> gammaset = new TreeSet<>();
        Map<String, Integer> resultset = new HashMap<>();
        // TreeSet <String> finalset = new TreeSet<>();

        String[] susp = {"bomb", "terrorist", "attack", "deplorable", "meets", "Heritage", "Video", "and",
                "in"};

        System.out.println("BAG OF WORDS\n");
        Bag b = new Bag();
        bagset = b.callBag(susp, array);
        System.out.println(bagset);
        System.out.println("\n\n\n");

        System.out.println("N GRAMS\n");
        Ngram n = new Ngram();
        ngramset = n.ngram(susp, array);
        System.out.println(ngramset);
        System.out.println("\n\n\n");

        System.out.println("GAMMA N\n");
        Gamma g = new Gamma();
        gammaset = g.callgamma(susp, array);
        System.out.println(gammaset);
        System.out.println("\n\n\n");

        Result r = new Result();
        resultset = r.calcfinal(bagset, ngramset, gammaset);
        System.out.println(resultset);

        return array;
    }

}
