package process.main;

import process.chrildFrame.ChDigitization;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test extends JFrame implements ActionListener {

    JFrame jf = new JFrame();
    JButton jb2 = new JButton("2");

    public test() {
        JPanel jp = new JPanel();
        JButton jb = new JButton("采样与量化");
        jf.add(jp);
        jp.add(jb);
        jp.add(jb2);
        jb2.addActionListener(this);
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jb_ActionPerformed(e);
            }

        });
        jf.setSize(300, 300);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == jb2) {

            new ChDigitization();

        }


    }

    public void jb_ActionPerformed(ActionEvent e) {
        new ChDigitization();
    }


    public static void main(String[] args) {
        new test();
    }

}
