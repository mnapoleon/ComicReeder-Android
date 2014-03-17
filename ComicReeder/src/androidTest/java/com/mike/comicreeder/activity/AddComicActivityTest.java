package com.mike.comicreeder.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.Button;

import com.mike.comicreeder.R;
import com.mike.comicreeder.components.FloatingLabelEditText;


/**
 * Created by michaelnapoleon on 10/17/13.
 */
public class AddComicActivityTest extends ActivityInstrumentationTestCase2<AddComicActivity> {

  AddComicActivity addComicActivity;

  FloatingLabelEditText comicName;
  FloatingLabelEditText writerName;
  FloatingLabelEditText issueNumber;
  FloatingLabelEditText publisherName;
  Button addButton;

  public AddComicActivityTest() {
    super(AddComicActivity.class);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    setActivityInitialTouchMode(true);
    addComicActivity = getActivity();

    comicName = (FloatingLabelEditText) addComicActivity.findViewById(R.id.comicName);
    writerName = (FloatingLabelEditText) addComicActivity.findViewById(R.id.writer);
    issueNumber = (FloatingLabelEditText) addComicActivity.findViewById(R.id.issueNum);
    publisherName = (FloatingLabelEditText) addComicActivity.findViewById(R.id.publisher);
    addButton = (Button) addComicActivity.findViewById(R.id.add_button);
  }

  @SmallTest
  public void testEditTexts_layout() {
    final View decorView = addComicActivity.getWindow().getDecorView();

    ViewAsserts.assertOnScreen(decorView, comicName);
    ViewAsserts.assertOnScreen(decorView, writerName);
    ViewAsserts.assertOnScreen(decorView, issueNumber);
    ViewAsserts.assertOnScreen(decorView, publisherName);
    ViewAsserts.assertOnScreen(decorView, addButton);
  }
}
