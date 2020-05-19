# [Advanced Programming Topics] #15 Thread Synchronization

This is the **15th** project part of my Advanced Programming Topics course at Leon Institute of Thechnology :mortar_board::lion:.

## :dart:GOAL

Show the main funtionality of **thread synchronization** in java by the use of the `synchronized` reserved word.

<p align="center">
<img src="./snapshots/Annotation 2020-05-19 045523.png"/>
</p>

## :eyeglasses:BRIEF EXPLANATION

This is shown through a GUI program that creates a custom array based class (`SimpleArray`) with a fixed size of 6 elements, then a second custom utility class (`ArrayWriter`) is used to fill the array but using **two instances** of it.

So, each instance of `ArrayWriter` will fill half of the `SimpleArray` (3 elements), The first one will write **1**, **2** and **3**, and the second one **11**, **12** and **13**.

These two instances write into the `SimpleArray` in a separated threads using whether a `synchronized` or a non `synchronized` method to call the `SimpleArray` `add` method, based on the **checkbox** state.

```java
public class SimpleArray{}
  /*
  ** SimpleArray Code.
  */

  // Synchronized method.
  public synchronized void addSync(int value, JLabel[] lookups, int color) {
    add(value, lookups, color);
  }

  // Non-synchronized method.
  public void addNonSync(int value, JLabel[] lookups, int color) {
    add(value, lookups, color);
  }

  // Functionality method.
  private void add(int value, JLabel[] lookups, int color) {
    /*
    ** add method code.
    */
  }
```

### Non Synchronized: :no_entry_sign::arrows_counterclockwise:

When we execute both `ArrayWriter` instances when **Thread Synchronization** is unchecked we get the following output:

<p align="center">
<img src="./snapshots/Annotation 2020-05-19 050023.png"/>
</p>

As we can see, both threads are printed in an specific color, **cyan** for **thread-1** and **yellow** for **thread-2** and each of them writes their values on each element in the `SimpleArray`, but here is the problem.
At the start both threads wrote on the element 0 of the `SimpleArray`, and therefore another element will be excluded, in this case the element number **4**, leaving it with no changes.
We can also see this in a much better way on the GUI:

<p align="center">
<img src="./snapshots/Annotation 2020-05-19 045958.png"/>
</p>

### Synchronized: :arrows_counterclockwise:

Then, to solve this problem we should use the **Synchronized** way.
So, now when we execute both threads with **Thread Synchronization** checked we ge get the following output:

<p align="center">
<img src="./snapshots/Annotation 2020-05-19 050130.png"/>
</p>

Now, each thread is executed in a better way, we can see that **thread-1** runs first, and then until it finishes its tasks **thread-2** starts, leaving our `SimpleArray` instance completely filled:

<p align="center">
<img src="./snapshots/Annotation 2020-05-19 050100.png"/>
</p>

So, we can use synchronization to prevent erroneous results in our programs when we use muliple threads at the same time to access the same resources.
