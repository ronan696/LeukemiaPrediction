/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import preprocessing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Hp
 */
public class ExtractFeaturesPanel extends javax.swing.JPanel {

    /**
     * Creates new form ExtractFeature
     */
    public ExtractFeaturesPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        createFeatureFile = new javax.swing.JButton();
        folderName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        featureFilePath = new javax.swing.JTextField();
        extractionProgress = new javax.swing.JProgressBar();
        browseFolder = new javax.swing.JButton();
        browseFeatureFile = new javax.swing.JButton();

        createFeatureFile.setText("Create");
        createFeatureFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createFeatureFileActionPerformed(evt);
            }
        });

        jLabel1.setText("Input Directory");

        jLabel2.setText("Output File");

        extractionProgress.setStringPainted(true);

        browseFolder.setText("Browse");
        browseFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFolderActionPerformed(evt);
            }
        });

        browseFeatureFile.setText("Browse");
        browseFeatureFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFeatureFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(folderName)
                            .addComponent(featureFilePath, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(browseFolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(browseFeatureFile, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)))
                    .addComponent(createFeatureFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(extractionProgress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(folderName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(browseFolder))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(featureFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseFeatureFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(extractionProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(createFeatureFile)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void browseFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseFolderActionPerformed
        File defaultPath = null;
        try {
            defaultPath = new File(new File(".").getCanonicalPath() + "\\Dataset");
        } catch (IOException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(defaultPath);
        fc.setDialogTitle("Select a folder");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Folders","directory");
        fc.setFileFilter(filter);
        fc.setAcceptAllFileFilterUsed(false);
        int i = fc.showOpenDialog(this);
        if(i==JFileChooser.APPROVE_OPTION){
            try{
                folderName.setText(fc.getSelectedFile().getPath());
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_browseFolderActionPerformed

    private void browseFeatureFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseFeatureFileActionPerformed
        File defaultPath = null;
        try {
            defaultPath = new File(new File(".").getCanonicalPath());
        } catch (IOException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");   
        fileChooser.setSelectedFile(new File(defaultPath + "\\dataset.txt"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text", "txt", "dat");
        fileChooser.setFileFilter(filter);
        fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            featureFilePath.setText(fileToSave.getAbsolutePath());
        }
    }//GEN-LAST:event_browseFeatureFileActionPerformed

    private void createFeatureFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createFeatureFileActionPerformed
        // TODO add your handling code here:
        SwingWorker sw1 = new SwingWorker<Integer,Integer>() 
        {
            @Override
            protected Integer doInBackground() throws Exception 
            {
                try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(featureFilePath.getText()));
                String folderPath = folderName.getText();
                File dir = new File(folderPath);
                File[] directoryListing = dir.listFiles();
                Integer size = directoryListing.length;
                for (int i=0;i<size;i++) {
                    String fileName = directoryListing[i].getPath();
                    boolean affected = fileName.charAt(fileName.length()-5) == '1';
                    Initialization.src = ImageIO.read(new File(fileName));
                    Initialization.width = Initialization.src.getWidth();
                    Initialization.height = Initialization.src.getHeight();
                    new Grayscale().convert();
                    new HistogramEqualization().performHistEqualization();
                    new ContrastStretching().performcs();
                    new ImageArithmetic().compute();
                    new MedianFilter().performmf();
                    new KMeans().init();
                    new Opening().open();
                    FeatureExtraction fe = new FeatureExtraction();
                    fe.computeFeatures(null,null);
                    fe.computePerimeter(null);
                    String record = fe.getArea() + ","
                        + fe.getPerimeter() + "," + fe.getFormFactor() + "," + fe.getStd() + "," 
                            + fe.getVar() + "," + fe.getEnergy() + "," + fe.getEntropy() + ","
                            + (affected ? "yes" : "no");
                    //System.out.println(fileName);
                    bw.append(record + "\n");
                    publish(100*(i+1)/size);
                  }
                    bw.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }   
                return 100;
            }
 
            @Override
            protected void process(List<Integer> chunks)
            {
                int val = chunks.get(chunks.size()-1);
                extractionProgress.setValue(val);
            }
 
            @Override
            protected void done() 
            {
                extractionProgress.setString("Compteted");
            }
        };
        sw1.execute(); 
    }//GEN-LAST:event_createFeatureFileActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseFeatureFile;
    private javax.swing.JButton browseFolder;
    private javax.swing.JButton createFeatureFile;
    private javax.swing.JProgressBar extractionProgress;
    private javax.swing.JTextField featureFilePath;
    private javax.swing.JTextField folderName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
