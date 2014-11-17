/*

  Scheduler.java

  (C) Samuel Genheden, 2012. 
  All rights reserved.

  Main class that is a window
  that allows the user to enter
  tasks and then ask the program
  to schedule them.

*/
import javax.swing.*;
import java.awt.Cursor;

public class Scheduler extends JFrame {

  /* Elements of the graphical interface */
  private JScrollPane iScrollPanel;
  private JScrollPane oScrollPanel;
  private JTextArea iText;
  private JTextArea oText;
  private JButton okButton;
  private JButton cancelButton;
  
  /* Initialize the user interface */
  private void initComponents() {
  
    iText = new JTextArea();
    oText = new JTextArea();
    okButton = new JButton();
    cancelButton = new JButton();
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Scheduler");
    setResizable(false);
    
    okButton.setText("Schedule");
    okButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
          doScheduling();
        }
    });
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(java.awt.event.ActionEvent evt) {
         System.exit(0);
       }
    });
    
    iText.setColumns(20);
    iText.setRows(40);
    iText.setEditable(true);
    iScrollPanel = new JScrollPane(iText);
    
    oText.setColumns(20);
    oText.setRows(40);
    oText.setEditable(true);
    oScrollPanel = new JScrollPane(oText);
    
    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(iScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(oScrollPanel,javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(okButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(cancelButton)))
          .addContainerGap(27, Short.MAX_VALUE))
    );
 
    
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(iScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(oScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(okButton)
          .addComponent(cancelButton))
        .addContainerGap(21, Short.MAX_VALUE))
    );
    
    pack();
    
  }
  
  /* This happens when the user press the OK-button 
     attempt to schedule the entered tasks */
  private void doScheduling() {
  
    /* The cursor to waiting */
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
  
    /* Read the tasks from the text box and sort them by length */
    TaskList tasks = new TaskList();
    tasks.readFromStrings(iText.getText().split("\n"));
    tasks.sort_by_length();
    
    /* Initialize minimized scheme */
    int minlen = -1;
    Scheme minscheme = new Scheme(tasks);
    
    /* Try to schedule the tasks sorted by length */
    Scheme scheme = new Scheme(tasks);
    if (scheme.schedule(tasks,minlen)) {
      minlen = scheme.length();
      minscheme.copy(scheme);
    }
    /* Try to schedule 10000 randomized list of tasks */
    for (int i=0;i<10000;i++) {
      TaskList tasks2 = tasks.randomize();
      scheme = new Scheme(tasks);
      boolean success = scheme.schedule(tasks2,minlen);
      if (success && ((minlen == -1) || (scheme.length()<minlen))) {
        minlen = scheme.length();
        minscheme.copy(scheme); 
      }
    }
    /* Show the optimized schedule in the textbox */
    String out = "";
    for (int i=0;i<minscheme.size();i++) {
      if (minscheme.point(i) != null) {
          out = out+i+" "+minscheme.point(i).tag()+" "+minscheme.point(i).ordinal()+"\n";
       }
    }
    oText.setText(out);
    
    /* Change back the cursor */
    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    
  }
  /* Create a new app and initialize the interface */
  public Scheduler() {
    initComponents();
  }
  /* Starting point of application */
  public static void main(String args[]) {
    new Scheduler().setVisible(true); 
  }

}