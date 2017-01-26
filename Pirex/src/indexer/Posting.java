package indexer;

import java.io.Serializable;
import java.util.*;

/**
 * An encapsulation of a Posting in Pirex.
 * 
 * Note from Sam Kiwus: In my opinion, no truly meaningful data validation seems possible in this
 * class. Any input values are validated elsewhere, the Posting class has to trust its users to
 * provide valid inputs. Input corrections would be meaningless.
 * 
 * 
 * @author Scrum Master & Sam Kiwus
 */
public class Posting implements Serializable
{
  private int opusNumber;
  private Set<Integer> documentNumbers;

  private static final long serialVersionUID = 1L;

  /**
   * Explicit Value Constructor. Takes in an Opus number and creates a new posting with it.
   * 
   * @param opusNumber
   *          The ordinal number of the Opus
   */
  public Posting(int opusNumber)
  {
    this.opusNumber = opusNumber;
    documentNumbers = new HashSet<Integer>();
  }

  /**
   * Add a Document (actually its ordinal number) to this Posting.
   * 
   * @param documentNumber
   *          The ordinal number of the Document to add
   */
  public void add(int documentNumber)
  {
    documentNumbers.add(new Integer(documentNumber));
  }

  /**
   * Returns the set of integers of the document numbers contained in the Posting object.
   * 
   * @return Set<Integer> of the document numbers contained in the posting
   */
  public Set<Integer> getDocumentNumbers()
  {
    return documentNumbers;
  }

  /**
   * Gets the ordinal number of the Opus.
   * 
   * @return The ordinal number of the Opus
   */
  public int getOpusNumber()
  {
    return this.opusNumber;
  }

  /**
   * Get the ordinal number of the Document objects in this Posting.
   * 
   * @return The ordinal numbers
   */
  public Iterator<Integer> get()
  {
    return documentNumbers.iterator();
  }
}
