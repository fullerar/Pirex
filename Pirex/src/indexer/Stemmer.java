package indexer;

public class Stemmer
{
  //List of common prefixes
  public static String[] onePrefix = {"a"};
  public static String[] twoPrefix = {"ab", "de", "en", "em", "in", "im", "re", "un"};
  public static String[] threePrefix = {"dis", "mid", "mis", "non", "pre", "sub"};
  public static String[] fourPrefix = {"anti", "fore", "over", "semi"};
  public static String[] fivePrefix = {"inter", "super", "under"};
  
  //List of common suffixes
  public static String[] oneSuffix = {"s", "y"};
  public static String[] twoSuffix = {"al", "ed", "en", "er", "ic", "ty", "ly", "es"};
  public static String[] threeSuffix = {"ial", "est", "ful", "ing", "ion", "ity", "ive", "ous"};
  public static String[] fourSuffix = {"able", "ible", "tion", "less", "ment", "ness", "eous",
  "ious"};
  public static String[] fiveSuffix = {"ation", "ition", "ative", "itive"};

  public static String[] stem(String[] str)
  {
	  str = removePrefixes(str);
	  str = removeSuffixes(str);
	  
	return str;
  }

  public static String [] removePrefixes(String[] str)
  {
    int lengthOfStr = str.length;
    for (int count = 0; count < lengthOfStr - 1; count++)
    { 
     // if(!Character.isUpperCase(str[count].charAt(0)))
      //{
        if(str[count].length() >= 3)
  		  {
  			  if(str[count].startsWith(onePrefix[0]))
  			  {
  			    if (!Character.isUpperCase(str[count].charAt(0)))
            {
  				    str[count] = str[count].substring(1);
            }
  			  }
  		  }
  		  if(str[count].length() >= 4)
  		  {
  			  for(int i = 0; i < twoPrefix.length; i++)
  			  {
  				  if(str[count].startsWith(twoPrefix[i]))
  				  {
  				    if (!Character.isUpperCase(str[count].charAt(0)))
              {
  				      str[count] = str[count].substring(2);
              }
  				  }
  			  }
  		  }
  		  if(str[count].length() >= 5)
  		  {
  			  for(int i = 0; i < threePrefix.length; i++)
  			  {
  				  if(str[count].startsWith(threePrefix[i]))
  				  {
  				    if (!Character.isUpperCase(str[count].charAt(0)))
              {
  				      str[count] = str[count].substring(3);
              }
  				  }
  			  }
  		  }
  		  if(str[count].length() >= 6)
  		  {
  			  for(int i = 0; i < fourPrefix.length; i++)
  			  {
  				  if(str[count].startsWith(fourPrefix[i]))
  				  {
  				    if (!Character.isUpperCase(str[count].charAt(0)))
              {
  				      str[count] = str[count].substring(4);
              }
  				  }
  			  }
  		  }
  		  if(str[count].length() >= 7)
  		  {
  			  for(int i = 0; i < fivePrefix.length; i++)
  			  {
  				  if(str[count].startsWith(fivePrefix[i]))
  				  {
  				    if (!Character.isUpperCase(str[count].charAt(0)))
              {
  				      str[count] = str[count].substring(5);
              }
  				  }
  			  }
  		  }
      }
    //}
	  return str;
  }
  
  
  
  public static String[] removeSuffixes(String[] str)
  {
	  int lengthOfStr = str.length;
	  for (int count = 0; count < lengthOfStr; count++)
	  {
  	    if(str[count].length() >= 3)
  		  {
  			  if(str[count].endsWith(oneSuffix[0]))
  			  {
  			    if (!Character.isUpperCase(str[count].charAt(0)))
  			    {
  			      str[count] = str[count].substring(0, str[count].length() - 1);
  			    }
  			  }
  		  }
  		  if(str[count].length() >= 4)
  		  {
  			  for(int i = 0; i < twoSuffix.length; i++)
  			  {
  				  if(str[count].endsWith(twoSuffix[i]))
  				  {
              if (!Character.isUpperCase(str[count].charAt(0)))
              {
                str[count] = str[count].substring(0, str[count].length() - 2);
              }
  				  }
  			  }
  		  }
        if(str[count].length() >= 5)
        {
          for(int i = 0; i < threeSuffix.length; i++)
          {
            if(str[count].endsWith(threeSuffix[i]))
            {
              if (!Character.isUpperCase(str[count].charAt(0)))
              {
                str[count] = str[count].substring(0, str[count].length() - 3);
              }
            }
          }
        }
        if(str[count].length() >= 6)
        {
          for(int i = 0; i < fourSuffix.length; i++)
          {
            if(str[count].endsWith(fourSuffix[i]))
            {
              if (!Character.isUpperCase(str[count].charAt(0)))
              {
                str[count] = str[count].substring(0, str[count].length() - 4);
              }
            }
          }
        }
        if(str[count].length() >= 7)
        {
          for(int i = 0; i < fiveSuffix.length; i++)
          {
            if(str[count].endsWith(fiveSuffix[i]))
            {
              if (!Character.isUpperCase(str[count].charAt(0)))
              {
                str[count] = str[count].substring(0, str[count].length() - 5);
              }
            }
          }
        }
	    }
	  return str;
  }
  
}
