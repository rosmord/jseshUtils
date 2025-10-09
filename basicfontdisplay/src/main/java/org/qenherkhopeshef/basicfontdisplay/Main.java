package org.qenherkhopeshef.basicfontdisplay;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleExternalFontDisplayer disp = new SimpleExternalFontDisplayer();
            disp.setVisible(true);
            disp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
