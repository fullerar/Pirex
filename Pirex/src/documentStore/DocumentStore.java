package documentStore;

import java.io.Serializable;
import java.util.*;

/**
 * An encapsulation of a DocumentStore (i.e., a Collection of Opus objects) in Pirex.
 * 
 * Note: This implementation is untested, is not robust, and may be incomplete.
 * 
 * @author Sam Kiwus
 */
public class DocumentStore implements Serializable
{
  private List<Opus> store;

  private static final long serialVersionUID = 1L;

  /**
   * Default Constructor.
   */
  public DocumentStore()
  {
    store = new LinkedList<Opus>();
  }

  /**
   * Getter for the current store size (length of the linked list of Opus objects). Returns -1 if
   * the DocumentStore is empty. A size of 0 means the DocumentStore is populated with a single
   * Opus.
   * 
   * @return the current number of Opus objects in the store
   */
  public int getStoreSize()
  {
    return store.size() - 1;
  }

  /**
   * Getter for the store itself.
   * 
   * @return linked list of Opus objects, the "heart" of the store class.
   */
  public List<Opus> getStore()
  {
    return store;
  }

  /**
   * Add an Opus to this DocumentStore. If the Opus object is null, do not add, and return the last
   * size of the store.
   * 
   * @param opus
   *          The Opus to add
   * @return The ordinal number of the Opus
   */
  public int add(Opus opus)
  {
    if (opus != null)
      store.add(opus);
    return store.size() - 1;
  }

  /**
   * Get a particular Opus. If the index to return is out of bounds, returns null.
   * 
   * @param index
   *          The ordinal number of the Opus to get.
   * @return The Opus
   */
  public Opus get(int index)
  {
    if (index >= 0 && index < store.size())
      return store.get(index);
    else
      return null;
  }

  /**
   * Get the ordinal number of an Opus. If the store doesn't contain the requested Opus object, or
   * the requested Opus is null, returns -1.
   * 
   * @param opus
   *          The Opus of interest
   * @return The index of the Opus, -1 if not in this DocumentStore.
   */
  public int indexOf(Opus opus) // Added functionality to do this with a String Opus title...
  {
    if (store.contains(opus) && opus != null)
      return store.indexOf(opus);
    else
      return -1;
  }

  /**
   * Get the ordinal number of an Opus, given its title.
   * 
   * @param title
   *          The title of the Opus of interest
   * @return The ordinal number, -1 if not found
   */
  public int indexOf(String title) // ...here!
  {
    for (Opus o : store)
    {
      if (o.getTitle().equalsIgnoreCase(title))
        return indexOf(o);
    }
    return -1;
  }

  /**
   * Remove an Opus from this DocumentStore. If the Opus is not in the DocumentStore, does nothing.
   * 
   * @param opus
   *          The Opus to remove
   */
  public void remove(Opus opus) // Also added functionality to do this with a String Opus title...
  {
    if (store.contains(opus))
      store.remove(opus);
  }

  /**
   * Remove an Opus from this DocumentStore, given its title. If the Opus is not in the
   * DocumentStore, does nothing.
   * 
   * @param title
   *          The Title of the Opus to remove
   */
  public void remove(String title) // ... here!
  {
    for (Opus o : store)
    {
      if (o.getTitle().equalsIgnoreCase(title))
        remove(o);
    }
  }

  /**
   * Remove an Opus from this DocumentStore, based on it's index. If the index is out of bounds or
   * the store is empty, does nothing.
   * 
   * @param index
   *          The ordinal number of the Opus to remove
   */
  public void remove(int index)
  {
    if (index >= 0 && index < store.size() && !store.isEmpty())
      store.remove(index);
  }
}
