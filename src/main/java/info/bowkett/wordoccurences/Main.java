package info.bowkett.wordoccurences;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by jbowkett on 14/10/2014.
 */
public class Main {

  public static void main(String[] args) {

    if (args.length == 2){
      final File feedFile = new File(args[0]);
      final File blackListFile = new File(args[1]);

      final SAXParserFactory fact = SAXParserFactory.newInstance();
      try {
        final SAXParser saxParser = fact.newSAXParser();
        final FeedHandler feedHandler = new FeedHandler(new PostBuilder());
        saxParser.parse(feedFile, feedHandler);
        final List<Post> posts = feedHandler.getPosts();

        final BlackListLoader blackListLoader = new BlackListLoader(blackListFile);
        final Set<String> blackList = blackListLoader.getBlackList();
        final WordCounter counter = new WordCounter(blackList);
        final Iterator<Post> iterator = posts.iterator();
        while(iterator.hasNext()){
          final List<WordCounter.WordCount> wordCounts = counter.countOccurences(iterator.next());
          //sort wordcounts
          //print first
        }


      }
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

  private static void usage() {
    System.err.println("Main <rss feed file> <word black list file>");
  }
}
