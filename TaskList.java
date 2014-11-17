/*

  TaskList.java

  (C) Samuel Genheden, 2012. 
  All rights reserved.

  Class to hold a list of tasks.
  Contains functions to read and
  create new lists.

*/
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;
import java.util.Random;

public class TaskList {

  /* Private data */
  private ArrayList<Task> list;
  
  /* Create an empty list */
  public TaskList() {
    list = new ArrayList<Task>();
  }
  /* Create a list by copying from another list */
  public TaskList(TaskList template) {
    list = new ArrayList<Task>();
    for (int i=0;i<template.size();i++) {
      list.add(new Task(template.task(i)));
    }
  }
  /* Read tasks from a text file */
  public void readFromFile(String filename) {
    list.clear();
    try {
      FileInputStream fstream = new FileInputStream(filename);
      DataInputStream in = new DataInputStream(fstream);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String line;
      while ((line = br.readLine()) != null) {
        if (line.length() > 2) {
          list.add(new Task(line));
        }
      }
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }
  /* Read tasks from an array of strings */
  public void readFromStrings(String[] strings) {
    list.clear();
    for (int i=0;i<strings.length;i++) {
      if (strings[i].length() > 2) {
        list.add(new Task(strings[i]));  
      }
    }
  }
  /* Create new list by scrambling the order 
     of the tasks in this list */
  public TaskList randomize() {
    boolean[] taken = new boolean[size()];
    for (int i=0;i<size();i++) {
      taken[i] = false;
    }
    int ntaken = 0;
    TaskList newtasks = new TaskList();
    Random gen = new Random();
    while (ntaken < size()) {
      int idx = gen.nextInt(size());
      if (taken[idx] == false) {
        newtasks.list.add(new Task(task(idx)));
        taken[idx] = true;
        ntaken++;
      }
    }
    return newtasks;
  }
  /* Sort the tasks by their length */
  public void sort_by_length() {
    TaskComparator comparator = new TaskComparator();
    Collections.sort(list,comparator);
  }
  /* Get functions */
  public int size() {
    return list.size();
  }
  public Task task(int idx) {
    return list.get(idx);
  }
}