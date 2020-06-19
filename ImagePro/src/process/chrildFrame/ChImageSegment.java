package process.chrildFrame;

import process.algorithms.ImageEnhance;
import process.algorithms.ImageSegment;
import process.common.Common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;

public class ChImageSegment extends JFrame implements ActionListener {
    Image iImage, iImage2, oImage;

    int iw, ih;
    int[] pixels;

    boolean loadflag = false,
            loadflag2 = false,
            runflag = false;    //图像处理执行标志

    ImageEnhance enhance;
    ImageSegment segment;
    Common common;

    public ChImageSegment() {
        setTitle(" 图像分割");
        this.setBackground(Color.lightGray);

        //菜单界面
        setMenu();

        enhance = new ImageEnhance();
        segment = new ImageSegment();
        common = new Common();

        setSize(530, 330);
        setLocation(750, 300);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        Graphics graph = getGraphics();

        if (evt.getSource() == openItem) {
            //文件选择对话框
            JFileChooser chooser = new JFileChooser();
            common.chooseFile(chooser, "G:/images", 0);//设置默认目录,过滤文件
            int r = chooser.showOpenDialog(null);

            MediaTracker tracker = new MediaTracker(this);

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
                    loadflag = true;
                } else if (loadflag && (!runflag)) {
                    iImage2 = common.openImage(name, tracker);
                    common.draw(graph, iImage, "原图", iImage2, "原图2");
                    loadflag2 = true;
                }
            }
        } else if (evt.getSource() == KirschItem) {
            setTitle(" 边缘检测 Kirsch算子");
            if (loadflag)//1:Kirsch
                show(graph, 1, "输入阈值(整数400~600)", "500", "Kirsch");
            else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == LaplaceItem) {
            setTitle(" 边缘检测 Laplace算子");
            if (loadflag)//2:Laplace
                show(graph, 2, "输入阈值(整数50~150)", "100", "Laplace");
            else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == LOGItem) {
            setTitle(" 边缘检测 LOG算子 ");
            if (loadflag)//20:LOG
                show(graph, 20, "输入阈值(整数50~150)", "100", "LOG");
            else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == LOG1Item) {
            setTitle(" 边缘检测 LOG(修正)算子 ");
            if (loadflag)//21:LOG(修正)
                show(graph, 21, "输入阈值(整数50~150)", "100", "LOG(修正)");
            else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == PrewittItem) {
            setTitle(" 边缘检测 Prewitt算子 ");
            if (loadflag)//3:Prewitt
                show(graph, 3, "输入阈值(整数100~250)", "180", "Prewitt");
            else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == RobertsItem) {
            setTitle("边缘检测 Roberts算子 ");
            if (loadflag) //0:Roberts算法
                show(graph, 0, "输入阈值(整数30~100)", "50", "Roberts");
            else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == Roberts1Item) {
            setTitle(" 边缘检测 平面拟合Roberts算法");
            if (loadflag)//0:一次平面拟合Roberts算法
                show(graph, 0, "输入阈值(整数20~60)", "40", "Roberts1");
            else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == SobelItem) {
            setTitle(" 边缘检测 Sobel算子 ");
            if (loadflag)//5:Sobel
                show(graph, 5, "输入阈值(整数100~300)", "200", "Sobel");
            else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == thresh1Item) {
            setTitle(" 阈值分割 ");
            if (loadflag)//6: 1维阈值分割
                show(graph, 6, "1维阈值分割");
        } else if (evt.getSource() == thresh2Item) {
            setTitle(" 2维阈值分割 ");
            if (loadflag)//7: 2维阈值分割
                show(graph, 7, "2维阈值分割");
        } else if (evt.getSource() == bthreshItem) {
            setTitle(" 最佳阈值分割 ");
            if (loadflag)//8:最佳阈值分割
                show(graph, 8, "最佳阈值分割");
        } else if (evt.getSource() == otsuItem) {
            setTitle(" Otsu算法分割 ");
            if (loadflag)//9: Otsu算法
                show(graph, 9, "Otsu算法");
        } else if (evt.getSource() == minusImItem) {
            setTitle(" 差影法 分割");
            if (loadflag && loadflag2)
                show(graph, 10, "差影法");
            else
                JOptionPane.showMessageDialog(null, "请先打开两幅图像!");
        }
    }

    public void paint(Graphics g) {
        if (loadflag) {
            g.clearRect(0, 0, 530, 350);
            g.drawImage(iImage, 5, 50, null);
            g.drawString("原图", 120, 320);
        }
    }

    /*************************************************
     * str  - 对话框说明字符串
     * type - 算子型号. 0:Roberts, Roberts1
     *                  1:Kirsch 
     *                  2:Laplace 20:LOG  21:LOG(修正)
     *                  3:Prewitt 5:Sobel
     * val  - 对话框参数默认值字符串
     * nam  - 输出图像标题字符串
     *************************************************/
    public void show(Graphics graph, int type, String str, String val, String nam) {
        pixels = common.grabber(iImage, iw, ih);
        if (type != 0)
            pixels = enhance.detect(pixels, iw, ih, type,
                    common.getParam(str, val), true);
        else if (nam.equals("Roberts"))
            pixels = enhance.robert(pixels, iw, ih,
                    common.getParam(str, val), true);
        else if (nam.equals("Roberts1"))
            pixels = enhance.robert1(pixels, iw, ih,
                    common.getParam(str, val));
        //将数组中的象素产生一个图像
        ImageProducer ip = new MemoryImageSource(iw, ih, pixels, 0, iw);
        oImage = createImage(ip);
        common.draw(graph, iImage, "原图", oImage, nam);
        runflag = true;
    }

    /*************************************************
     * type - 型号. 0:BLPF 1:BHPF 2:ELPF 3:EHPF
     * name - 输出图像标题字符串
     *************************************************/
    public void show(Graphics graph, int type, String name) {
        int th;
        byte[] imb;
        pixels = common.grabber(iImage, iw, ih);
        switch (type) {
            case 6: //1维阈值分割
                th = segment.segment(pixels, iw, ih);
                pixels = common.thSegment(pixels, iw, ih, th);
                break;
            case 7: //2维阈值分割
                th = segment.segment2(pixels, iw, ih);
                pixels = common.thSegment(pixels, iw, ih, th);
                break;
            case 8: //最佳阈值分割
                th = segment.bestThresh(pixels, iw, ih);
                pixels = common.thSegment(pixels, iw, ih, th);
                break;
            case 9: //Otsu算法
                th = segment.otsuThresh(pixels, iw, ih);
                pixels = common.thSegment(pixels, iw, ih, th);
                break;
            case 10://差影法
                int[] pix2 = common.grabber(iImage2, iw, ih);
                pixels = segment.minusImage(pixels, pix2, iw, ih);
                break;
        }
        //将数组中的象素产生一个图像
        ImageProducer ip = new MemoryImageSource(iw, ih, pixels, 0, iw);
        oImage = createImage(ip);
        common.draw(graph, iImage, "原图", oImage, name);
        runflag = true;
    }

    private void closeWin() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    //菜单界面
    public void setMenu() {
        Menu fileMenu = new Menu("文件");
        openItem = new MenuItem("打开");
        openItem.addActionListener(this);
        fileMenu.add(openItem);

        exitItem = new MenuItem("退出");
        exitItem.addActionListener(this);
        fileMenu.add(exitItem);

        Menu edgeMenu = new Menu("边缘检测");
        KirschItem = new MenuItem("Kirsch算子");
        KirschItem.addActionListener(this);
        edgeMenu.add(KirschItem);

        edgeMenu.addSeparator();
        LaplaceItem = new MenuItem("Laplace算子");
        LaplaceItem.addActionListener(this);
        edgeMenu.add(LaplaceItem);

        LOGItem = new MenuItem("LOG算子");
        LOGItem.addActionListener(this);
        edgeMenu.add(LOGItem);

        LOG1Item = new MenuItem("LOG算子(修正)");
        LOG1Item.addActionListener(this);
        edgeMenu.add(LOG1Item);

        edgeMenu.addSeparator();
        PrewittItem = new MenuItem("Prewitt算子");
        PrewittItem.addActionListener(this);
        edgeMenu.add(PrewittItem);

        edgeMenu.addSeparator();
        RobertsItem = new MenuItem("Roberts算子");
        RobertsItem.addActionListener(this);
        edgeMenu.add(RobertsItem);

        Roberts1Item = new MenuItem("Roberts算子(平面拟合)");
        Roberts1Item.addActionListener(this);
        edgeMenu.add(Roberts1Item);

        edgeMenu.addSeparator();
        SobelItem = new MenuItem("Sobel算子");
        SobelItem.addActionListener(this);
        edgeMenu.add(SobelItem);

        Menu imSegMenu = new Menu("图像分割");
        thresh1Item = new MenuItem("1维阈值分割");
        thresh1Item.addActionListener(this);
        imSegMenu.add(thresh1Item);

        thresh2Item = new MenuItem("2维阈值分割");
        thresh2Item.addActionListener(this);
        imSegMenu.add(thresh2Item);

        imSegMenu.addSeparator();
        bthreshItem = new MenuItem("最佳阈值分割");
        bthreshItem.addActionListener(this);
        imSegMenu.add(bthreshItem);

        imSegMenu.addSeparator();
        otsuItem = new MenuItem("Otsu算法");
        otsuItem.addActionListener(this);
        imSegMenu.add(otsuItem);

        imSegMenu.addSeparator();
        minusImItem = new MenuItem("差影法");
        minusImItem.addActionListener(this);
        imSegMenu.add(minusImItem);

        MenuBar menuBar = new MenuBar();
        menuBar.add(fileMenu);
        menuBar.add(edgeMenu);
        menuBar.add(imSegMenu);
        setMenuBar(menuBar);
    }

    MenuItem openItem;
    MenuItem exitItem;
    //边缘检测
    MenuItem KirschItem;
    MenuItem LaplaceItem;
    MenuItem LOGItem;
    MenuItem LOG1Item;
    MenuItem PrewittItem;
    MenuItem RobertsItem;
    MenuItem Roberts1Item;
    MenuItem SobelItem;

    MenuItem thresh1Item;       //一维阈值分割  
    MenuItem thresh2Item;       //二维阈值分割  
    MenuItem bthreshItem;       //最佳阈值分割
    MenuItem otsuItem;          //Otsu算法分割
    MenuItem minusImItem;       //差影法

}
