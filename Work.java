/*

  Work.java

  (C) Samuel Genheden, 2012. 
  All rights reserved.

  This is a class to store information on a
  work event. The class simply stores a tag
  of the event, and the ordinal number of that
  tag. This data is set once, when the object
  is created.
*/

public class Work {

  /* Private data */
  private String seriestag;
  private int ord;
  
  /* Always construct a Work object from a tag and an ordinal */
  public Work(String atag, int anord) {
    seriestag = atag;
    ord = anord;  
  }
  
  /* Get routines */
  public int ordinal() {
    return ord;
  }
  public String tag() {
    return seriestag;
  }
}
