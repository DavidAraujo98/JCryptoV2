//package src;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Font;

public class CLabel extends JLabel {

    public CLabel() {
        super();
        setFont(new Font("Arial", Font.BOLD, 15));
        setBackground(new Color(30, 30, 30));
        setForeground(new Color(180, 180, 180));
        setBorder(new LineBorder(new Color(30, 30, 30, 0)));
    }

    public CLabel(String name) {
        super(name);
        setFont(new Font("Arial", Font.BOLD, 15));
        setBackground(new Color(30, 30, 30));
        setForeground(new Color(180, 180, 180));
        setBorder(new LineBorder(new Color(30, 30, 30, 0)));
    }

}
