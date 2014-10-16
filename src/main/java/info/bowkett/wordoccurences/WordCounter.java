package info.bowkett.wordoccurences;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by jbowkett on 14/10/2014.
 */
public class WordCounter {
  private final Set<String> blackList = new HashSet<String>();
  private final boolean ignoreCase;

  public WordCounter(Set<String> blackList, boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
    internalise(blackList);
  }

  public static WordCounter caseInsensitive(Set<String> blackList){
    return new WordCounter(blackList, true);
  }

  public static WordCounter caseSensitive(Set<String> blackList){
    return new WordCounter(blackList, false);
  }

  private void internalise(Set<String> blackList) {
    for (String s : blackList) {
      this.blackList.add(homogeniseCaseIfRequired(s));
    }
  }

  private String homogeniseCaseIfRequired(String s) {
    if(ignoreCase){
      return s.toLowerCase();
    }
    return s;
  }

  public Collection<WordCount> countOccurencesWithin(Post post) {
    final ConcurrentMap<String,WordCount> wordCounts = new ConcurrentHashMap<String,WordCount>();
    final String content = post.getContent();

    final String oneOrMoreNonWordCharacters = "\\W+";
    final String[] split = content.split(oneOrMoreNonWordCharacters);

    for (String input : split) {
      final String word = homogeniseCaseIfRequired(input);
      if(isNotBlacklisted(word)) {
        wordCounts.putIfAbsent(word, new WordCount(input, 0));
        wordCounts.get(word).increment();
      }
    }
    return wordCounts.values();
  }

  private boolean isNotBlacklisted(String word) {
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
