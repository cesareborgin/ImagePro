package process.chrildFrame;

import process.algorithms.Elements;
import process.common.Common;
import process.param.Parameters;
import process.param.ResultShow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;

public class ChElements extends JFrame implements ActionListener {

    Image iImage, iImage2, oImage;
    BufferedImage bImage;
    int iw, ih;
    int[] pixels;

    boolean loadflag = false,
            loadflag2 = false,    //��2��ͼ�������־
            runflag = false,    //ͼ����ִ�б�־
            mergeflag = false;    //ͼ��ϳɱ�־ 

    //����ѡ�����
    JButton okButton;
    JDialog dialog;

    Common common;
    Elements elements;

    public ChElements() {
        setTitle(" ��ɫͼ��Ļ����任");
        this.setBackground(Color.lightGray);

        //�˵�����
        setMenu();

        common = new Common();
        elements = new Elements();

        setSize(530, 330);
        setLocation(750, 300);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        Graphics graph = getGraphics();
        MediaTracker tracker = new MediaTracker(this);

        if (evt.getSource() == openItem) {
            //�ļ�ѡ��Ի���
            JFileChooser chooser = new JFileChooser();
            common.chooseFile(chooser, "G:/images", 0);//����Ĭ��Ŀ¼,�����ļ�
            int r = chooser.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {
                String name = chooser.getSelectedFile().getAbsolutePath();

                if (loadflag2 || runflag) {
                    loadflag = false;
                    loadflag2 = false;
                    runflag = false;
                }
                if (!loadflag) {
                    //װ��ͼ��
                    iImage = common.openImage(name, tracker);
                    //ȡ����ͼ��Ŀ�͸�
                    iw = iImage.getWidth(null);
                    ih = iImage.getHeight(null);
                    repaint();
                } else if (loadflag && (!runflag)) {
                    iImage2 = common.openImage(name, tracker);
                    common.draw(graph, iImage, "ԭͼ1", iImage2, "ԭͼ2");
                    repaint();
                }
            }
            if (loadflag) loadflag2 = true;
            else loadflag = true;
        } else if (evt.getSource() == grayItem) {
            setTitle("  ��ɫͼ��ת��Ϊ�Ҷ�ͼ�� ");
            if (loadflag) {
                pixels = common.grabber(iImage, iw, ih);

                //ת��Ϊ�Ҷ�ͼ��
                pixels = elements.toGray(pixels, iw, ih);

                //�������е����ز���һ��ͼ��
                showPix(graph, pixels, "ת����");
                repaint();
            }
        } else if (evt.getSource() == threshItem) {
            setTitle("�Ҷ���ֵ�任");
            if (loadflag) {
                pixels = common.grabber(iImage, iw, ih);

                int thresh = common.getParam("������ֵ(0~255)", "100");//��ȡ��ֵ

                if (thresh > 255)
                    thresh = 255;
                else if (thresh < 0)
                    thresh = 0;

                //��ͼ�������ֵ�任
                pixels = elements.thresh(pixels, iw, ih, thresh);

                //�������е����ز���һ��ͼ��
                showPix(graph, pixels, "�任���");
                repaint();
            } else
                JOptionPane.showMessageDialog(null, "���ȴ�ͼ��!");
        } else if (evt.getSource() == linearItem) {
            setTitle(" �Ҷ����Ա任");

            if (loadflag) {
                Parameters pp = new Parameters("����", "����:",
                        "�ؾ�:", "1.2", "10");
                setPanel(pp, "���Ա任");

                float p = pp.getPadx();
                int q = (int) pp.getPady();

                pixels = common.grabber(iImage, iw, ih);

                //��ͼ����н�����������
                pixels = elements.linetrans(pixels, iw, ih, p, q);

                //�������е����ز���һ��ͼ��
                showPix(graph, pixels, "�任���");
                repaint();
            } else
                JOptionPane.showMessageDialog(null, "���ȴ�ͼ��!");
        } else if (evt.getSource() == okButton)
            dialog.dispose();
        else if (evt.getSource() == falsecItem) {
            setTitle(" �Ҷ�ͼ��Ϊ��ɫͼ ");
            if (loadflag) {
                pixels = common.grabber(iImage, iw, ih);

                int p = 64, q = 192;

                //��ͼ����н�����������
                pixels = elements.falseColor(pixels, iw, ih, p, q);

                //�������е����ز���һ��ͼ��
                showPix(graph, pixels, "�任���");
                repaint();
            } else
                JOptionPane.showMessageDialog(null, "���ȴ�ͼ��!");
        } else if (evt.getSource() == combinItem)//ͼ���ں�
        {
            setTitle(" ͼ���ں�  ");
            if (loadflag && loadflag2) {
                float p1 = 0.5f, p2 = 0.5f;   //ͼ���ں�ϵ����p1+p2=1

                //����1��ͼ��iImage�����ظ�������pixels1
                int[] pixels1 = common.grabber(iImage, iw, ih);

                //����2��ͼ��iImage2�����ظ�������pixels2
                int[] pixels2 = common.grabber(iImage2, iw, ih);

                int[] mpixels = elements.combine(pixels1, pixels2, iw, ih, p1, p2);

                //�������е����ز���һ��ͼ��
                ImageProducer ip = new MemoryImageSource(iw, ih, mpixels, 0, iw);
                oImage = createImage(ip);
                ResultShow result = new ResultShow(oImage);
                result.show();
                runflag = true;
            } else
                JOptionPane.showMessageDialog(null, "���ȴ�����ͼ��!");
        }
    }


    public void paint(Graphics g) {
        if ((iImage != null) && (!mergeflag)) {
            g.clearRect(0, 0, 260, 350);
            g.drawImage(iImage, 5, 50, null);
            g.drawString("ԭͼ", 120, 320);
        } else if (mergeflag) {
            g.clearRect(0, 0, 260, 350);
            g.drawImage(bImage, 5, 50, null);
            g.drawString("ǰ���뱳��ͼ", 100, 320);
        }
    }

    public void showPix(Graphics graph, int[] pixels, String str) {
        //�������е����ز���һ��ͼ��
        ImageProducer ip = new MemoryImageSource(iw, ih, pixels, 0, iw);
        Image oImage = createImage(ip);
        common.draw(graph, iImage, "ԭͼ", oImage, str);
        runflag = true;
    }

    private void closeWin() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void setPanel(Parameters pp, String s) {
        JPanel buttonsPanel = new JPanel();
        okButton = new JButton("ȷ��");
        okButton.addActionListener(this);

        dialog = new JDialog(this, s + " ����ѡ��", true);

        Container contentPane = getContentPane();
        Container dialogContentPane = dialog.getContentPane();

        dialogContentPane.add(pp, BorderLayout.CENTER);
        dialogContentPane.add(buttonsPanel, BorderLayout.SOUTH);
        dialog.pack();
        buttonsPanel.add(okButton);
        dialog.setLocation(50, 330);
        dialog.show();
    }

    public void setMenu() {
        //�˵�����
        Menu fileMenu = new Menu("�ļ�");
        openItem = new MenuItem("��");
        openItem.addActionListener(this);
        fileMenu.add(openItem);

        exitItem = new MenuItem("�˳�");
        exitItem.addActionListener(this);
        fileMenu.add(exitItem);

        Menu processMenu = new Menu("ͼ����");
        grayItem = new MenuItem("��Ϊ�Ҷ�ͼ��");
        grayItem.addActionListener(this);
        processMenu.add(grayItem);

        processMenu.addSeparator();
        threshItem = new MenuItem("��ֵ�任");
        threshItem.addActionListener(this);
        processMenu.add(threshItem);

        processMenu.addSeparator();
        linearItem = new MenuItem("���Ա任");
        linearItem.addActionListener(this);
        processMenu.add(linearItem);

        processMenu.addSeparator();
        falsecItem = new MenuItem("α��ɫ����");
        falsecItem.addActionListener(this);
        processMenu.add(falsecItem);

        processMenu.addSeparator();
        combinItem = new MenuItem("ͼ���ں�");
        combinItem.addActionListener(this);
        processMenu.add(combinItem);


        MenuBar menuBar = new MenuBar();
        menuBar.add(fileMenu);
        menuBar.add(processMenu);
        setMenuBar(menuBar);
    }

    MenuItem openItem;
    MenuItem exitItem;
    MenuItem grayItem;
    MenuItem threshItem;
    MenuItem linearItem;
    MenuItem falsecItem;
    MenuItem combinItem;
    MenuItem mergeItem;
    MenuItem drawItem;
}
