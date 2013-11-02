package com.mike.comicreeder.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

import com.mike.comicreeder.R;
import com.mike.comicreeder.components.FloatingLabelEditText;
import com.mike.comicreeder.model.Comic;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.search_comics_activity)
public class SearchForComicsActivity extends RoboActivity {

  @InjectView(R.id.comicNameSearch) FloatingLabelEditText comicName;
  @InjectView(R.id.publisherSearch) FloatingLabelEditText publisherName;
  @InjectView(R.id.writerSearch)    FloatingLabelEditText writerName;

  private class RemoteSearchTask extends AsyncTask<Map<String, String>, Void, Void> {

    private List<ParseObject> queryResults;

    @Override
    protected Void doInBackground(Map<String, String>... params) {
      ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Comics");
      Map<String, String> onlyParams = params[0];
      for (String key : onlyParams.keySet()) {
        query.whereEqualTo(key, onlyParams.get(key));
      }
      query.orderByAscending("comicName");
      query.addAscendingOrder("issue");
      try {
        queryResults = query.find();
      } catch (ParseException e) {

      }
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      ArrayList<Comic> comicList = new ArrayList<Comic>();
      for (ParseObject queryResult : queryResults) {
        Comic comic = new Comic();
        comic.setObjectId(queryResult.getObjectId());
        comic.setWriter(queryResult.getString("writer"));
        comic.setComicName(queryResult.getString("comicName"));
        comic.setIssueNumber(queryResult.getInt("issue"));
        comic.setPublisher(queryResult.getString("publisher"));
        comicList.add(comic);
      }

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

  public void searchForComics(View view) {

    Map<String, String> searchParams = new HashMap<String, String>();

    if (comicName.getTextFieldValue() != null && comicName.getTextFieldValue().toString().trim().length() > 0) {
      searchParams.put("comicName", comicName.getTextField().getText().toString().trim());
    }
    if (writerName.getTextFieldValue()!= null && writerName.getTextFieldValue().toString().trim().length() > 0) {
      searchParams.put("writer", writerName.getTextField().getText().toString().trim());
    }
    if (publisherName.getTextFieldValue()!= null && publisherName.getTextFieldValue().toString().trim().length() > 0) {
      searchParams.put("publisher", publisherName.getTextField().getText().toString().trim());
    }
    new RemoteSearchTask().execute(searchParams);
  }
}
