//package src;

import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class JCrypto extends JFrame implements ActionListener, ItemListener {

    private static CButton[] bts = new CButton[4];
    private static CToggleButton[] ckbts = new CToggleButton[4];
    private static ButtonGroup btgg = new ButtonGroup();
    private static CTextField[] finfo = new CTextField[2];
    private static CTextArea ckres = new CTextArea("Checksum");
    private static File digest = null;
    private static String[] isize = { " bytes", " KB", " MG", " GB", " TB" };

    public static void main(String[] args) {
        new JCrypto();
    }

    public JCrypto() {
        setBackground(new Color(30, 30, 30));
        setLayout(new GridBagLayout());
        setTitle("Java Encryption Tool");
        Dimension b = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension((int) (b.getHeight() / 1.80), (int) (b.getWidth() / 4)));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel[] panels = new JPanel[6];
        panels[0] = new JPanel(new GridLayout(1, 2)); // Top buttons
        panels[1] = new JPanel(new GridLayout(4, 1)); // Center info
        panels[2] = new JPanel(new FlowLayout()); // File path
        panels[3] = new JPanel(new FlowLayout()); // File size
        panels[4] = new JPanel(new GridLayout(1, 4)); // Panel for checksum buttons
        panels[5] = new JPanel(new GridLayout(1, 2)); // Bottom buttons
        for (int i = 0; i < panels.length; i++) {
            panels[i].setBackground(new Color(30, 30, 30));
        }

        bts[0] = new CButton("Open File");
        bts[1] = new CButton("New RSA Keys");
        bts[2] = new CButton("Encrypt");
        bts[3] = new CButton("Decrypt");
        for (int i = 0; i < bts.length; i++) {
            bts[i].addActionListener(this);
        }

        ckbts[0] = new CToggleButton("MD5");
        ckbts[1] = new CToggleButton("SHA-1");
        ckbts[2] = new CToggleButton("SHA-256");
        ckbts[3] = new CToggleButton("SHA-512");
        for (int i = 0; i < ckbts.length; i++) {
            ckbts[i].addItemListener(this);
            btgg.add(ckbts[i]);
        }

        finfo[0] = new CTextField("-"); // File Path
        finfo[0].setEditable(false);
        finfo[1] = new CTextField("-"); // File Size
        finfo[1].setEditable(false);

        // Top buttons
        panels[0].add(bts[0]);
        panels[0].add(bts[1]);

        // File path
        CLabel file = new CLabel("File Path: ");
        panels[2].add(file);
        panels[2].add(finfo[0]);

        // File size
        CLabel files = new CLabel("File Size: ");
        panels[3].add(files);
        panels[3].add(finfo[1]);

        // Checksum buttons
        panels[4].add(ckbts[0]);
        panels[4].add(ckbts[1]);
        panels[4].add(ckbts[2]);
        panels[4].add(ckbts[3]);

        // Bottom buttons
        panels[5].add(bts[2]);
        panels[5].add(bts[3]);

        // Center
        panels[1].add(panels[2]);
        panels[1].add(panels[3]);
        panels[1].add(panels[4]);
        panels[1].add(ckres);

        GridBagConstraints cst = new GridBagConstraints();

        cst.fill = GridBagConstraints.BOTH;
        cst.gridx = 0;
        cst.gridy = 0;
        cst.weightx = 1;
        cst.weighty = 1;
        cst.gridwidth = 3;
        add(panels[0], cst);

        cst.fill = GridBagConstraints.BOTH;
        cst.gridx = 0;
        cst.gridy = 1;
        cst.weightx = 1;
        cst.weighty = 4;
        cst.gridwidth = 3;
        add(panels[1], cst);

        cst.fill = GridBagConstraints.BOTH;
        cst.gridx = 0;
        cst.gridy = 2;
        cst.weighty = 1;
        cst.weightx = 1;
        cst.gridwidth = 3;
        add(panels[5], cst);

        setVisible(true);
    }

    private static void checksumClear() {
        ckres.setText("Checksum");
        for (int i = 0; i < ckbts.length; i++) {
            ckbts[i].setSelected(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CButton x = (CButton) e.getSource();
        if (x == bts[0]) {
            try {
                checksumClear();
                Path ph = FileHandler.getPath(false);
                digest = ph.toFile();
                finfo[0].setText(digest.getAbsolutePath());
                long y = Files.size(ph);
                int i = 1;
                while (i <= ((int) (Math.log10(y) + 1) / 4)) {
                    y = y / 1024;
                    i++;
                }
                finfo[1].setText(String.valueOf(y) + isize[i - 1]);
                setVisible(true);
            } catch (NullPointerException e2) {
                JOptionPane.showMessageDialog(null, "No file selected");
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "File not found or invalid");
            }
        } else if (x == bts[1]) {
            try {
                Encryptor.outputRSAKeyPair(null);
            } catch (NoSuchAlgorithmException | IOException e1) {
                e1.printStackTrace();
            }
        } else if (x == bts[2]) {
            int r = JOptionPane.showConfirmDialog(null, "Do you want to load a key?", "Provide key",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (r == JOptionPane.NO_OPTION) {
                try {
                    Encryptor.encrypt(this.digest, null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else if (r == JOptionPane.YES_OPTION) {
                try {
                    Encryptor.encrypt(this.digest, Encryptor.loadKey());
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        } else if (x == bts[3]) {
            try {
                Encryptor.decrypt(this.digest, Encryptor.loadKey());
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        CToggleButton x = (CToggleButton) e.getSource();
        if (digest != null) {
            if (x.isSelected()) {
                if (x == ckbts[0]) {
                    ckres.setText(Hash.MD5.checksum(digest));
                } else if (x == ckbts[1]) {
                    ckres.setText(Hash.SHA1.checksum(digest));
                } else if (x == ckbts[2]) {
                    ckres.setText(Hash.SHA256.checksum(digest));
                } else if (x == ckbts[3]) {
                    ckres.setText(Hash.SHA512.checksum(digest));
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a file first");
        }
    }

}