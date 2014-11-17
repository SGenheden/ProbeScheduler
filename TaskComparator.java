/*

  TaskComparator.java

  (C) Samuel Genheden, 2012. 
  All rights reserved.

  Comparator class used to sort
  tasks by their length.

*/
import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

  @Override
  public int compare(Task task1, Task task2) {
  
    int len1 = task1.length();
    int len2 = task2.length();
    
    if (len1 > len2) {
      return -1;
    } else if (len1 < len2) {
      return +1;
    } else {
      return 0;
    }
  }

}