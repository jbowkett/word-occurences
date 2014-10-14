package info.bowkett.wordoccurences;

/**
 * Created by jbowkett on 14/10/2014.
 *
 * GoF builder pattern
 */
public class PostBuilder {
  private StringBuilder buffer = new StringBuilder();
  private String title;
  private String date;
  private String creator;
  private String description;
  private String content;
  private String author;
  private boolean append = false;

  public void reset() {
    resetBuffer();
    title = date  = creator = description = content = author = null;
  }

  public Post buildPost() {
    return new Post(title, date, creator, description, content, author);
  }


  public void appendCharacters(char[] chars) {
    if(append){
      buffer.append(new String(chars).trim());
    }
  }

  public void titleComplete() {
    title = buffer.toString();
    resetBuffer();
  }

  private void resetBuffer() {
    buffer.delete(0, buffer.length());
  }

  public void publishingDateComplete() {
    date = buffer.toString();
    resetBuffer();
  }

  public void creatorComplete() {
    creator = buffer.toString();
    resetBuffer();
  }

  public void descriptionComplete() {
    description = buffer.toString();
    resetBuffer();
  }

  public void contentComplete() {
    content = buffer.toString();
    resetBuffer();
  }

  public void authorComplete() {
    author = buffer.toString();
    resetBuffer();
  }

  public void ingestContent(boolean shouldIngest) {
    append = shouldIngest;
  }
}
