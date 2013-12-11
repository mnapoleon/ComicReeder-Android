package com.mike.comicreeder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mike.comicreeder.R;
import com.mike.comicreeder.components.FloatingLabelEditText;
import com.mike.comicreeder.model.ParseComics;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

import roboguice.fragment.RoboFragment;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by michaelnapoleon on 12/10/13.
 */
@ContentView(R.layout.fragment_add_comic)
public class AddComicFragment extends RoboFragment implements ParseComics {

  @InjectView(R.id.comicName) FloatingLabelEditText mComicNameText;
  @InjectView(R.id.writer)    FloatingLabelEditText mWriterText;
  @InjectView(R.id.issueNum)  FloatingLabelEditText mIssueNumberText;
  @InjectView(R.id.publisher) FloatingLabelEditText mPublisherText;
  @InjectView(R.id.add_comic_button) Button mAddComicButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    super.onCreateView(inflater, parent, savedInstanceState);

    View view = inflater.inflate(R.layout.fragment_add_comic, parent, false);

    return view;
  }

  /**
   * Used this because the views aren't injected until after this call.  Needed it to
   * get views.
   * @param view
   * @param savedInstanceState
   */
  @Override
  public void onViewCreated (View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    setAddComicButtonClickListerner();
  }

  /**
   * Used to display a 'toast' message to the user to let
   * them know the save was saved.
   */
  private void comicSavedToast() {
    Context context = getActivity().getApplicationContext();
    CharSequence text = "Comic saved!";

    int duration = Toast.LENGTH_SHORT;
    Toast toast = Toast.makeText(context, text, duration);
    toast.show();

  }

  protected void setAddComicButtonClickListerner() {
    View.OnClickListener addComicButtonListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ParseObject comic = new ParseObject(CLASS_COMICS);
        comic.put(COLUMN_COMIC_NAME, mComicNameText.getTextFieldValue().toString());
        comic.put(COLUMN_WRITER, mWriterText.getTextFieldValue().toString());
        comic.put(COLUMN_ISSUE, Integer.parseInt(mIssueNumberText.getTextFieldValue().toString()));
        comic.put(COLUMN_PUBLISHER, mPublisherText.getTextFieldValue().toString());
        comic.setACL(new ParseACL(ParseUser.getCurrentUser()));
        comic.put(COLUMN_USER, ParseUser.getCurrentUser());

        comic.saveInBackground();
        comicSavedToast();

        Intent intent = new Intent(getActivity(), ComicReederActivity.class);
        startActivity(intent);
      }
    };

    mAddComicButton.setOnClickListener(addComicButtonListener);
  }
}
