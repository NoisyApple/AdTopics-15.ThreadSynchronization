package com.milkyblue;

import javax.swing.JLabel;

// ArrayWriter class. fills a SimpleArray instance.
public class ArrayWriter implements Runnable {

  private final SimpleArray sharedArray;
  private final int initValue;
  private JLabel[] lookups;
  private int color;
  private boolean useSync;

  // Class constructor. Keeps track of the lookup labels from the GUI, also the
  // selected color and if a Synchronized method will be used or not.
  public ArrayWriter(int value, SimpleArray array, JLabel[] lookups, int color, boolean useSync) {
    initValue = value;
    sharedArray = array;
    this.lookups = lookups;
    this.color = color;
    this.useSync = useSync;
  }

  // Method executed when thread runs. Calls wether a synchronized or
  // non-synchronized method to add a value to the SimpleArray element depending
  // on the constructor passed arguments.
  public void run() {
    for (int i = initValue; i < initValue + 3; i++)
      if (useSync)
        sharedArray.addSync(i, lookups, color);
      else
        sharedArray.addNonSync(i, lookups, color);

  }

}