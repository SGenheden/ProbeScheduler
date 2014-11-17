/*

  Scheme.java

  (C) Samuel Genheden, 2012. 
  All rights reserved.

  Class to hold a work scheme of 
  tasks. Contains routine to populate
  the scheme.

*/
import java.util.ArrayList;

public class Scheme {

  /* Private data */
  private ArrayList<Work> list;
  private int len;
  
  /* Try to distribute a single task, given a starting time */
  private boolean distribute(Task task,int start) {
    /* Place the zeroth moment */
    int pidx = start;
    list.set(pidx,new Work(task.tag(),1));
    /* Update the length of the scheme */
    if (pidx > len) len = pidx;
    /* This will be the ordinal of the work event */
    int ord = 1;
    /* Loop over the other moments */
    for (int i=1;i<task.size();i++) {
      ord++;
      /* Calculate the time for the work event */
      pidx = pidx + task.moment(i);
      if (pidx+1 < size()) {
        /* Check that there is space to put the event */
        if ((point(pidx) == null) && (point(pidx-1) == null) && (point(pidx+1)==null)) {
          /* Place the moment and update the length if necessary */
          list.set(pidx,new Work(task.tag(),ord));
          if (pidx > len) len = pidx;
        } else {
          return false;
        }
      } else if (pidx < size()) {
        /* Check that their is space to put the event */
        if ((point(pidx) == null) && (point(pidx-1) == null)) {
          /* Place the moment and update the length if necessary */
          list.set(pidx,new Work(task.tag(),ord));
          if (pidx > len) len = pidx;
        } else {
          return false;
        }      
      } else {
        return false;
      }
    }
    /* Only return true if it was possible to place the entire task */
    return true;
  }
  
  /* Try to place out a task on the scheme */
  private boolean place_task(Task task) {
    int start = 0;
    boolean success = false;
    /* Keep a copy of the old scheme so this can be overridden */
    Scheme oldscheme = new Scheme(this);
    /* Scan for a starting point */
    while ((!success) && (start < size()-2)) {
      boolean okstart = false;
      /* Check if their is space to start a task at this point */
      if (start == 0) {
        okstart = (point(start) == null);
      } else {
        okstart = ((point(start) == null) && (point(start-1) == null) && (point(start+1) == null));
      }
      if (okstart) {
        /* Copy from the old scheme, i.e. overwrite possible old failures */
        copy(oldscheme);
        /* Distribute the task */
        success = distribute(task,start);
      } else {
        success = false;
      }
      start = start + 1;
    }
    /* Either the end of the scheme was reached and it was not possible to place the task,
       or it was possible to place the task somewhere */
    return success;
  }
  
  /* Create a default scheme by placing all the tasks after another 
     but do not populate it. Used to obtain a maximum length scheme */
  public Scheme(TaskList tasks) {
    list = new ArrayList<Work>();
    for (int i=0;i<tasks.size();i++) {
      /* Add space between previous and next event */
      if (i>0) {
        list.add(null);
      } 
      /* Add space for the zeroth moment */
      list.add(null);
      /* Add space for all other moments */
      for (int j=1;j<tasks.task(i).size();j++) {
        for (int k=0;k<tasks.task(i).moment(j);k++) {
          list.add(null);
        }
      }
    }
    len = 0;
  }
  /* Create a new scheme by copying from another scheme */
  public Scheme(Scheme template) {
    list = new ArrayList<Work>();
    copy(template);
  }
  /* Copy the state of the scheme from another scheme */
  public void copy(Scheme template) {
    list.clear();
    for (int i=0;i<template.size();i++) {
      if (template.point(i) == null) {
        list.add(null);
      } else {  
        list.add(new Work(template.point(i).tag(),template.point(i).ordinal())); 
      }
    }
    len = template.length();
  }
  /* The total length of the scheme, i.e., the time of the last work event */
  public int length() {
    /*int len = 0;
    for (int i=0;i<size();i++) {
      if (point(i) != null) {
        len = i;
      }
    }
    return len;*/
    return len;
  }
  /* Try to schedule a list of tasks 
     by placing them in sequential order. 
     Stop if length is greater than cutoff */
  public boolean schedule(TaskList tasks,int cutoff) {
    for (int i=0;i<tasks.size();i++) {
      if (!place_task(tasks.task(i))) {
        return false;
      } else {
        /* Check if the length is short than cutoff */
        if ((cutoff > -1) && (length() > cutoff)) {
          return false;
        }
      }
    }
    /* Return true only if it was possible to place all the tasks */
    return true;
  }
  /* Get functions */
  public Work point(int idx) {
    return list.get(idx);
  }
  public int size() {
    return list.size();
  }
}