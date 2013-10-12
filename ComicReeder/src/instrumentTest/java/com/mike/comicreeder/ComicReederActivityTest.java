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

  Button button;

  public ComicReederActivityTest() {
    super(ComicReederActivity.class);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    startActivity(new Intent(getInstrumentation().getTargetContext(), ComicReederActivity.class), null, null);

    comicReederActivity = getActivity();

    button = (Button) comicReederActivity.findViewById(R.id.get_button);
  }

  @SmallTest
  public void testButton() {
    String actualButtonText = button.getText().toString();

    String expected = "Get My Comics";
    assertEquals(expected, actualButtonText);
  }
}
