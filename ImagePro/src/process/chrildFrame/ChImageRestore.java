package process.chrildFrame;

import process.algorithms.ImageRestore;
import process.common.Common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;

public class ChImageRestore extends JFrame implements ActionListener {
    Image iImage, oImage;

    int iw, ih;
    int[] pixels;

    boolean loadflag = false,
            runflag = false;    //ͼ����ִ�б�־

    ImageRestore restore;
    Common common;

    public ChImageRestore() {
        setTitle(" ͼ��ָ� ");
        this.setBackground(Color.lightGray);

        //�˵�����
        setMenu();

        restore = new ImageRestore();
        common = new Common();

        setSize(530, 330);
        setLocation(750, 300);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        Graphics graph = getGraphics();

        if (evt.getSource() == openItem) {
            //�ļ�ѡ��Ի���
            JFileChooser chooser = new JFileChooser();
            common.chooseFile(chooser, "G:/images", 0);//����Ĭ��Ŀ¼,�����ļ�
            int r = chooser.showOpenDialog(null);

            MediaTracker tracker = new MediaTracker(this);

            if (r == JFileChooser.APPROVE_OPTION) {
                String name = chooser.getSelectedFile().getAbsolutePath();
                if (runflag) {
                    loadflag = false;
                    runflag = false;
                }

                if (!loadflag) {
                    //װ��ͼ��
                    iImage = common.openImage(name, tracker);
                    //ȡ����ͼ��Ŀ�͸�
                    iw = iImage.getWidth(null);
                    ih = iImage.getHeight(null);
                    repaint();
                    loadflag = true;
                }
            }
        } else if (evt.getSource() == blurItem) {
            setTitle(" ͼ��ָ� ");
            if (loadflag) {
                pixels = common.grabber(iImage, iw, ih);
                pixels = restore.imBlur(pixels, iw, ih);
                //�������е����ز���һ��ͼ��
                showPix(graph, pixels, "ͼ��ģ��");
            }
        } else if (evt.getSource() == restoreItem) {
            setTitle("ͼ��ָ�");
            if (loadflag) {
                pixels = restore.imRestore(pixels, iw, ih);
                //�������е����ز���һ��ͼ��
                showPix(graph, pixels, "ͼ��ָ�");
            } else
                JOptionPane.showMessageDialog(null, "���ȴ�ͼ��!");
        } else if (evt.getSource() == exitItem)
            System.exit(0);
    }

    public void paint(Graphics g) {
        if (iImage != null) {
            g.clearRect(0, 0, 530, 350);
            g.drawImage(iImage, 5, 50, null);
            g.drawString("ԭͼ", 120, 320);
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

    //�˵�����
    public void setMenu() {
        Menu fileMenu = new Menu("�ļ�");
        openItem = new MenuItem("��");
        openItem.addActionListener(this);
        fileMenu.add(openItem);

        exitItem = new MenuItem("�˳�");
        exitItem.addActionListener(this);
        fileMenu.add(exitItem);

        Menu processMenu = new Menu("ͼ����");
        blurItem = new MenuItem("ģ��ͼ��");
        blurItem.addActionListener(this);
        processMenu.add(blurItem);

        restoreItem = new MenuItem("�ָ�ͼ��");
        restoreItem.addActionListener(this);
        processMenu.add(restoreItem);

        MenuBar menuBar = new MenuBar();
        menuBar.add(fileMenu);
        menuBar.add(processMenu);
        setMenuBar(menuBar);
    }

    MenuItem openItem;
    MenuItem exitItem;
    MenuItem blurItem;
    MenuItem restoreItem;
}
