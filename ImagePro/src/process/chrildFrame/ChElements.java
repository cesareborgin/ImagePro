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
            loadflag2 = false,    //第2幅图像载入标志
            runflag = false,    //图像处理执行标志
            mergeflag = false;    //图像合成标志 

    //参数选择面板
    JButton okButton;
    JDialog dialog;

    Common common;
    Elements elements;

    public ChElements() {
        setTitle(" 彩色图像的基本变换");
        this.setBackground(Color.lightGray);

        //菜单界面
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
            //文件选择对话框
            JFileChooser chooser = new JFileChooser();
            common.chooseFile(chooser, "G:/images", 0);//设置默认目录,过滤文件
            int r = chooser.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {
                String name = chooser.getSelectedFile().getAbsolutePath();

                if (loadflag2 || runflag) {
                    loadflag = false;
                    loadflag2 = false;
                    runflag = false;
                }
                if (!loadflag) {
                    //装载图像
                    iImage = common.openImage(name, tracker);
                    //取载入图像的宽和高
                    iw = iImage.getWidth(null);
                    ih = iImage.getHeight(null);
                    repaint();
                } else if (loadflag && (!runflag)) {
                    iImage2 = common.openImage(name, tracker);
                    common.draw(graph, iImage, "原图1", iImage2, "原图2");
                    repaint();
                }
            }
            if (loadflag) loadflag2 = true;
            else loadflag = true;
        } else if (evt.getSource() == grayItem) {
            setTitle("  彩色图像转变为灰度图像 ");
            if (loadflag) {
                pixels = common.grabber(iImage, iw, ih);

                //转变为灰度图像
                pixels = elements.toGray(pixels, iw, ih);

                //将数组中的象素产生一个图像
                showPix(graph, pixels, "转变结果");
                repaint();
            }
        } else if (evt.getSource() == threshItem) {
            setTitle("灰度阈值变换");
            if (loadflag) {
                pixels = common.grabber(iImage, iw, ih);

                int thresh = common.getParam("输入阈值(0~255)", "100");//获取阈值

                if (thresh > 255)
                    thresh = 255;
                else if (thresh < 0)
                    thresh = 0;

                //对图像进行阈值变换
                pixels = elements.thresh(pixels, iw, ih, thresh);

                //将数组中的象素产生一个图像
                showPix(graph, pixels, "变换结果");
                repaint();
            } else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == linearItem) {
            setTitle(" 灰度线性变换");

            if (loadflag) {
                Parameters pp = new Parameters("参数", "钭率:",
                        "截距:", "1.2", "10");
                setPanel(pp, "线性变换");

                float p = pp.getPadx();
                int q = (int) pp.getPady();

                pixels = common.grabber(iImage, iw, ih);

                //对图像进行进行线性拉伸
                pixels = elements.linetrans(pixels, iw, ih, p, q);

                //将数组中的象素产生一个图像
                showPix(graph, pixels, "变换结果");
                repaint();
            } else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == okButton)
            dialog.dispose();
        else if (evt.getSource() == falsecItem) {
            setTitle(" 灰度图变为彩色图 ");
            if (loadflag) {
                pixels = common.grabber(iImage, iw, ih);

                int p = 64, q = 192;

                //对图像进行进行线性拉伸
                pixels = elements.falseColor(pixels, iw, ih, p, q);

                //将数组中的象素产生一个图像
                showPix(graph, pixels, "变换结果");
                repaint();
            } else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == combinItem)//图像融合
        {
            setTitle(" 图像融合  ");
            if (loadflag && loadflag2) {
                float p1 = 0.5f, p2 = 0.5f;   //图像融合系数，p1+p2=1

                //将第1个图像iImage的像素赋于数组pixels1
                int[] pixels1 = common.grabber(iImage, iw, ih);

                //将第2个图像iImage2的像素赋于数组pixels2
                int[] pixels2 = common.grabber(iImage2, iw, ih);

                int[] mpixels = elements.combine(pixels1, pixels2, iw, ih, p1, p2);

                //将数组中的象素产生一个图像
                ImageProducer ip = new MemoryImageSource(iw, ih, mpixels, 0, iw);
                oImage = createImage(ip);
                ResultShow result = new ResultShow(oImage);
                result.show();
                runflag = true;
            } else
                JOptionPane.showMessageDialog(null, "请先打开两幅图像!");
        }
    }


    public void paint(Graphics g) {
        if ((iImage != null) && (!mergeflag)) {
            g.clearRect(0, 0, 260, 350);
            g.drawImage(iImage, 5, 50, null);
            g.drawString("原图", 120, 320);
        } else if (mergeflag) {
            g.clearRect(0, 0, 260, 350);
            g.drawImage(bImage, 5, 50, null);
            g.drawString("前景与背景图", 100, 320);
        }
    }

    public void showPix(Graphics graph, int[] pixels, String str) {
        //将数组中的象素产生一个图像
        ImageProducer ip = new MemoryImageSource(iw, ih, pixels, 0, iw);
        Image oImage = createImage(ip);
        common.draw(graph, iImage, "原图", oImage, str);
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
        okButton = new JButton("确定");
        okButton.addActionListener(this);

        dialog = new JDialog(this, s + " 参数选择", true);

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
        //菜单界面
        Menu fileMenu = new Menu("文件");
        openItem = new MenuItem("打开");
        openItem.addActionListener(this);
        fileMenu.add(openItem);

        exitItem = new MenuItem("退出");
        exitItem.addActionListener(this);
        fileMenu.add(exitItem);

        Menu processMenu = new Menu("图像处理");
        grayItem = new MenuItem("变为灰度图像");
        grayItem.addActionListener(this);
        processMenu.add(grayItem);

        processMenu.addSeparator();
        threshItem = new MenuItem("阈值变换");
        threshItem.addActionListener(this);
        processMenu.add(threshItem);

        processMenu.addSeparator();
        linearItem = new MenuItem("线性变换");
        linearItem.addActionListener(this);
        processMenu.add(linearItem);

        processMenu.addSeparator();
        falsecItem = new MenuItem("伪彩色处理");
        falsecItem.addActionListener(this);
        processMenu.add(falsecItem);

        processMenu.addSeparator();
        combinItem = new MenuItem("图像融合");
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
