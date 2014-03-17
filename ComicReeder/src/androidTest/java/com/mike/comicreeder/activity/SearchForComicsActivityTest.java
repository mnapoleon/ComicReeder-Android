package com.mike.comicreeder.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.Button;

import com.mike.comicreeder.components.FloatingLabelEditText;
import com.mike.comicreeder.R;

/**
 * Created by michaelnapoleon on 11/3/13.
 */
public class SearchForComicsActivityTest extends ActivityInstrumentationTestCase2<SearchForComicsActivity> {

  SearchForComicsActivity searchForComicsActivity;

  FloatingLabelEditText comicName;
  FloatingLabelEditText publisherName;
  FloatingLabelEditText writerName;
  Button searchButton;

  public SearchForComicsActivityTest() {
    super(SearchForComicsActivity.class);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    setActivityInitialTouchMode(true);
    searchForComicsActivity = getActivity();

    comicName = (FloatingLabelEditText) searchForComicsActivity.findViewById(R.id.comicNameSearch);
    publisherName = (FloatingLabelEditText) searchForComicsActivity.findViewById(R.id.publisherSearch);
    writerName = (FloatingLabelEditText) searchForComicsActivity.findViewById(R.id.writerSearch);
    searchButton = (Button) searchForComicsActivity.findViewById(R.id.search_button);
  }

  @SmallTest
  public void test_layout() {

    final View decorView = searchForComicsActivity.getWindow().getDecorView();

    ViewAsserts.assertOnScreen(decorView, comicName);
    ViewAsserts.assertOnScreen(decorView, writerName);
    ViewAsserts.assertOnScreen(decorView, publisherName);
    ViewAsserts.assertOnScreen(decorView, searchButton);
  }
}
