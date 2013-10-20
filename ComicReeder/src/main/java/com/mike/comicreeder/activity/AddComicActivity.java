package com.mike.comicreeder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mike.comicreeder.R;
import com.parse.ParseObject;

public class AddComicActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_comic);
    // Show the Up button in the action bar.
    setupActionBar();

    setUpEditTextAndLabel(R.id.comicName, R.id.comicFloatingLabel);
    setUpEditTextAndLabel(R.id.writer, R.id.writerFloatingLabel);
    setUpEditTextAndLabel(R.id.issueNum, R.id.issueNumFloatingLabel);
    setUpEditTextAndLabel(R.id.publisher, R.id.publisherFloatingLabel);
  }

  private void setUpEditTextAndLabel(final int editTextViewId, final int labelViewId) {
    final EditText editText = (EditText) findViewById(editTextViewId);

    TextWatcher textWatcher = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        TextView textView = (TextView)findViewById(labelViewId);
        if (s.length() == 0) {
          textView.setText("");
        }
        else if (s.length() == 1) {
          textView.setText(editText.getHint());
        }
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    };

    editText.addTextChangedListener(textWatcher);
  }


  public void addComic(View view) {

    EditText comicNameText = (EditText) findViewById(R.id.comicName);
    EditText writerText = (EditText) findViewById(R.id.writer);
    EditText issueNumberText = (EditText) findViewById(R.id.issueNum);
    EditText publisherText = (EditText) findViewById(R.id.publisher);

    ParseObject comic = new ParseObject("Comics");
    comic.put("comicName", comicNameText.getText().toString());
    comic.put("writer", writerText.getText().toString());
    comic.put("issue", Integer.parseInt(issueNumberText.getText().toString()));
    comic.put("publisher", publisherText.getText().toString());

    comic.saveInBackground();

    Intent intent = new Intent(this, ComicReederActivity.class);
    startActivity(intent);
  }

  /**
   * Set up the {@link android.app.ActionBar}, if the API is available.
   */
  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  private void setupActionBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      getActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.add_comic, menu);
    return true;
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        // This ID represents the Home or Up button. In the case of this
        // activity, the Up button is shown. Use NavUtils to allow users
        // to navigate up one level in the application structure. For
        // more details, see the Navigation pattern on Android Design:
        //
        // http://developer.android.com/design/patterns/navigation.html#up-vs-back
        //
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

}
