package info.bowkett.wordoccurences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by jbowkett on 14/10/2014.
 */
public class WordCounter {
  private final Set<String> blackList;

  public WordCounter(Set<String> blackList) {
    this.blackList = blackList;
  }

  public List<WordCount> countOccurences(Post post) {
    final String content = post.getContent();
    final List<WordCount> wordCounts = new ArrayList<WordCount>();


    //split on whitespace
    final String[] split = content.split("\\w");

    for (String word : split) {
      if(blackList.contains(word)){
        //ignore it
      }
      else{

      }
    }
    return wordCounts;
  }


  static class WordCount{
    final String word;
    final int count;

    WordCount(String word, int count) {
      this.word = word;
      this.count = count;
    }
  }
}
