package testing;

import static org.junit.Assert.*;
import indexer.Posting;

import java.util.HashSet;

import org.junit.Test;

/**
 * JUnit test class for the Posting class. Objective: maximize coverage.
 * 
 * @author Sam Kiwus
 *
 */
public class PostingTest
{

  /**
   * Test method for Posting constructors.
   */
  @Test
  public void testPosting()
  {
    Posting post = new Posting(0);
    assertNotNull(post);
    assertNotNull(post.getDocumentNumbers());

    Posting post2 = new Posting(1);
    assertNotNull(post2);
    assertNotNull(post2.getDocumentNumbers());
    assertNotSame(post, post2);

    post2 = post;
    assertSame(post, post2);
  }

  /**
   * Test method for the add(document number) method.
   */
  @Test
  public void testAdd()
  {
    Posting post = new Posting(0);
    post.add(0);
    post.add(1);
    post.add(2);
    HashSet<Integer> set = new HashSet<Integer>(post.getDocumentNumbers());
    assertEquals(3, set.size());
    post.add(6);
    set = new HashSet<Integer>(post.getDocumentNumbers());
    assertEquals(4, set.size());
  }

  /**
   * Test method for getDocumentNumbers(). Should be returning a Set<Integer> of all the documents
   * in the Posting.
   */
  @Test
  public void testGetDocumentNumbers()
  {
    Posting post = new Posting(0);
    post.add(0);
    post.add(1);
    post.add(2);
    post.add(3);
    post.add(4);
    assertNotNull(post.getDocumentNumbers());
    HashSet<Integer> set = new HashSet<Integer>(post.getDocumentNumbers());
    assertEquals(5, set.size());
  }

  /**
   * Test method for getOpusNumber(). Should simply be returning the Opus number of the Posting.
   */
  @Test
  public void testGetOpusNumber()
  {
    Posting post = new Posting(777);
    assertEquals(777, post.getOpusNumber());
  }

  /**
   * Test method for get(). Should be returning an iterator for the Postings.
   */
  @Test
  public void testGet()
  {
    Posting post = new Posting(777);
    assertNotNull(post.get());
  }

}
