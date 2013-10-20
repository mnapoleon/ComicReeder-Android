package com.mike.comicreeder.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.Button;

import com.mike.comicreeder.R;

/**
 * Created by michaelnapoleon on 10/11/13.
 */
public class ComicReederActivityTest extends ActivityInstrumentationTestCase2<ComicReederActivity> {

  ComicReederActivity comicReederActivity;

  Button button1;
  Button button2;

  public ComicReederActivityTest() {
    super(ComicReederActivity.class);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    setActivityInitialTouchMode(true);

    comicReederActivity = getActivity();

    button1 = (Button) comicReederActivity.findViewById(R.id.search_button);
    button2 = (Button) comicReederActivity.findViewById(R.id.add_comic_button);
  }

  @SmallTest
  public void testButtons_layout() {
    final View decorView = comicReederActivity.getWindow().getDecorView();

    ViewAsserts.assertOnScreen(decorView, button1);
    ViewAsserts.assertOnScreen(decorView, button2);
  }
}
