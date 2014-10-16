package info.bowkett.wordoccurences;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by jbowkett on 14/10/2014.
 */
public class WordCounter {
  private final Set<String> blackList;

  public WordCounter(Set<String> blackList) {
    this.blackList = blackList;
  }

  public Collection<WordCount> countOccurences(Post post) {
    final String content = post.getContent();
    final ConcurrentMap<String,WordCount> wordCounts = new ConcurrentHashMap<String,WordCount>();

    //split one or more non-word characters
    final String[] split = content.split("\\W+");

    for (String word : split) {
      if(isValidForCounting(word)) {
        wordCounts.putIfAbsent(word, new WordCount(word, 0));
        wordCounts.get(word).increment();
      }
    }
    return wordCounts.values();
  }

  private boolean isValidForCounting(String word) {
    return !blackList.contains(word);
  }

  static class WordCount{
    final String word;
    int count;

    WordCount(String word, int count) {
      this.word = word;
      this.count = count;
    }

    public void increment() {
      count++;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      WordCount wordCount = (WordCount) o;

      return count == wordCount.count &&
             word.equals(wordCount.word);

    }

    @Override
    public int hashCode() {
      int result = word.hashCode();
      result = 31 * result + count;
      return result;
    }
  }
}
