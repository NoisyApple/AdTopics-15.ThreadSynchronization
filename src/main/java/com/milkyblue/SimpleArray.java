package com.milkyblue;

import java.awt.Color;
import java.util.Random;

import javax.swing.JLabel;

import com.github.tomaslanger.chalk.Chalk;

// SimpleArray class. Models an array that keeps track of the last updated index. Also updates the GUI when its own array is updated.
public class SimpleArray {
  private final int[] array;
  private int index;
  private final static Random generator = new Random();

  // Class constructor.
  public SimpleArray(int arrLength) {
    array = new int[arrLength];
    index = 0;
  }

  // Synchronized method.
  public synchronized void addSync(int value, JLabel[] lookups, int color) {
    add(value, lookups, color);
  }

  // Non-synchronized method.
  public void addNonSync(int value, JLabel[] lookups, int color) {
    add(value, lookups, color);
  }

  // Functionality method to update the array.
  private void add(int value, JLabel[] lookups, int color) {
    int postition = index;

    // Waits between 0 and 5 seconds before updating the array.
    try {
      Thread.sleep(generator.nextInt(5000));
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Updates both the array and the correspondent GUI label.
    array[postition] = value;
    lookups[postition].setText(Integer.toString(value));

    // Prints the updated data depending on the tracked color.
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

    // Increases the index.
    ++index;
    System.out.println("[" + Chalk.on("NEXT").magenta() + "] " + index);
  }

  // Overrides the toString method. Prints the actual elements in the array.
  public String toString() {
    String arrayString = "\n[" + Chalk.on("SIMPLE ARRAY CONTENT").magenta() + "]\n[";

    for (int i = 0; i < array.length; i++)
      arrayString += (i != array.length - 1) ? array[i] + ", " : array[i];

    arrayString += "]\n";

    return arrayString;
  }
}