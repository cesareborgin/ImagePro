package process.main;

import process.chrildFrame.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainFrame extends JFrame implements ActionListener {

    //菜单设计

    private JFrame jf;
    JPanel jp;
    JButton jb1, jb2, jb3, jb4, jb5, jb6, jb7, jb8;
    JLabel tubiao, biaoti;

    public mainFrame() {
        setBackground(Color.green);
        biaoti = new JLabel("图像处理多功能软件");
        biaoti.setFont(new Font("隶书", 1, 40));
        biaoti.setForeground(Color.blue);

        this.add(biaoti);
        biaoti.setBounds(300, 30, 500, 50);
        tubiao = new JLabel(new ImageIcon("G:/images/background.png"));
        tubiao.setSize(1100, 800);
        add(tubiao, BorderLayout.SOUTH);
        //菜单界面
        setMenu();
        setSize(1100, 800);
        setLocation(420, 40);
        setVisible(true);

        getContentPane();
        setLayout(null);

        getRootPane().setBorder(
                BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLUE));
    }

    //各个控件的动作
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

        jb1 = new JButton("图像的采样与量化");
        jb1.addActionListener(this);
        jp.add(jb1);

        jb2 = new JButton("图像的基本处理");
        jb2.addActionListener(this);
        jp.add(jb2);

        jb3 = new JButton("图像的几何变换");
        jb3.addActionListener(this);
        jp.add(jb3);

        jb4 = new JButton("图像增强");
        jb4.addActionListener(this);
        jp.add(jb4);

        jb5 = new JButton("图像复原");
        jb5.addActionListener(this);
        jp.add(jb5);

        jb6 = new JButton("图像形态学处理");
        jb6.addActionListener(this);
        jp.add(jb6);

        jb7 = new JButton("图像的阈值分割");
        jb7.addActionListener(this);
        jp.add(jb7);


        jb8 = new JButton("目标识别");
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


