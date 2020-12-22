//package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class CButton extends JButton implements MouseListener {

    public CButton() {
        super();
        setFont(new Font("Arial", Font.BOLD, 20));
        setBackground(new Color(30, 30, 30));
        setForeground(new Color(100, 100, 100));
        setBorder(new LineBorder(new Color(30, 30, 30, 0)));
        addMouseListener(this);
    }

    public CButton(String name) {
        super(name);
        setFont(new Font("Arial", Font.BOLD, 20));
        setBackground(new Color(30, 30, 30));
        setForeground(new Color(100, 100, 100));
        setBorder(new LineBorder(new Color(30, 30, 30, 0)));
        addMouseListener(this);
    }

    public CButton(String name, Icon icon) {
        super(name, icon);
        setFont(new Font("Arial", Font.BOLD, 20));
        setBackground(new Color(30, 30, 30));
        setForeground(new Color(100, 100, 100));
        setBorder(new LineBorder(new Color(30, 30, 30, 0)));
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        setBackground(new Color(48, 48, 48));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setBackground(new Color(48, 48, 48));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setBackground(new Color(30, 30, 30));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setBackground(new Color(48, 48, 48));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBackground(new Color(30, 30, 30));
    }
}
