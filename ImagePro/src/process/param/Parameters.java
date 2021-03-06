/**
 * @Parameters.java
 * @Version 1.0 2010.02.14
 * @Author Xie-Hua Sun
 */
package process.param;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class Parameters extends JPanel {
    JLabel pLabel, qLabel;
    JTextField pField, qField;
    Checkbox xbox, ybox;

    public Parameters(String note, String s1, String s2,
                      String p1, String p2)              //2个文本框型
    {
        pLabel = new JLabel(s1);
        qLabel = new JLabel(s2);
        pField = new JTextField(p1, 5);
        qField = new JTextField(p2, 5);

        add(pLabel);
        add(pField);
        add(qLabel);
        add(qField);

        setBorder(new CompoundBorder(
                BorderFactory.createTitledBorder(note),
                BorderFactory.createEmptyBorder(10, 10, 50, 10)));
    }

    public Parameters(String note, String s1, String s2)   //2个按钮型
    {
        CheckboxGroup cbg = new CheckboxGroup();
        xbox = new Checkbox(s1, cbg, true);
        ybox = new Checkbox(s2, cbg, true);
        add(xbox);
        add(ybox);

        setBorder(new CompoundBorder(
                BorderFactory.createTitledBorder(note),
                BorderFactory.createEmptyBorder(10, 10, 50, 10)));
    }

    public float getPadx() {
        return Float.parseFloat(pField.getText());
    }

    public float getPady() {
        return Float.parseFloat(qField.getText());
    }

    public int getRadioState() {
        if (xbox.getState()) return 0;
        else if (ybox.getState()) return 1;
        else return 0;
    }
}
