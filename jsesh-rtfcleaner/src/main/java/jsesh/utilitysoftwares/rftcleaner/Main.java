package jsesh.utilitysoftwares.rftcleaner;

import javax.swing.SwingUtilities;

public class Main {
     /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        SwingUtilities.invokeLater(
            () -> 
                new JRTFCleaner().setVisible(true)            
        );
    }
}
