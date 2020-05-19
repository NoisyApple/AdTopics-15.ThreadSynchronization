package com.milkyblue;

import java.awt.Color;
import java.util.Random;

import javax.swing.JLabel;

import com.github.tomaslanger.chalk.Chalk;

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
    if (color == ThreadSyncGUI.BLUE) {
      lookups[postition].setBackground(Color.decode("#1D70A2"));
      System.out.println("[" + Chalk.on(Thread.currentThread().getName().toUpperCase()).cyan() + "] wrote "
          + Chalk.on(Integer.toString(value)).green() + " in the element number "
          + Chalk.on(Integer.toString(postition)).green());
    } else {
      lookups[postition].setBackground(Color.decode("#F7B626"));
      System.out.println("[" + Chalk.on(Thread.currentThread().getName().toUpperCase()).yellow() + "] wrote "
          + Chalk.on(Integer.toString(value)).green() + " in the element number "
          + Chalk.on(Integer.toString(postition)).green());
    }

    ++index;
    System.out.println("[" + Chalk.on("NEXT").magenta() + "] " + index);
  }

  public String toString() {
    String arrayString = "\n[" + Chalk.on("SIMPLE ARRAY CONTENT").magenta() + "]\n[";

    for (int i = 0; i < array.length; i++)
      arrayString += (i != array.length - 1) ? array[i] + ", " : array[i];

    arrayString += "]\n";

    return arrayString;
  }
}