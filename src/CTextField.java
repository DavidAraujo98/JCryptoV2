//package src;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Font;

public class CTextField extends JTextField {

    public CTextField() {
        super();
        setFont(new Font("Arial", Font.BOLD, 15));
        setBackground(new Color(30, 30, 30));
        setForeground(new Color(100, 100, 100));
        setBorder(new LineBorder(new Color(30, 30, 30, 0)));
        setEditable(false);
    }

    public CTextField(String name) {
        super(name);
        setFont(new Font("Arial", Font.BOLD, 15));
        setBackground(new Color(30, 30, 30));
        setForeground(new Color(100, 100, 100));
        setBorder(new LineBorder(new Color(30, 30, 30, 0)));
        setEditable(false);
    }

}
