package info.bowkett.wordoccurences;

import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jbowkett on 14/10/2014.
 */
public class BlackListLoader {

  private Set<String> blackList = new HashSet<String>();

  public BlackListLoader(File blackList) {
    load(blackList);
  }

  private void load(File file) {

    FileReader fr = null;
    BufferedReader br = null;
    try{
      fr = new FileReader(file);
      br= new BufferedReader(fr);
      String line = null;
      while((line = br.readLine() )!= null){
        final String[] words = line.split(",");
        Collections.addAll(blackList, words);
      }
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    finally{
      try {
        if (br != null) br.close();
        if (fr != null) fr.close();
      }
      catch(IOException ioe){
        //do nothing on close of resources
      }
    }
  }

  public Set<String> getBlackList() {
    return blackList;
  }
}
