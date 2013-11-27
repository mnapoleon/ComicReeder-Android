package com.mike.comicreeder.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

import com.mike.comicreeder.R;
import com.mike.comicreeder.components.FloatingLabelEditText;
import com.mike.comicreeder.model.Comic;
import com.mike.comicreeder.model.ParseComics;
import com.mike.comicreeder.remote.ComicReederSearchTask;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.search_comics_activity)
public class SearchForComicsActivity extends RoboActivity implements ParseComics {

  @InjectView(R.id.comicNameSearch) FloatingLabelEditText mComicName;
  @InjectView(R.id.publisherSearch) FloatingLabelEditText mPublisherName;
  @InjectView(R.id.writerSearch)    FloatingLabelEditText mWriterName;

  private class RemoteSearchTask extends AsyncTask<Map<String, String>, Void, List<ParseObject>> {

    private ComicReederSearchTask searchTask = new ComicReederSearchTask();

    @Override
    protected List<ParseObject> doInBackground(Map<String, String>... params) {
      return searchTask.searchForComics(params[0]);
    }

    @Override
    protected void onPostExecute(List<ParseObject> result) {
      ArrayList<Comic> comicList = searchTask.getComicList(result);

      Intent intent = new Intent(SearchForComicsActivity.this, ComicSearchListActivity.class);
      intent.putParcelableArrayListExtra("comicData", comicList);

      startActivity(intent);
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Show the Up button in the action bar.
    setupActionBar();
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
    getMenuInflater().inflate(R.menu.search_from_comics, menu);
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

  /**
   * Sets up the search criteria before executing the remote task.
   * @param view
   */
  public void searchForComics(View view) {

    Map<String, String> searchParams = new HashMap<String, String>();

    if (mComicName.getTextFieldValue() != null && mComicName.getTextFieldValue().toString().trim().length() > 0) {
      searchParams.put(COLUMN_COMIC_NAME, mComicName.getTextField().getText().toString().trim());
    }
    if (mWriterName.getTextFieldValue()!= null && mWriterName.getTextFieldValue().toString().trim().length() > 0) {
      searchParams.put(COLUMN_WRITER, mWriterName.getTextField().getText().toString().trim());
    }
    if (mPublisherName.getTextFieldValue()!= null && mPublisherName.getTextFieldValue().toString().trim().length() > 0) {
      searchParams.put(COLUMN_PUBLISHER, mPublisherName.getTextField().getText().toString().trim());
    }
    new RemoteSearchTask().execute(searchParams);
  }
}
