package searchEngine;

import indexer.Indexer;
import indexer.InvertedIndex;
import indexer.Posting;

import java.util.*;

import documentStore.DocumentStore;
import documentStore.Opus;

/**
 * A very limited implementation of SearchEngine.
 * 
 * This layer interacts with both the user interface layer and the store layer
 * (which consists of the DocumentStore and the InvertedIndex). 
 * 
 * Note: This implementation is untested, is not robust, and may be incomplete.
 * 
 * @author  Scrum Master
 */
public class SearchEngine 
{
	private DocumentStore     documentStore;
	private InvertedIndex     invertedIndex;

	/**
	 * Explicit Value Constructor.
	 * 
	 * @param documentStore   The document store to use
	 * @param invertedIndex   The invertedIndex to use
	 */
	public SearchEngine(DocumentStore documentStore, InvertedIndex invertedIndex)
	{
		this.documentStore = documentStore;
		this.invertedIndex = invertedIndex;
	}
	
	/**
	 * Find all of the Document objects in the InvertedIndex that contain the first
	 * term in the given basic query.
	 * 
	 * @param   query The basic query
	 * @return  All of the Document objects (and corresonding Opus objects) containing the first term
	 */
	public List<SearchResult> find(String query)
	{
		String[] indexTerms = Indexer.createIndexTerms(query);
//		System.out.println(indexTerms.length);
//		for(String s : indexTerms)
//		{
//		  System.out.println(s);
//		}
		HashMap<Integer,Posting> postings = invertedIndex.get(indexTerms[0]);
		if (postings == null)
		{
		  return null;
		}
		Set<Integer> keys = postings.keySet();
		Iterator<Integer> j = keys.iterator();
    ArrayList<SearchResult> result = new ArrayList<SearchResult>();

		while (j.hasNext())
		{
		  Integer opusNumber = j.next();
	    Opus opus = documentStore.get(opusNumber);
		  Posting posting = postings.get(opusNumber);
		  Iterator<Integer> i = posting.get();
		  while (i.hasNext())
		  {
	      int documentNumber = i.next().intValue();
	      result.add(new SearchResult(opus, opusNumber, opus.getDocument(documentNumber), documentNumber));
		  }
		}
		return result;
	}
}
