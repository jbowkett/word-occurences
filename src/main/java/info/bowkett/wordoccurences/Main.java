package info.bowkett.wordoccurences;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by jbowkett on 14/10/2014.
 */
public class Main {

  public static void main(String[] args) {

    if (allValid(args)){
      final File feedFile = new File(args[args.length - 2]);
      final File blackListFile = new File(args[args.length - 1]);
      final boolean ignoreCase = args.length == 3 && args[0].equalsIgnoreCase("-ignoreCase");

      final SAXParserFactory fact = SAXParserFactory.newInstance();
      try {
        final SAXParser saxParser = fact.newSAXParser();
        final FeedHandler feedHandler = new FeedHandler(new PostBuilder());
        saxParser.parse(feedFile, feedHandler);
        final List<Post> posts = feedHandler.getPosts();

        final BlackListLoader blackListLoader = new BlackListLoader(blackListFile);
        final Set<String> blackList = blackListLoader.getBlackList();
        final WordCounter counter = new WordCounter(blackList, ignoreCase);

        final Iterator<Post> iterator = posts.iterator();
        while(iterator.hasNext()){
          final Post post = iterator.next();
          final Collection<WordCounter.WordCount> wordCounts = counter.countOccurencesWithin(post);
          final List<WordCounter.WordCount> orderedCounts = new ArrayList<WordCounter.WordCount>();
          orderedCounts.addAll(wordCounts);
          //sort wordcounts
          Collections.sort(orderedCounts, new Comparator<WordCounter.WordCount>(){
            @Override
            public int compare(WordCounter.WordCount wordCount, WordCounter.WordCount wordCount2) {
              return wordCount2.getCount() - wordCount.getCount();
            }
          });
          final WordCounter.WordCount highestWordCount = orderedCounts.get(0);
          final String word = highestWordCount.getWord();
          final int count = highestWordCount.getCount();
          System.out.println("For post with title :[" + post.getTitle() + "] most common word is:["+word+"], occurring :["+count+"] times.");
        }

      } //multi-catch would be much nicer here
      catch (SAXException e) {
        e.printStackTrace();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
      catch (ParserConfigurationException e) {
        e.printStackTrace();
      }
    }
    else{
      usage();
    }



  }

  private static boolean allValid(String[] args) {
    return args.length == 2 ||
           args.length == 3 && args[0].equalsIgnoreCase("-ignoreCase");
  }

  private static void usage() {
    System.err.println("Main [-ignoreCase] <rss feed file> <word black list file>");
  }
}
