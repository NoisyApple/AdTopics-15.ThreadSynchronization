package com.milkyblue;

import java.awt.Color;
import java.util.Random;

import javax.swing.JLabel;

public class SimpleArray {
  private final int[] array;
  private int index;
  private final static Random generator = new Random();

  public SimpleArray(int arrLength) {
    array = new int[arrLength];
    index = 0;
  }

  public synchronized void addSync(int value, JLabel[] lookups, int color) {
    updateArray(value, lookups, color);
  }

  public void add(int value, JLabel[] lookups, int color) {
    updateArray(value, lookups, color);
  }

  private void updateArray(int value, JLabel[] lookups, int color) {
    int postition = index;

    try {
      Thread.sleep(generator.nextInt(5000));
    } catch (Exception e) {
      e.printStackTrace();
    }

    array[postition] = value;
    lookups[postition].setText(Integer.toString(value));
    if (color == ThreadSyncGUI.BLUE)
      lookups[postition].setBackground(Color.decode("#1D70A2"));
    else
      lookups[postition].setBackground(Color.decode("#F7B626"));

    System.out.println(Thread.currentThread().getName() + " wrote " + value + " in the element number " + postition);

    ++index;
    System.out.println("Next index: " + index);
  }

  public String toString() {
    String arrayString = "\nSimple Array Content:\n";

    for (int i = 0; i < array.length; i++) {
      arrayString += array[i] + " ";
    }

    return arrayString;
  }
}