//package src;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class CTextArea extends JTextArea {

    public CTextArea() {
        super();
        setFont(new Font("Arial", Font.BOLD, 15));
        setBackground(new Color(30, 30, 30));
        setForeground(new Color(180, 180, 180));
        setBorder(new LineBorder(new Color(100, 100, 100)));
        setEditable(false);
        setLineWrap(true);
    }

    public CTextArea(String name) {
        super(name);
        setFont(new Font("Arial", Font.BOLD, 15));
        setBackground(new Color(30, 30, 30));
        setForeground(new Color(180, 180, 180));
        setBorder(new LineBorder(new Color(100, 100, 100)));
        setEditable(false);
        setLineWrap(true);
    }
}
