package info.bowkett.wordoccurences;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
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

  private Set<String> blackList;
  private WordCounter counter;

  @Mock Post post;

  @Before
  public void before() throws Exception {
    blackList = new HashSet<String>();
    counter = new WordCounter(blackList);
  }

  /**
   * Method: countOccurences(Post post)
   */
  @Test
  public void testCountOccurencesWhenOnlyOneWordInPostWithNoBlackList() throws Exception {
    when(post.getContent()).thenReturn("Test");
    final Collection<WordCounter.WordCount> wordCounts = counter.countOccurences(post);
    assertEquals(1, wordCounts.size());
  }

  @Test
  public void testCountOccurencesWhenOnlyOneWordMultipleTimesInPostWithNoBlackList() throws Exception {
    when(post.getContent()).thenReturn("Test Test");
    final Collection<WordCounter.WordCount> wordCounts = counter.countOccurences(post);
    assertEquals(1, wordCounts.size());
    final WordCounter.WordCount[] results = new WordCounter.WordCount[1];
    wordCounts.toArray(results);
    assertEquals(new WordCounter.WordCount("Test", 2), results[0]);
  }

  @Test
  public void testCountOccurencesWhenOnlyOneWordMultipleTimesInPostSeparatedByPunctuationWithNoBlackList() throws Exception {
    when(post.getContent()).thenReturn("Test;,.?Test");
    final Collection<WordCounter.WordCount> wordCounts = counter.countOccurences(post);
    assertEquals(1, wordCounts.size());
    final WordCounter.WordCount[] results = new WordCounter.WordCount[1];
    wordCounts.toArray(results);
    assertEquals(new WordCounter.WordCount("Test", 2), results[0]);
  }

  @Test
  public void testCountOccurencesWhenOnlyOneWordMultipleTimesButThatWordAppearsInTheBlackList() throws Exception {
    when(post.getContent()).thenReturn("Test Test");
    blackList.add("Test");
    final Collection<WordCounter.WordCount> wordCounts = counter.countOccurences(post);
    assertEquals(0, wordCounts.size());
  }

  @Test
  public void testCountOccurencesWhenOnlyOneWordMultipleTimesButADifferentWordAppearsInTheBlackList() throws Exception {
    when(post.getContent()).thenReturn("Test Test");
    blackList.add("Missing");
    final Collection<WordCounter.WordCount> wordCounts = counter.countOccurences(post);
    assertEquals(1, wordCounts.size());
    final WordCounter.WordCount[] results = new WordCounter.WordCount[1];
    wordCounts.toArray(results);
    assertEquals(new WordCounter.WordCount("Test", 2), results[0]);
  }
}
