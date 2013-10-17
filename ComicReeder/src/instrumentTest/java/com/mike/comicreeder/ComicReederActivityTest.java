package com.mike.comicreeder;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import android.widget.Button;

/**
 * Created by michaelnapoleon on 10/11/13.
 */
public class ComicReederActivityTest extends ActivityUnitTestCase<ComicReederActivity> {

  ComicReederActivity comicReederActivity;

  Button button1;
  Button button2;
  Button button3;

  public ComicReederActivityTest() {
    super(ComicReederActivity.class);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    startActivity(new Intent(getInstrumentation().getTargetContext(), ComicReederActivity.class), null, null);

    comicReederActivity = getActivity();

    button1 = (Button) comicReederActivity.findViewById(R.id.get_button);
    button2 = (Button) comicReederActivity.findViewById(R.id.search_button);
    button3 = (Button) comicReederActivity.findViewById(R.id.add_comic_button);
  }

  @SmallTest
  public void testButtons() {
    String actualButton1Text = button1.getText().toString();
    String expected1 = "Get My Comics";
    assertEquals(expected1, actualButton1Text);

    String actualButton2Text = button2.getText().toString();
    String expected2 = "Search for Comics";
    assertEquals(expected2, actualButton2Text);

    String actualButton3Text = button3.getText().toString();
    String expected3 = "Add Comic";
    assertEquals(expected3, actualButton3Text);
  }
}
