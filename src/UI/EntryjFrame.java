/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

/**            EntryjFrame
 *--------------------------------------
 * @author lirancaduri
 * 
 * class EntryjFrame - Represent the entry window.
 *--------------------------------------
 */
public class EntryjFrame extends javax.swing.JFrame {

    private MainJFrame mainJFrame; // Game panel
  
    /**
     * Creates new form EntryjFrame
     */
    
    /*--------------------------------------------------------------------------
    General : The class constractor.
    
    Output : instance of the class.
    --------------------------------------------------------------------------*/
    public EntryjFrame() {
        initComponents();
        
        this.setTitle("Chinese Checkers");
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpEntry = new javax.swing.JPanel();
        instructionJl = new javax.swing.JLabel();
        jlbImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        instructionJl.setFont(new java.awt.Font("American Typewriter", 1, 24)); // NOI18N
        instructionJl.setForeground(new java.awt.Color(153, 102, 255));
        instructionJl.setText("Press Any Key TO Continue!");

        jlbImage.setIcon(new javax.swing.ImageIcon("/Users/lirancaduri/NetBeansProjects/JavaApplication2/Resources/resizeBoard.png")); // NOI18N

        javax.swing.GroupLayout jpEntryLayout = new javax.swing.GroupLayout(jpEntry);
        jpEntry.setLayout(jpEntryLayout);
        jpEntryLayout.setHorizontalGroup(
            jpEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEntryLayout.createSequentialGroup()
                .addComponent(jlbImage)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jpEntryLayout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(instructionJl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpEntryLayout.setVerticalGroup(
            jpEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpEntryLayout.createSequentialGroup()
                .addComponent(jlbImage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(instructionJl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpEntry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**-------------------------------------------------------------------------
    FuncName: formKeyPressed
    
    General : the function gets click from the keyboard .
    
    Input : evt - keyboard clicked event .
    
    Output : Show the game panel .
    
    Run Time : O(1)
    --------------------------------------------------------------------------*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // press any keyboard key to continue to the game panel
        mainJFrame.setVisible(true);// show game panel 
        this.setVisible(false);// hide entry window
    }//GEN-LAST:event_formKeyPressed

    /**-------------------------------------------------------------------------
    FuncName: formMouseClicked
    
    General : the function gets click from the keyboard .
    
    Input : evt - mouse clicked event .
    
    Output : Show the game panel .
    
    Run Time : O(1)
    --------------------------------------------------------------------------*/
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // press any mouse button to continue to the game panel
        mainJFrame.setVisible(true); // show game panel 
        this.setVisible(false); // hide entry window
    }//GEN-LAST:event_formMouseClicked

    public MainJFrame getMainJFrame() { //return the game panel
        return mainJFrame;
    }

    public void setMainJFrame(MainJFrame mainJFrame) { //set a new game panel
        this.mainJFrame = mainJFrame;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel instructionJl;
    private javax.swing.JLabel jlbImage;
    private javax.swing.JPanel jpEntry;
    // End of variables declaration//GEN-END:variables
}
