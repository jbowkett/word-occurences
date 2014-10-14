package info.bowkett.wordoccurences;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 * Created by jbowkett on 14/10/2014.
 */
public class FeedHandler extends DefaultHandler {
  private final PostBuilder postBuilder;
  private List<Post> posts = new ArrayList<Post>();
  private static final Set<String> IGNORED_TAGS = new HashSet<String>();

  public FeedHandler(PostBuilder postBuilder) {
    this.postBuilder = postBuilder;
  }


  static {
    IGNORED_TAGS.add("link");
    IGNORED_TAGS.add("comments");
    IGNORED_TAGS.add("symbol");
    IGNORED_TAGS.add("wfw:commentRss");
    IGNORED_TAGS.add("slash:comments");
    IGNORED_TAGS.add("wfw:commentRss");
    IGNORED_TAGS.add("slash:comments");
  }

  public List<Post> getPosts() {
    return posts;
  }

  public void startElement(String uri, String localName, String qName, Attributes attributes){
    if(IGNORED_TAGS.contains(qName)){
          //ignore this tag
      postBuilder.ingestContent(false);
    }
    else if(qName.equals("title")){
      postBuilder.ingestContent(true);
    }
    else if(qName.equals("pubDate")){
      postBuilder.ingestContent(true);
    }
    else if(qName.equals("dc:creator")){
      postBuilder.ingestContent(true);
    }
    else if(qName.equals("description")){
      postBuilder.ingestContent(true);
    }
    else if(qName.equals("content:encoded")){
      postBuilder.ingestContent(true);
    }
    else if(qName.equals("author")){
      postBuilder.ingestContent(true);
    }
  }

  public void characters(char[] ch, int start, int length){
    final char[] chars = Arrays.copyOfRange(ch, start, start + length);
    postBuilder.appendCharacters(chars);
  }



  public void endElement(String uri, String localName, String qName){
    if(IGNORED_TAGS.contains(qName)){
      //ignore this tag
    }

    else if (qName.equals("item")){
      final Post post = postBuilder.buildPost();
      posts.add(post);
      postBuilder.reset();
    }
    else if(qName.equals("title")){
      postBuilder.titleComplete();
    }
    else if(qName.equals("pubDate")){
      postBuilder.publishingDateComplete();
    }
    else if(qName.equals("dc:creator")){
      postBuilder.creatorComplete();
    }
    else if(qName.equals("description")){
      postBuilder.descriptionComplete();
    }
    else if(qName.equals("content:encoded")){
      postBuilder.contentComplete();
    }
    else if(qName.equals("author")){
      postBuilder.authorComplete();
    }

  }
}
