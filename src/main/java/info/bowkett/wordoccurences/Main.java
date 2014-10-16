package info.bowkett.wordoccurences;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
          final Collection<WordCounter.WordCount> wordCounts = counter.countOccurencesWithin(iterator.next());
          //sort wordcounts
          //print first
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
