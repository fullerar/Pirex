package indexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * A very rudimentary implementation of the Indexer in Pirex. It is only slightly more than
 * a stub.
 * 
 * This is a utility class that is used by several layers.
 * 
 * Note: This implementation is untested, is not robust, and may be incomplete.
 * 
 * @author  Scrum Master
 */
public class Indexer 
{
  /**
   * Create an array of index terms from a line of text (or query string).
   * 
   * Note: This implementation simply splits the line at spaces. It does not handle:
   * punctuation, numbers, stop words, word forms, etc... 
   * 
   * @param  line  The line of text (or query string)
   * @return The array of index terms.
   */
  public static String[] createIndexTerms(String line)
  {
    ArrayList<String> list = new ArrayList<String>();
    //System.out.println(line);
    line = line.replace("--", " ");
    line = line.replace(". ", " ");
    line = line.replace(" ' ", " ");
    
    line = line.replaceAll("[?!@#$%^&*(),]", "");
    String[] strArray = line.split(" ");
    for (int count = 0; count < strArray.length; count++)
    {
      list.add(strArray[count]);
    }
    removeStopWords(list);
    String[] finalArray = new String[list.size()];

    for (int count = 0; count < finalArray.length/* - 1*/; count++)
    {
      finalArray[count] = list.get(count);
    }
//    String lastWord = list.get(finalArray.length - 1);
//    lastWord = list.get(finalArray.length - 1).substring(0, lastWord.length() - 1);
//    finalArray[finalArray.length - 1] = lastWord;
    finalArray = Stemmer.stem(finalArray);
    return finalArray;
//    return line.split(" ");
  }
  
  public static ArrayList<String> removeStopWords(ArrayList<String> str)
  {
    String[] stopWords = new String[]{"a", "an", "and", "are", "but", "did", "do",
        "does", "for", "had", "has", "is", "it", "its", "of", "or", "that", "the",
        "this", "to", "were", "which", "with"};
    int count = 0;
    for (int strSize = 0; strSize < str.size(); strSize++)
    {
      for(int stop = 0; stop < stopWords.length; stop++)
       if (str.get(strSize).equalsIgnoreCase(stopWords[stop]))
       {
          str.set(strSize," ");
          count++;
       }
    }
    for (int num = 0; num < count; num++)
    {
      str.remove(" ");
    }
    return str;
  }
  
  public static void main(String[]args)
  {
    String test = "ALICE’S ADVENTURES IN an and WONDERLAND but hooplah";
    String[] result = createIndexTerms(test);
    for(String s : result)
    {
      System.out.println(s);
    }
    
    
//    String test = "ALICE’S ADVENTURES IN an and WONDERLAND";
//    String[] result = test.split(" ");
//    ArrayList<String> test2 = new ArrayList<String>();
//
//    for(String s : result)
//    {
//      test2.add(s);
//    }
//    test2 = removeStopWords(test2);
//    for(String s : test2)
//    {
//      System.out.println(s);
//    }
    
  }
}
