package com.milkyblue;

import javax.swing.JLabel;

public class ArrayWriter implements Runnable {

  private final SimpleArray sharedArray;
  private final int initValue;
  private JLabel[] lookups;
  private int color;
  private boolean useSync;

  public ArrayWriter(int value, SimpleArray array, JLabel[] lookups, int color, boolean useSync) {
    initValue = value;
    sharedArray = array;
    this.lookups = lookups;
    this.color = color;
    this.useSync = useSync;
  }

  public void run() {
    for (int i = initValue; i < initValue + 3; i++)
      if (useSync)
        sharedArray.addSync(i, lookups, color);
      else
        sharedArray.add(i, lookups, color);

  }

}