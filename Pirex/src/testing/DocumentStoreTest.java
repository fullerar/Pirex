package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import documentStore.Document;
import documentStore.DocumentStore;
import documentStore.Opus;

/**
 * JUnit test class for the DocumentStore class. Objective: 100% code coverage.
 * 
 * @author Sam Kiwus
 *
 */
public class DocumentStoreTest
{

  /**
   * Test method for constructor of the DocumentStore object.
   */
  @Test
  public void testDocumentStore()
  {
    DocumentStore docStor = new DocumentStore();
    assertNotNull(docStor);
    assertNotNull(docStor.getStore());

    DocumentStore docStor2 = new DocumentStore();
    assertNotNull(docStor2);
    assertNotNull(docStor2.getStore());
    assertNotSame(docStor, docStor2);

    docStor2 = docStor;
    assertSame(docStor, docStor2);
  }

  /**
   * Test method for the getStoreSize() method.
   */
  @Test
  public void testAddGetStoreSize()
  {
    DocumentStore docStor = new DocumentStore();
    assertEquals(-1, docStor.getStoreSize()); // Should be empty, returning -1.
    Opus opus = new Opus("", "");
    assertEquals(0, docStor.add(opus)); // Size returned should be 0.
    assertEquals(0, docStor.getStoreSize()); // Should have a single Opus.

    assertEquals(0, docStor.add(null)); // Should ignore null additions.
    assertEquals(0, docStor.getStoreSize()); // Should still be 0 (only one Opus).

  }

  /**
   * Test method for the getStore(). Should be returning a pointer to the DocumentStore list.
   */
  @Test
  public void testGetStore()
  {
    DocumentStore docStor = new DocumentStore();
    assertNotNull(docStor.getStore());

    Opus opus = new Opus("", "");
    docStor.add(opus);

    List<Opus> testList = new ArrayList<Opus>();
    testList = docStor.getStore();

    assertEquals(testList, docStor.getStore());
    assertEquals(1, testList.size()); // List should have one element (not 0, since the x-1 is a
                                      // function of the DocumentStore methods)
  }

  /**
   * Test method for the get() method of the DocumentStore. Should return an Opus at the index
   * location, and returning null if the index is out of bounds.
   */
  @Test
  public void testGet()
  {
    DocumentStore docStor = new DocumentStore();
    Opus opus = new Opus("", "");
    docStor.add(opus);

    Opus testOpus = new Opus("Test", "ting123");
    assertNull(docStor.get(-1));
    assertNull(docStor.get(1));

    assertEquals(opus, docStor.get(0));
    docStor.add(testOpus);

    assertEquals(testOpus, docStor.get(1));
    assertNotEquals(docStor.get(1), docStor.get(0));
  }

  /**
   * Test method for indexOf(Opus) method, which returns the index of the requested Opus.
   */
  @Test
  public void testIndexOfOpus()
  {
    DocumentStore docStor = new DocumentStore();
    Opus opus = new Opus("Test", "ting123");
    docStor.add(new Opus("", ""));
    docStor.add(new Opus("", ""));
    docStor.add(opus);
    docStor.add(new Opus("", ""));
    assertEquals(2, docStor.indexOf(opus)); // Should be the third Opus in the DocumentStore.
    assertEquals(2, docStor.indexOf("Test")); // Should find the Opus with this title.
    assertEquals(opus, docStor.get(docStor.indexOf("Test"))); // And they should be the same Opus.
    assertEquals(-1, docStor.indexOf((Opus) null)); // Pass null, casted as Opus, should return -1
                                                    // (Opus not in this DocumentStore).

    assertEquals(-1, docStor.indexOf(new Opus("", ""))); // Should return -1 because this is a new
                                                         // Opus, not in this DocumentStore.
    assertEquals(-1, docStor.indexOf("Fake name")); // Should return -1 because this Opus tital is
                                                    // not in this DocumentStore.
  }

  /**
   * Test method for remove(Opus) method. Should do nothing if the Opus is not in the Store.
   */
  @Test
  public void testRemoveOpus()
  {
    DocumentStore docStor = new DocumentStore();
    Opus opus = new Opus("Test", "ting123");
    docStor.add(opus);
    docStor.remove((Opus) null);
    assertEquals(0, docStor.getStoreSize());
    docStor.remove(opus);
    assertEquals(-1, docStor.getStoreSize());
  }

  /**
   * Test method for remove(String "Opus Title") method. Should do nothing if Opus is not in the
   * Store.
   */
  @Test
  public void testRemoveString()
  {
    DocumentStore docStor = new DocumentStore();
    Opus opus = new Opus("Test", "ting123");
    docStor.add(opus);
    docStor.remove("");
    assertEquals(0, docStor.getStoreSize());
    docStor.remove("Test");
    assertEquals(-1, docStor.getStoreSize());
  }

  /**
   * Test method for remove(index) method. Should do nothing if the index is out of bounds or the
   * Store is empty.
   */
  @Test
  public void testRemoveInt()
  {
    DocumentStore docStor = new DocumentStore();
    Opus opus = new Opus("Test", "ting123");
    docStor.add(opus);
    docStor.remove(-1);
    assertEquals(0, docStor.getStoreSize());
    docStor.remove(0);
    assertEquals(-1, docStor.getStoreSize());
    docStor.remove(0); // The following commands should do nothing.
    docStor.remove(3);
    docStor.remove(-2);
    assertEquals(-1, docStor.getStoreSize()); // DocumentStore should still be the same size.
  }

}
