package com.mike.comicreeder;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.EditText;

/**
 * Created by michaelnapoleon on 10/17/13.
 */
public class AddComicActivityTest extends ActivityInstrumentationTestCase2<AddComicActivity> {

  AddComicActivity addComicActivity;

  EditText comicName;
  EditText writerName;
  EditText issueNumber;
  EditText publisherName;

  public AddComicActivityTest() {
    super(AddComicActivity.class);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    setActivityInitialTouchMode(true);
    addComicActivity = getActivity();

    comicName = (EditText) addComicActivity.findViewById(R.id.comicName);
    writerName = (EditText) addComicActivity.findViewById(R.id.writer);
    issueNumber = (EditText) addComicActivity.findViewById(R.id.issueNum);
    publisherName = (EditText) addComicActivity.findViewById(R.id.publisher);

  }

  @SmallTest
  public void testEditTexts_layout() {
    final View decorView = addComicActivity.getWindow().getDecorView();

    ViewAsserts.assertOnScreen(decorView, comicName);
    ViewAsserts.assertOnScreen(decorView, writerName);
    ViewAsserts.assertOnScreen(decorView, issueNumber);
    ViewAsserts.assertOnScreen(decorView, publisherName);
  }
}
