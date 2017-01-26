package searchEngine;

import documentStore.Document;
import documentStore.Opus;

/**
 * An encapsulation of result of a search.
 * 
 * Note: This implementation is untested, is not robust, and may be incomplete.
 * 
 * @author  Scrum Master
 */
public class SearchResult 
{
	private Document  document;
  private int       documentNumber, opusNumber;
	private Opus      opus;
	
	/**
	 * Explicit Value Constructor.
	 * 
	 * @param opus           The Opus
	 * @param opusNumber     The ordinal number of the Opus
	 * @param document       The Document
	 * @param documentNumber The ordinal number of the Document
	 */
	public SearchResult(Opus opus, int opusNumber, Document document, int documentNumber)
	{
		this.opus     = opus;
		this.opusNumber = opusNumber;
		this.document = document;
		this.documentNumber = documentNumber;
	}
	
	/**
	 * Get the Document.
	 * 
	 * @return  The Document
	 */
	public Document getDocument()
	{
		return document;
	}
	
	/**
	 * Get the ordinal number of the Document.
	 * 
	 * @return The ordinal number of the Document
	 */
	public int getDocumentNumber()
	{
		return documentNumber;
	}
	
	/**
	 * Get the Opus.
	 * 
	 * @return  The Opus
	 */
	public Opus getOpus()
	{
		return opus;
	}
	
	/**
	 * Get the ordinal number of the Opus.
	 * 
	 * @return The ordinal number of the Opus
	 */
	public int getOpusNumber()
	{
		return opusNumber;
	}
}
