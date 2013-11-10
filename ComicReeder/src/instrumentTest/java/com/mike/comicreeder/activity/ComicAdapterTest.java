package com.mike.comicreeder.activity;

import android.test.AndroidTestCase;
import android.view.View;
import android.widget.TextView;

import com.mike.comicreeder.R;
import com.mike.comicreeder.model.Comic;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by michaelnapoleon on 11/9/13.
 */
public class ComicAdapterTest extends AndroidTestCase {

  private ComicAdapter adapter;

  private Comic comic1;
  private Comic comic2;

  public ComicAdapterTest() {
    super();
  }

  protected void setUp() throws Exception {
    super.setUp();

    ArrayList<Comic> data = new ArrayList<Comic>();

    comic1 = new Comic();
    comic1.setComicName("Comic1");
    comic1.setIssueNumber(1);
    comic1.setWriter("Writer1");
    comic1.setPublisher("ComicPub1");
    comic1.setObjectId("IDIDIDID");

    comic2 = new Comic();
    comic2.setComicName("Comic2");
    comic2.setIssueNumber(2);
    comic2.setWriter("Writer2");
    comic2.setPublisher("ComicPub1");
    comic2.setObjectId("IOIOIOIOIO");

    data.add(comic1);
    data.add(comic2);
    adapter = new ComicAdapter(getContext(), R.layout.comic_list_item, data);
  }

  public void testGetView() {
    View view = adapter.getView(0, null, null);

    TextView topText = (TextView)view.findViewById(R.id.toptextdata);

    assertNotNull(view);
    assertNotNull(topText);
    assertEquals("Comic name should be Comic1", comic1.getComicName(), topText.getText());
  }
}
