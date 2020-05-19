package com.milkyblue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.tomaslanger.chalk.Chalk;

// ThreadSyncGUI class. Models the GUI of the program.
public class ThreadSyncGUI {
  // Constants to control color between threads.
  public static final int BLUE = 0, YELLOW = 1;

  private JFrame mainFrame;
  private JPanel mainPanel, topPanel, centerPanel, bottomPanel;
  private JCheckBox chkSync;
  private JLabel[][] arrayLookups;
  private JButton btnExecute;

  // Class constructor.
  public ThreadSyncGUI() {
    // Enables colored output on terminal.
    Chalk.setColorEnabled(true);

    mainFrame = new JFrame("Thread Synchronization");
    mainPanel = new JPanel();
    topPanel = new JPanel();
    centerPanel = new JPanel();
    bottomPanel = new JPanel();
    chkSync = new JCheckBox("Thread Synchronization");

    // arrayLookups is initialized as a two dimensional array of labels, one row for
    // array positions and the other row for array actual values.
    arrayLookups = new JLabel[2][6];

    for (int i = 0; i < arrayLookups.length; i++)
      for (int j = 0; j < arrayLookups[i].length; j++) {
        if (i == 0)
          arrayLookups[i][j] = new JLabel(Integer.toString(j), JLabel.CENTER);
        else
          arrayLookups[i][j] = new JLabel("0", JLabel.CENTER);
      }

    btnExecute = new JButton("Execute");

    // Main methods are called.
    addAttributes();
    addListeners();
    build();
    launch();

  }

  // Adds attributes to elements in the program.
  private void addAttributes() {
    mainPanel.setLayout(new BorderLayout());
    centerPanel.setLayout(new GridBagLayout());

    centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

    // Sets attributes to arrayLookups labels based on the correspondent row.
    for (int i = 0; i < arrayLookups.length; i++)
      for (int j = 0; j < arrayLookups[i].length; j++) {
        arrayLookups[i][j].setOpaque(true);

        if (i == 0)
          arrayLookups[i][j].setBackground(Color.decode("#333333"));
        else
          arrayLookups[i][j].setBackground(Color.decode("#888888"));

        arrayLookups[i][j].setForeground(Color.decode("#DDDDDD"));
        arrayLookups[i][j].setPreferredSize(new Dimension(30, 20));
      }

    mainFrame.setResizable(false);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  // Adds listeners to elements in the GUI.
  private void addListeners() {

    // Creates a SimpleArray instance and uses two ArrayWriter instances to fill it.
    btnExecute.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        boolean useSync = chkSync.isSelected();

        // Resets values row background and text.
        for (JLabel lookup : arrayLookups[1]) {
          lookup.setBackground(Color.decode("#888888"));
          lookup.setText("0");
        }

        SimpleArray sharedArray = new SimpleArray(6);
        ArrayWriter writer1 = new ArrayWriter(1, sharedArray, arrayLookups[1], BLUE, useSync);
        ArrayWriter writer2 = new ArrayWriter(11, sharedArray, arrayLookups[1], YELLOW, useSync);

        // Executes both instances of ArrayWriter.
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(writer1);
        executor.execute(writer2);
        executor.shutdown();

        // Waits for executor threads termination.
        try {
          boolean tasksStopped = executor.awaitTermination(1, TimeUnit.MINUTES);
          if (tasksStopped)
            System.out.println(sharedArray);
          else
            System.out
                .println("[" + Chalk.on("FAILURE").red() + "] Timeout expired while awaiting for tasks termination");
        } catch (Exception ex) {
          System.out
              .println("[" + Chalk.on("ERROR").red() + "] Interruption ocurred while awaiting for tasks termination");
        }

      }
    });
  }

  // Builds the GUI.
  private void build() {
    topPanel.add(chkSync);

    GridBagConstraints gbc = new GridBagConstraints();

    // Adds arrayLookups labels.
    for (int i = 0; i < arrayLookups.length; i++)
      for (int j = 0; j < arrayLookups[i].length; j++) {
        gbc.gridy = i;
        gbc.gridx = j;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(1, 2, 1, 2);
        centerPanel.add(arrayLookups[i][j], gbc);
      }

    bottomPanel.add(btnExecute);

    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(centerPanel, BorderLayout.CENTER);
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

    mainFrame.add(mainPanel);
  }

  // Launches mainFrame by setting its visible value to true, then centers and
  // resizes the frame.
  private void launch() {
    mainFrame.setVisible(true);
    mainFrame.pack();
    mainFrame.setLocationRelativeTo(null);
  }
}