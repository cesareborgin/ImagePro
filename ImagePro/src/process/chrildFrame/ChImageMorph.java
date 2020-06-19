package process.chrildFrame;

import process.algorithms.ImageMorph;
import process.common.Common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;

public class ChImageMorph extends JFrame implements ActionListener, MouseListener {
    Image iImage, oImage;

    boolean loadflag = false,       //输入图像标志
            runflag = false,       //执行处理标志
            fillflag = false;
    int iw, ih;
    int[] pixels;

    ImageMorph morph;
    Common common;

    public ChImageMorph() {
        setTitle(" 图像形态学");
        this.setBackground(Color.lightGray);

        //菜单界面
        setMenu();

        morph = new ImageMorph();
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

                if (runflag) {
                    loadflag = false;
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
                }
            }
        } else if (evt.getSource() == erode1Item)//腐蚀
        {
            if (loadflag) {
                setTitle("正方形结构腐蚀 ");
                removeMouseListener(this);
                show(graph, 11, "正方形腐蚀");
            } else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == erode2Item) {
            if (loadflag) {
                setTitle(" 菱形结构腐蚀");
                removeMouseListener(this);
                show(graph, 12, "菱形腐蚀");
            } else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == erode3Item) {
            if (loadflag) {
                setTitle("八角结构腐蚀");
                removeMouseListener(this);
                show(graph, 13, "八角腐蚀");
            } else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == dilate1Item)//膨胀
        {
            if (loadflag) {
                setTitle(" 正方形结构膨胀");
                removeMouseListener(this);
                show(graph, 21, "正方膨胀");
            } else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == dilate2Item) {
            if (loadflag) {
                setTitle("菱形结构膨胀 ");
                removeMouseListener(this);
                show(graph, 22, "菱形膨胀");
            } else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == dilate3Item) {
            if (loadflag) {
                setTitle(" 八角结构膨胀 ");
                removeMouseListener(this);
                show(graph, 23, "八角膨胀");
            } else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == OPEN1Item)//开启
        {
            if (loadflag) {
                setTitle(" 正方形结构开启 ");
                removeMouseListener(this);
                show(graph, 31, "正方开启");
            } else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == CLOSE1Item)//闭合
        {
            if (loadflag) {
                setTitle("正方结构闭合 ");
                removeMouseListener(this);
                show(graph, 41, "正方结构闭合");
            } else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == filter1Item)//滤波1,正方结构
        {
            if (loadflag) {
                setTitle("正方结构噪声滤波 ");
                removeMouseListener(this);
                show(graph, 51, "正方滤波");
            } else
                JOptionPane.showMessageDialog(null, "请先打开图像!");

        } else if (evt.getSource() == edge1Item)//正方结构边界提取
        {
            if (loadflag) {
                setTitle("正方结构边界提取");
                removeMouseListener(this);
                show(graph, 61, "边界提取");
            } else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == fillItem)//区域填充
        {
            if (loadflag) {
                setTitle(" 区域填充");
                addMouseListener(this);
                fillflag = true;
            } else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == hmtItem)//击中未击中变换
        {
            if (loadflag) {
                setTitle("HMT击中未击中变换");
                removeMouseListener(this);
                show(graph, 71, "HMT变换");
            } else
                JOptionPane.showMessageDialog(null, "请先打开图像!");
        } else if (evt.getSource() == erodeItem)//灰度腐蚀
        {
            if (loadflag) {
                setTitle(" 灰度腐蚀 ");
                SHOW(graph, 81, "灰度腐蚀");
            }
        } else if (evt.getSource() == dilateItem)//灰度膨胀
        {
            if (loadflag) {
                setTitle(" 灰度膨胀 ");
                SHOW(graph, 91, "灰度膨胀");
            }
        } else if (evt.getSource() == OPENItem)//灰度开启
        {
            if (loadflag) {
                setTitle(" 灰度开启 ");
                SHOW(graph, 10, "灰度开启");
            }
        } else if (evt.getSource() == CLOSEItem)//灰度闭合
        {
            if (loadflag) {
                setTitle(" 灰度闭合");
                SHOW(graph, 11, "灰度闭合");
            }
        } else if (evt.getSource() == filt1Item)//滤波1
        {
            if (loadflag) {
                setTitle(" 形态学滤波1 ");
                SHOW(graph, 13, "滤波1");
            }
        } else if (evt.getSource() == filt2Item)//滤波2
        {
            if (loadflag) {
                setTitle("形态学滤波2 ");
                SHOW(graph, 14, "滤波2");
            }
        } else if (evt.getSource() == filt3Item)//滤波3
        {
            if (loadflag) {
                setTitle(" 形态学滤波3");
                SHOW(graph, 15, "滤波3");
            }
        }
    }

    public void paint(Graphics g) {
        if (loadflag) {
            g.clearRect(0, 0, 530, 350);
            g.drawImage(iImage, 5, 50, null);
            g.drawString("原图", 120, 320);
        }
        if (fillflag) {
            g.setColor(Color.red);
            g.drawString("用鼠标选择填充种子点", 40, 290);
            fillflag = false;
        }
    }

    public void mouseClicked(MouseEvent e) {
        Graphics g = getGraphics();
        pixels = common.grabber(iImage, iw, ih);

        //将ARGB图像序列pixels变为2值图像矩阵image2
        byte[] imb = common.toBinary(pixels, iw, ih);

        int ix = e.getX() - 5;
        int iy = e.getY() - 50;
        imb = morph.fill(imb, iw, ih, ix, iy);

        pixels = common.bin2Rgb(imb, iw, ih);

        //将数组中的象素产生一个图像
        ImageProducer ip = new MemoryImageSource(iw, ih, pixels, 0, iw);
        oImage = createImage(ip);
        common.draw(g, iImage, "原图", oImage, "区域填充");
        runflag = true;
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    /*************************************************
     * type - 型号. 0:BLPF 1:BHPF 2:ELPF 3:EHPF
     * name - 输出图像标题字符串
     *************************************************/
    public void show(Graphics graph, int type, String name) {
        pixels = common.grabber(iImage, iw, ih);

        //将ARGB图像序列pixels变为2值图像矩阵image2
        byte[] imb = common.toBinary(pixels, iw, ih);

        switch (type) {
            case 11: //正方形结构腐蚀
                imb = morph.erode(imb, iw, ih, 11);
                break;
            case 12: //菱形结构腐蚀
                imb = morph.erode(imb, iw, ih, 12);
                break;
            case 13: //八角结构腐蚀
                imb = morph.erode(imb, iw, ih, 13);
                break;
            case 21: //正方形结构膨胀
                imb = morph.dilate(imb, iw, ih, 21);
                break;
            case 22: //菱形结构膨胀
                imb = morph.dilate(imb, iw, ih, 22);
                break;
            case 23: //八角结构膨胀
                imb = morph.dilate(imb, iw, ih, 23);
                break;
            case 31: //正方形结构开启
                imb = morph.erode(imb, iw, ih, 11);
                imb = morph.dilate(imb, iw, ih, 21);
                break;
            case 41: //正方形结构闭合
                imb = morph.dilate(imb, iw, ih, 21);
                imb = morph.erode(imb, iw, ih, 11);
                break;
            case 51: //正方形结构滤波
                imb = morph.erode(imb, iw, ih, 11);
                imb = morph.dilate(imb, iw, ih, 21);
                imb = morph.dilate(imb, iw, ih, 21);
                imb = morph.erode(imb, iw, ih, 11);
                break;
            case 61: //正方形结构边界提取
                imb = morph.edge(imb, iw, ih, 11);
                ;
                break;
            case 71: //HMT变换
                imb = morph.hmt(imb, iw, ih);
                break;
        }
        pixels = common.bin2Rgb(imb, iw, ih);

        //将数组中的象素产生一个图像
        ImageProducer ip = new MemoryImageSource(iw, ih, pixels, 0, iw);
        oImage = createImage(ip);
        common.draw(graph, iImage, "原图", oImage, name);
        runflag = true;
    }

    public void SHOW(Graphics graph, int type, String name) {
        pixels = common.grabber(iImage, iw, ih);

        switch (type) {
            case 81://灰度腐蚀
                pixels = morph.grayErode(pixels, iw, ih, 1);
                break;
            case 91://灰度膨胀
                pixels = morph.grayDilate(pixels, iw, ih, 1);
                break;
            case 10://灰度开启
                pixels = morph.grayErode(pixels, iw, ih, 1);
                pixels = morph.grayDilate(pixels, iw, ih, 1);
                break;
            case 11://灰度闭合
                pixels = morph.grayDilate(pixels, iw, ih, 1);
                pixels = morph.grayErode(pixels, iw, ih, 1);
                break;

            case 12://滤波1
                pixels = morph.grayErode(pixels, iw, ih, 2); //开启
                pixels = morph.grayDilate(pixels, iw, ih, 2);
                pixels = morph.grayDilate(pixels, iw, ih, 2);//闭合
                pixels = morph.grayErode(pixels, iw, ih, 2);
                break;
            case 13://滤波2
                pixels = morph.grayDilate(pixels, iw, ih, 2);//闭合
                pixels = morph.grayErode(pixels, iw, ih, 2);
                pixels = morph.grayErode(pixels, iw, ih, 2); //开启
                pixels = morph.grayDilate(pixels, iw, ih, 2);
                break;
            case 14://滤波3
                pixels = morph.grayFilter(pixels, iw, ih);
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

        Menu morphMenu = new Menu("二值形态学");
        erode1Item = new MenuItem("正方腐蚀");
        erode1Item.addActionListener(this);
        morphMenu.add(erode1Item);

        erode2Item = new MenuItem("菱形腐蚀");
        erode2Item.addActionListener(this);
        morphMenu.add(erode2Item);

        erode3Item = new MenuItem("八角腐蚀");
        erode3Item.addActionListener(this);
        morphMenu.add(erode3Item);

        morphMenu.addSeparator();
        dilate1Item = new MenuItem("正方膨胀");
        dilate1Item.addActionListener(this);
        morphMenu.add(dilate1Item);

        dilate2Item = new MenuItem("菱形膨胀");
        dilate2Item.addActionListener(this);
        morphMenu.add(dilate2Item);

        dilate3Item = new MenuItem("八角膨胀");
        dilate3Item.addActionListener(this);
        morphMenu.add(dilate3Item);

        morphMenu.addSeparator();
        OPEN1Item = new MenuItem("正方结构开启");
        OPEN1Item.addActionListener(this);
        morphMenu.add(OPEN1Item);

        CLOSE1Item = new MenuItem("正方结构闭合");
        CLOSE1Item.addActionListener(this);
        morphMenu.add(CLOSE1Item);

        morphMenu.addSeparator();
        filter1Item = new MenuItem("正方结构滤波");
        filter1Item.addActionListener(this);
        morphMenu.add(filter1Item);

        edge1Item = new MenuItem("正方结构边界提取");
        edge1Item.addActionListener(this);
        morphMenu.add(edge1Item);

        fillItem = new MenuItem("区域填充");
        fillItem.addActionListener(this);
        morphMenu.add(fillItem);


        hmtItem = new MenuItem("HMT变换");
        hmtItem.addActionListener(this);
        morphMenu.add(hmtItem);

        Menu grayMenu = new Menu("灰度形态学");

        erodeItem = new MenuItem("灰度腐蚀");
        erodeItem.addActionListener(this);
        grayMenu.add(erodeItem);

        dilateItem = new MenuItem("灰度膨胀");
        dilateItem.addActionListener(this);
        grayMenu.add(dilateItem);

        OPENItem = new MenuItem("开启");
        OPENItem.addActionListener(this);
        grayMenu.add(OPENItem);

        CLOSEItem = new MenuItem("闭合");
        CLOSEItem.addActionListener(this);
        grayMenu.add(CLOSEItem);

        filt1Item = new MenuItem("形态学滤波1");
        filt1Item.addActionListener(this);
        grayMenu.add(filt1Item);

        filt2Item = new MenuItem("形态学滤波2");
        filt2Item.addActionListener(this);
        grayMenu.add(filt2Item);

        filt3Item = new MenuItem("形态学滤波3");
        filt3Item.addActionListener(this);
        grayMenu.add(filt3Item);

        MenuBar menuBar = new MenuBar();
        menuBar.add(fileMenu);
        menuBar.add(morphMenu);
        menuBar.add(grayMenu);
        setMenuBar(menuBar);
    }

    MenuItem openItem;
    MenuItem exitItem;

    MenuItem erode1Item;      //正方结构腐蚀
    MenuItem erode2Item;      //菱形结构腐蚀
    MenuItem erode3Item;      //八角结构腐蚀

    MenuItem dilate1Item;     //正方结构膨胀
    MenuItem dilate2Item;     //菱形结构膨胀
    MenuItem dilate3Item;     //八角结构膨胀
    MenuItem OPEN1Item;       //正方结构开启    
    MenuItem CLOSE1Item;      //正方结构闭合

    MenuItem filter1Item;     //正方结构滤波
    MenuItem edge1Item;       //正方结构边缘提取
    MenuItem fillItem;        //区域填充
    MenuItem hmtItem;         //击中未击中变换  

    MenuItem erodeItem;       //灰度腐蚀
    MenuItem dilateItem;      //灰度膨胀    
    MenuItem OPENItem;        //灰度开启    
    MenuItem CLOSEItem;       //灰度闭合


    MenuItem filt1Item;       //形态学滤波1,先开后闭 
    MenuItem filt2Item;       //形态学滤波2,先闭后开
    MenuItem filt3Item;       //形态学滤波3,复合滤波 
}
