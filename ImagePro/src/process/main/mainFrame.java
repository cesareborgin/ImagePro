package process.main;

import process.chrildFrame.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainFrame extends JFrame implements ActionListener {

    //�˵����

    private JFrame jf;
    JPanel jp;
    JButton jb1, jb2, jb3, jb4, jb5, jb6, jb7, jb8;
    JLabel tubiao, biaoti;

    public mainFrame() {
        setBackground(Color.green);
        biaoti = new JLabel("ͼ����๦�����");
        biaoti.setFont(new Font("����", 1, 40));
        biaoti.setForeground(Color.blue);

        this.add(biaoti);
        biaoti.setBounds(300, 30, 500, 50);
        tubiao = new JLabel(new ImageIcon("G:/images/background.png"));
        tubiao.setSize(1100, 800);
        add(tubiao, BorderLayout.SOUTH);
        //�˵�����
        setMenu();
        setSize(1100, 800);
        setLocation(420, 40);
        setVisible(true);

        getContentPane();
        setLayout(null);

        getRootPane().setBorder(
                BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLUE));
    }

    //�����ؼ��Ķ���
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == jb1) {

            new ChDigitization();

        } else if (evt.getSource() == jb2) {
            new ChElements();
        } else if (evt.getSource() == jb3) {
            new ChGeomTrans();
        } else if (evt.getSource() == jb4) {
            new ChImageEnhance();
        } else if (evt.getSource() == jb5) {
            new ChImageRestore();
        } else if (evt.getSource() == jb6) {
            new ChImageMorph();
        } else if (evt.getSource() == jb7) {
            new ChImageSegment();
        } else if (evt.getSource() == jb8) {
            new ChDigitRecog();
        }

    }

    public void setMenu() {
        jf = new JFrame();

        jp = new JPanel();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(jp, BorderLayout.CENTER);

        jb1 = new JButton("ͼ��Ĳ���������");
        jb1.addActionListener(this);
        jp.add(jb1);

        jb2 = new JButton("ͼ��Ļ�������");
        jb2.addActionListener(this);
        jp.add(jb2);

        jb3 = new JButton("ͼ��ļ��α任");
        jb3.addActionListener(this);
        jp.add(jb3);

        jb4 = new JButton("ͼ����ǿ");
        jb4.addActionListener(this);
        jp.add(jb4);

        jb5 = new JButton("ͼ��ԭ");
        jb5.addActionListener(this);
        jp.add(jb5);

        jb6 = new JButton("ͼ����̬ѧ����");
        jb6.addActionListener(this);
        jp.add(jb6);

        jb7 = new JButton("ͼ�����ֵ�ָ�");
        jb7.addActionListener(this);
        jp.add(jb7);


        jb8 = new JButton("Ŀ��ʶ��");
        jb8.addActionListener(this);
        jp.add(jb8);


    }

    private JPanel getPanelWithBorder(Border b, String BorderName) {
        JPanel p = new JPanel();
        p.add(new JLabel(BorderName));
        p.setBorder(b);
        return p;
    }

    public static void main(String[] args) {
        new mainFrame();
    }

}


