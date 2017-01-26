package testing;

import static org.junit.Assert.*;
import indexer.InvertedIndex;

import org.junit.Test;

/**
 * Test class for the InvertedIndex class. Objective: maximize code coverage.
 * 
 * @author Sam Kiwus
 *
 */
public class InvertedIndexTest
{

  /**
   * Test method for InvertedIndex Constructors.
   */
  @Test
  public void testInvertedIndex()
  {
    InvertedIndex invIn = new InvertedIndex();
    assertNotNull(invIn);
    assertNotNull(invIn.getPostingsHM());

    InvertedIndex invIn2 = new InvertedIndex();
    assertNotNull(invIn2);
    assertNotNull(invIn2.getPostingsHM());
    assertNotSame(invIn, invIn2);

    invIn2 = invIn;
    assertSame(invIn, invIn2);
  }

  /**
   * Test method for addEntry(String, int, int). Should be working great because Dr. Bernstein wrote
   * it!
   */
  @Test
  public void testAddEntry()
  {
    InvertedIndex iV = new InvertedIndex();
    assertNull(iV.get("and"));
    iV.addEntry("and", 1, 23);
    assertNotNull(iV.get("and"));
    iV.addEntry("and", 2, 15);
    iV.addEntry("and", 2, 30);
    assertNotNull(iV.get("and"));
  }

  /**
   * Test method for getPostingsHM(). Should return, essentially, the heart of the InvertedIndex.
   */
  @Test
  public void testGetPostingsHM()
  {
    InvertedIndex iV = new InvertedIndex();
    assertNull(iV.get("and"));
    iV.addEntry("and", 1, 23);
    assertNotNull(iV.get("and"));
    iV.addEntry("and", 2, 15);
    iV.addEntry("and", 2, 30);
    assertNotNull(iV.get("and"));
  }

  /**
   * Test method for get(String) method, should return null if the posting is not in the
   * InvertedIndex.
   */
  @Test
  public void testGet()
  {
    InvertedIndex iV = new InvertedIndex();
    assertNull(iV.get("and"));
    iV.addEntry("and", 1, 23);
    assertNotNull(iV.get("and"));
    iV.addEntry("and", 2, 15);
    iV.addEntry("and", 2, 30);
    assertNull(iV.get("or"));
  }

}
