package twitterinfo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FrontEnd {

    private static String[][] array;

    private static String[] susp = {"bomb", "terrorist", "attack", "deplorable", "meets", "Heritage", "Video",
            "and", "in"};

    private static TreeSet<String> bagSet = new TreeSet<>();

    private static TreeSet<String> ngramSet = new TreeSet<>();

    private static TreeSet<String> gammaSet = new TreeSet<>();

    private static Map<String, Integer> resultMap = new HashMap<>();

    private static GridBagConstraints gbc = new GridBagConstraints();

    public static void main(String[] args)
            throws Exception {
        TwitterInfo twit = new TwitterInfo();
        //array = twit.callTwit();
        JFrame frame = new JFrame();
        frame.setSize(2000, 2000);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        gbc.insets = new Insets(10, 10, 10, 10);

        display(panel);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void display(JPanel panel) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton b1 = new JButton("Bag of Words");
        panel.add(b1, gbc);

        JPanel bag = new JPanel(new GridBagLayout());
        gbc.gridy = 1;
        panel.add(bag, gbc);

        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                bag.removeAll();
                Bag b = new Bag();
                bagSet = b.callBag(susp, array);
                for (String o : bagSet) {
                    gbc.gridy++;
                    bag.add(new JLabel(o), gbc);
                }
            }
        });

        gbc.gridx = 2;
        gbc.gridy = 0;
        JButton b2 = new JButton("N Grams");
        panel.add(b2, gbc);

        JPanel ngram = new JPanel(new GridBagLayout());
        gbc.gridy = 1;
        panel.add(ngram, gbc);

        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                ngram.removeAll();
                Ngram n = new Ngram();
                ngramSet = n.ngram(susp, array);
                for (String o : ngramSet) {
                    gbc.gridy++;
                    ngram.add(new JLabel(o), gbc);
                }
            }
        });
        gbc.gridx = 4;
        gbc.gridy = 0;
        JButton b3 = new JButton("Gamma N Words");
        panel.add(b3, gbc);

        JPanel gamma = new JPanel(new GridBagLayout());
        gbc.gridy = 1;
        panel.add(gamma, gbc);

        b3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                gamma.removeAll();
                Gamma g = new Gamma();
                gammaSet = g.callgamma(susp, array);
                for (String o : gammaSet) {
                    gbc.gridy++;
                    gamma.add(new JLabel(o), gbc);
                }
            }
        });
        gbc.gridx = 6;
        gbc.gridy = 0;
        JButton b4 = new JButton("Final Result");
        panel.add(b4, gbc);

        JPanel result = new JPanel(new GridBagLayout());
        gbc.gridy = 1;
        panel.add(result, gbc);

        b4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                result.removeAll();
                Result r = new Result();
                resultMap = r.calcfinal(bagSet, ngramSet, gammaSet);
                for (Map.Entry<String, Integer> o : resultMap.entrySet()) {
                    gbc.gridy++;
                    result.add(new JLabel(o.getKey() + " = " + o.getValue()), gbc);
                }
            }
        });

        gbc.gridx = 8;
        gbc.gridy = 0;
        reset(panel);
    }

    private static void reset(JPanel panel) {
        JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                panel.removeAll();
                display(panel);
            }
        });
        panel.add(reset, gbc);
    }

}
