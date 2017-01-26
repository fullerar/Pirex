package indexer;

import java.io.Serializable;
import java.util.*;

/**
 * A simple encapsulation of an InvertedIndex in Pirex.
 *
 * Note: From my limited perspective, no truly meaningful data validation
 * seems possible in this class. Any input values are created and validated elsewhere, the
 * InvertedIndex class has to trust its users classes to provide valid inputs. Input corrections
 * would be baseless and "solve" nothing of real value.
 * 
 * @author Scrum Master & Sam Kiwus
 */
public class InvertedIndex implements Serializable
{
  private HashMap<String, HashMap<Integer, Posting>> index;
  private static final long serialVersionUID = 1L;

  /**
   * Default Constuctor.
   */
  public InvertedIndex()
  {
    index = new HashMap<String, HashMap<Integer, Posting>>();
  }

  /**
   * Add an entry to this InvertedIndex.
   * 
   * @param term
   *          The term to add
   * @param opusNumber
   *          The ordinal number of the Opus containing the term
   * @param documentNumber
   *          The ordinal number of the Document containing the term
   */
  public void addEntry(String term, int opusNumber, int documentNumber)
  {
    HashMap<Integer, Posting> postings = index.get(term);
    Posting posting;

    if (postings == null)
    {
      postings = new HashMap<Integer, Posting>();
      posting = new Posting(opusNumber);
      postings.put(opusNumber, posting);
      index.put(term, postings);
    }
    else
    {
      posting = postings.get(opusNumber);
      if (posting == null)
      {
        posting = new Posting(opusNumber);
        postings.put(opusNumber, posting);
      }
      else
      {
        posting.add(documentNumber);
      }
    }
  }

  /**
   * Getter for the Postings hash map in this InvertedIndex object. Useful for data manipulation.
   * 
   * @return the HashMap<String, HashMap<Integer, Posting>> (essentially, the whole index of this
   *         object) contained in this instance of an InvertedIndex.
   */
  public HashMap<String, HashMap<Integer, Posting>> getPostingsHM()
  {
    return index;
  }

  /**
   * Return the Posting for a particular term.
   * 
   * @param term
   *          The term of interest
   * @return The Posting containing the term
   */
  public HashMap<Integer, Posting> get(String term)
  {
    return index.get(term);
  }
}
