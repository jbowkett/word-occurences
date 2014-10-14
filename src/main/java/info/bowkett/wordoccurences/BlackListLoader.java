package info.bowkett.wordoccurences;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jbowkett on 14/10/2014.
 */
public class BlackListLoader {
  private final File blackList;

  public BlackListLoader(File blackList) {
    this.blackList = blackList;
  }

  public Set<String> getBlackList() {
    return new HashSet<String>();
  }
}
