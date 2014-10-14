package info.bowkett.wordoccurences;

import java.util.Date;

/**
 * Created by jbowkett on 14/10/2014.
 */
public class Post {

  private final String title, date,creator, description, content, author;

  public Post(String title, String date, String creator, String description, String content, String author) {
    this.author = author;
    this.title = title;
    this.date = date;
    this.creator = creator;
    this.description = description;
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  /*
  full implementation:
  private final String title, link, comments, creator, guid, desciption, commentRssLink, slash, author;
  private final String [] categories, symbols, stockSymbols, content;
  private final Date publishingDate;

  public Post(String title, String link, String comments, String creator,
              String guid, String desciption, String commentRssLink,
              String slash, String author, String[] categories,
              String[] symbols, String[] stockSymbols, String[] content,
              Date publishingDate) {
    this.title = title;
    this.link = link;
    this.comments = comments;
    this.creator = creator;
    this.guid = guid;
    this.desciption = desciption;
    this.commentRssLink = commentRssLink;
    this.slash = slash;
    this.author = author;
    this.categories = categories;
    this.symbols = symbols;
    this.stockSymbols = stockSymbols;
    this.content = content;
    this.publishingDate = publishingDate;
  }
  */
}
