package com.mike.comicreeder.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mike.comicreeder.R;
import com.mike.comicreeder.model.Comic;

import java.util.ArrayList;

public class ComicSearchListActivity extends ListActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.comic_search_listactivity);
    // Show the Up button in the action bar.
    setupActionBar();

    ArrayList<Comic> comicList = getIntent().getParcelableArrayListExtra("comicData");
    if (comicList == null || comicList.size() == 0) {
      searchResultsToast(0);
    }
    else {
      ComicAdapter adapter = new ComicAdapter(this, R.layout.comic_list_item, comicList);
      setListAdapter(adapter);
      searchResultsToast(comicList.size());
    }

    final ListView lv = getListView();

    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectComic(view, (Comic) lv.getItemAtPosition(position));
      }
    });
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
    getMenuInflater().inflate(R.menu.comic_search_list, menu);
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
   * Used to display a 'toast' message to user to indicate number of
   * comics found in the search.
   * @param numberOfComicsFound number of comics found
   */
  private void searchResultsToast(int numberOfComicsFound) {
    Context context = getApplicationContext();
    CharSequence text = "Search found " + numberOfComicsFound + " comics!";

    int duration = Toast.LENGTH_SHORT;
    Toast toast = Toast.makeText(context, text, duration);
    toast.show();
  }

  public void selectComic(View view, Comic selectedComic) {
    Context context = getApplicationContext();

    if (selectedComic.getCoverImageUrl() != null) {
      Intent intent = new Intent(ComicSearchListActivity.this, CoverImageActivity.class);
      intent.putExtra("coverImageUrl", selectedComic.getCoverImageUrl());
      startActivity(intent);
    }
    else {
      CharSequence text = selectedComic.getComicName();
      int duration = Toast.LENGTH_SHORT;
      Toast toast = Toast.makeText(context, text, duration);
      toast.show();
    }
  }
}
