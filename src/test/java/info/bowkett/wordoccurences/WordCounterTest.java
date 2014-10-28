package info.bowkett.wordoccurences;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


/**
 * WordCounter Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Oct 16, 2014</pre>
 */
@RunWith(MockitoJUnitRunner.class)
public class WordCounterTest {

  @Mock Post post;

  @Test
  public void testCaseSensitiveOnlyOneWordInPostWithNoBlackList() throws Exception {
    final Collection<WordCounter.WordCount> wordCounts = getCaseSensitiveWordCounts("Test");
    assertEquals(1, wordCounts.size());
  }

  @Test
  public void testCaseInsensitiveOnlyOneWordInPostWithNoBlackList() throws Exception {
    final Collection<WordCounter.WordCount> wordCounts = getCaseInsensitiveWordCounts("Test");
    assertEquals(1, wordCounts.size());
  }

  @Test
  public void testCaseSensitiveWhenOnlyOneWordMultipleTimesInPostWithNoBlackList() throws Exception {
    final Collection<WordCounter.WordCount> wordCounts = getCaseSensitiveWordCounts("Test Test");
    assertEquals(1, wordCounts.size());
    assertTrue(wordCounts.contains(new WordCounter.WordCount("Test", 2)));
  }

  @Test
  public void testCaseInsensitiveCountOccurencesWithNoBlackList() throws Exception {
    final Collection<WordCounter.WordCount> wordCounts = getCaseInsensitiveWordCounts("Test TeST");
    assertEquals(1, wordCounts.size());
    assertTrue(wordCounts.contains(new WordCounter.WordCount("Test", 2)));
  }

  @Test
  public void testCaseInsensitiveAppliesToBlackListEntriesAlso() throws Exception {
    final Collection<WordCounter.WordCount> wordCounts = getCaseInsensitiveWordCounts("Test TeST BLACKLISTED", "blacklisted");
    assertEquals(1, wordCounts.size());
    assertTrue(wordCounts.contains(new WordCounter.WordCount("Test", 2)));
  }

  @Test
  public void testCaseSensitiveWithNoBlackList() throws Exception {
    final Collection<WordCounter.WordCount> wordCounts = getCaseSensitiveWordCounts("Test TeSt");
    assertEquals(2, wordCounts.size());
    assertTrue(wordCounts.contains(new WordCounter.WordCount("Test", 1)));
    assertTrue(wordCounts.contains(new WordCounter.WordCount("TeSt", 1)));
  }

  @Test
  public void testSameWordSeparatedByPunctuationIsCountedAsTwoWords() throws Exception {
    final Collection<WordCounter.WordCount> results = getCaseSensitiveWordCounts("Test;,.?Test");
    assertEquals(1, results.size());
    assertTrue(results.contains(new WordCounter.WordCount("Test", 2)));
  }

  @Test
  public void testCaseSensitiveZeroOccurencesWhenOnlyABlackListedWord() throws Exception {
    final Collection<WordCounter.WordCount> wordCounts = getCaseSensitiveWordCounts("Test Test", "Test");
    assertEquals(0, wordCounts.size());
  }

  @Test
  public void testCaseSensitiveCountOccurencesWhenOnlyOneWordMultipleTimesButADifferentWordAppearsInTheBlackList() throws Exception {
    final Collection<WordCounter.WordCount> wordCounts = getCaseSensitiveWordCounts("Test Test", "Missing");
    assertEquals(1, wordCounts.size());
    assertTrue(wordCounts.contains(new WordCounter.WordCount("Test", 2)));
  }

  private Collection<WordCounter.WordCount> getCaseSensitiveWordCounts(String postContent, String... blList) {
    final Set<String> blackList = createBlackList(blList);
    final WordCounter counter = WordCounter.caseSensitive(blackList);
    return doCount(postContent, counter);
  }

  private Collection<WordCounter.WordCount> getCaseInsensitiveWordCounts(String postContent, String... blList) {
    final Set<String> blackList = createBlackList(blList);
    final WordCounter counter = WordCounter.caseInsensitive(blackList);
    return doCount(postContent, counter);
  }

  private Set<String> createBlackList(String[] blList) {
    final Set<String> blackList = new HashSet<String>();
    Collections.addAll(blackList, blList);
    return blackList;
  }

  private Collection<WordCounter.WordCount> doCount(String postContent, WordCounter counter) {
    when(post.getContent()).thenReturn(postContent);
    return counter.countOccurencesWithin(post);
  }
}
