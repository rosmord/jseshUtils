/*
 * JRTFCleaner.java
 *
 * Created on 20 avril 2008, 22:48
 */
package jsesh.utilitysoftwares.rftcleaner;

import java.awt.FileDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Serge Rosmorduc
 */
public class JRTFCleaner extends JFrame {
    
    private javax.swing.JButton doTransformButton;
    private javax.swing.JButton inputFileButton;
    private javax.swing.JTextField inputFileField;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton outputFileButton;
    private javax.swing.JTextField outputFileField;

    /**
     *
     */
    private static final long serialVersionUID = 81471224206980306L;
    String directoryA = ".";
    String fileNameA = "unnamed.rtf";
    String directoryB = ".";
    String fileNameB = "unnamed1.rtf";

    /** Creates new form JRTFCleaner */
    public JRTFCleaner() {
        initComponents();
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        doTransformButton = new javax.swing.JButton();
        inputFileField = new javax.swing.JTextField();
        inputFileButton = new javax.swing.JButton();
        outputFileField = new javax.swing.JTextField();
        outputFileButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Fichier d'origine");

        jLabel2.setText("Fichier de destination");

        doTransformButton.setText("Transformer");
        doTransformButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doTransformButtonActionPerformed(evt);
            }
        });

        inputFileButton.setText("...");
        inputFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputFileButtonActionPerformed(evt);
            }
        });

        outputFileButton.setText("...");
        outputFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputFileButtonActionPerformed(evt);
            }
        });

        jEditorPane1.setBorder(null);
        jEditorPane1.setContentType("text/html");
        jEditorPane1.setEditable(false);
        jEditorPane1.setText("<html>\n  <head>\n\n  </head>\n  <body>\n    <p style=\"margin-top: 0\">\n      Ce logiciel sert à « nettoyer » les fichiers RTF produits par word et contenant des hiéroglyphes.\n    </p>\n    <p>En effet, sur Mac, word modifie les images créées par JSesh, et celles-ci ne sont plus importables dans \t           InDesign.</p>\n     <p>Ce logiciel produit un RTF lisible par InDesign à partir d'un RTF écrit par Word</p>\n  </body>\n</html>\n");
        jEditorPane1.setOpaque(false);
        jScrollPane2.setViewportView(jEditorPane1);

        // layout everything in mig layout
        MigLayout layout = new MigLayout("fillx", "[grow, fill][]", "[][][][grow, fill]");
        getContentPane().setLayout(layout);
        getContentPane().add(jLabel1, "span, split 3");
        getContentPane().add(inputFileField);
        getContentPane().add(inputFileButton, "wrap");
        getContentPane().add(jLabel2, "span, split 3");
        getContentPane().add(outputFileField);
        getContentPane().add(outputFileButton, "wrap");
        getContentPane().add(doTransformButton, "span, split 1, align center");
        getContentPane().add(jScrollPane2, "span, grow, wrap"); 
        
        pack();
    }

    private void inputFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputFileButtonActionPerformed
        if (! "".equals(inputFileField.getText())) {
            File startFile= new File(inputFileField.getText());
            directoryA= startFile.getParent();
            fileNameA= startFile.getName();
        }
        FileDialog fileDialog = new FileDialog(this, "Fichier d'origine");
        fileDialog.setMode(FileDialog.LOAD);
        fileDialog.setDirectory(directoryA);
        fileDialog.setFile(fileNameA);
        fileDialog.setFilenameFilter(new RTFFilter());
        fileDialog.setVisible(true);
        if (fileDialog.getFile() != null) {
            try {
                File f = new File(new File(fileDialog.getDirectory()), fileDialog.getFile());
                String s = f.getCanonicalFile().getCanonicalPath();
                directoryA = fileDialog.getDirectory();
                fileNameA = fileDialog.getFile();
                inputFileField.setText(s);
            } catch (IOException ex) {
                inputFileField.setText("unnamed.rtf");
                throw new RuntimeException(ex);
            }
        }
}
    private void outputFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputFileButtonActionPerformed
         if (! "".equals(outputFileField.getText())) {
            File startFile= new File(outputFileField.getText());
            directoryB= startFile.getParent();
            fileNameB= startFile.getName();
        }
        FileDialog fileDialog = new FileDialog(this, "Fichier d'origine");
        fileDialog.setMode(FileDialog.SAVE);
        fileDialog.setDirectory(directoryB);
        fileDialog.setFile(fileNameB);
        fileDialog.setFilenameFilter(new RTFFilter());
        fileDialog.setVisible(true);
        if (fileDialog.getFile() != null) {
            try {
                File f = new File(new File(fileDialog.getDirectory()), fileDialog.getFile());
                String s = f.getCanonicalFile().getCanonicalPath();
                fileNameB = fileDialog.getFile();
                directoryB = fileDialog.getDirectory();
                outputFileField.setText(s);
            } catch (IOException ex) {
                inputFileField.setText("unnamed1.rtf");
                throw new RuntimeException(ex);
            }
        }
    }


    private void doTransformButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doTransformButtonActionPerformed
        File source = new File(inputFileField.getText());//new File(new File(directoryA),fileNameA);
        File destination = new File(outputFileField.getText());// new File(new File(directoryB), fileNameB);
        if (source.equals(destination)) {
            JOptionPane.showMessageDialog(this, "Les deux fichiers doivent être différents");
        } else {
            FileInputStream in = null;
            try {
                RTFCleaner rTFCleaner = new RTFCleaner();
                in = new FileInputStream(source);
                FileOutputStream out = new FileOutputStream(destination);
                rTFCleaner.cleanRTF(in, out);
                JOptionPane.showMessageDialog(this, "Fichier transformé");
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, ex.getLocalizedMessage());
                ex.printStackTrace();
                throw new RuntimeException(ex);
            } catch (IOException ex1) {
                throw new RuntimeException(ex1);
            } finally {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

   
   }
