package documentStore;

import java.io.Serializable;
import java.util.*;
/**
 * An encapsulation of a Document (i.e., a paragraph) in Pirex.
 * 
 * Note: This implementation is untested, is not robust, and may be incomplete.
 * 
 * @author Scrum Master
 */
public class Document implements Serializable
{

  private List<String>    lines;
  
  private static final long   serialVersionUID = 1L;
  private static final String BLANK = "";
  
  /**
   * Default Constructor.
   */
  public Document()
  {
    lines = new ArrayList<String>();
  }
  
  /**
   * Add a line (of text) to this Document.
   * 
   * @param line   The line to add
   */
  public void addLine(String line)
  {
    if(line != null)
    lines.add(line);
  }
  
  /**
   * Returns the lines, List, of the document.
   * @return the ArrayList<String> of lines in the document
   */
  public List<String> getLines()
  {
    return lines;
  }
  
  /**
   * Get the short form of this Document.
   * 
   * @return The short form of this Document.
   */
  public String getShortForm()
  {
    if (lines.size() > 0) return lines.get(0);
    else                  return BLANK;
  }
  
  /**
   * Return a String representation of this Document.
   * 
   * @return  The String representation.
   */
  public String toString()
  {
    String result = "";
    Iterator<String>  i = lines.iterator();
    while (i.hasNext()) result += i.next() + "\n";
    
    return result;
  }
}
