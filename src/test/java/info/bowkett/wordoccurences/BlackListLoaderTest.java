package info.bowkett.wordoccurences;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.File;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;


/**
 * BlackListLoader Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Oct 16, 2014</pre>
 */
public class BlackListLoaderTest {

  @Before
  public void before() throws Exception {
  }

  @After
  public void after() throws Exception {
  }

  /**
   * Method: getBlackList()
   */
  @Test
  public void testGetBlackList() throws Exception {
    final File file = new File("src/test/resources/blacklist-test-fixture.txt");
    final BlackListLoader loader = new BlackListLoader(file);
    final Set<String> blackList = loader.getBlackList();
    assertNotNull(blackList);
    assertEquals(3, blackList.size());
    assertTrue(blackList.contains("a"));
    assertTrue(blackList.contains("able"));
    assertTrue(blackList.contains("about"));
  }
}
