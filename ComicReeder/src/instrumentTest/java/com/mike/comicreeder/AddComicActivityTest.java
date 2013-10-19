package com.mike.comicreeder;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.EditText;

/**
 * Created by michaelnapoleon on 10/17/13.
 */
public class AddComicActivityTest extends ActivityUnitTestCase<AddComicActivity> {

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

    startActivity(new Intent(getInstrumentation().getTargetContext(), AddComicActivity.class), null, null);

  }

  @SmallTest
  public void testEditTextControls() {
    addComicActivity = getActivity();

    comicName = (EditText) addComicActivity.findViewById(R.id.comicName);
    writerName = (EditText) addComicActivity.findViewById(R.id.writer);
    issueNumber = (EditText) addComicActivity.findViewById(R.id.issueNum);
    publisherName = (EditText) addComicActivity.findViewById(R.id.publisher);

    assertNotNull(comicName);
    assertNotNull(writerName);
    assertNotNull(issueNumber);
    assertNotNull(publisherName);
  }
}
