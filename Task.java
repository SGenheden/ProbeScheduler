/*

  Task.java

  (C) Samuel Genheden, 2012. 
  All rights reserved.

  Class to hold the information for
  a single task. A task is made of 
  an identity tag and several moments,
  i.e., the points when this task
  should be worked on. This data is
  once, when the object is created.

*/

public class Task {
  /* Private data */
  private String seriestag;
  private int[] moments;
  private int nmoments;
  
  /* Create a task from a single line,
     used when reading from file or strings 
     Line consist of the tag and then the 
     moments, separated by space */
  public Task(String fileline) {
    String[] cols = fileline.trim().split(" ");
    seriestag = cols[0];
    moments = new int[cols.length];
    /* Adds a zeroth moment */
    moments[0] = 0;
    nmoments = cols.length;
    for (int i=1;i<cols.length;i++) {
      moments[i] = Integer.parseInt(cols[i]);
    }
  }
  /* Create a task by copying from another
     task */
  public Task(Task template) {
    seriestag = template.tag();
    nmoments = template.size();
    moments = new int[nmoments];
    for (int i=0;i<size();i++) {
      moments[i] = template.moment(i);
    }
  }
  /* Return the total time to execute this task */
  public int length() {
    int len = 0;
    for (int i=0;i<size();i++){
      len = len + moment(i);
    }
    return len;
  }
  /* Get functions */
  public int moment(int idx) {
    return moments[idx];
  }
  public int size() {
    return nmoments;
  }
  public String tag() {
    return seriestag;
  }
}