//package src;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

public class CToggleButton extends JToggleButton {

    public CToggleButton() {
        super();
        setFont(new Font("Arial", Font.BOLD, 20));
        setBackground(new Color(30, 30, 30));
        setForeground(new Color(100, 100, 100));
        setBorder(new LineBorder(new Color(30, 30, 30, 0)));
    }

    public CToggleButton(String name) {
        super(name);
        setFont(new Font("Arial", Font.BOLD, 20));
        setBackground(new Color(30, 30, 30));
        setForeground(new Color(100, 100, 100));
        setBorder(new LineBorder(new Color(30, 30, 30, 0)));
    }

    public CToggleButton(String name, Icon icon) {
        super(name, icon);
        setFont(new Font("Arial", Font.BOLD, 20));
        setBackground(new Color(30, 30, 30));
        setForeground(new Color(100, 100, 100));
        setBorder(new LineBorder(new Color(30, 30, 30, 0)));
    }
}
