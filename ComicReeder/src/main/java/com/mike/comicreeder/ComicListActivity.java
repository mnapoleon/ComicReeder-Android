package com.mike.comicreeder;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ComicListActivity extends ListActivity {

  private List<ParseObject> queryResults;

  private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params) {
      ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Comics");
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
      List<Comic> comicList = new ArrayList<Comic>();
      for (ParseObject queryResult : queryResults) {
        Comic comic = new Comic();
        comic.setObjectId(queryResult.getObjectId());
        comic.setWriter(queryResult.getString("writer"));
        comic.setComicName(queryResult.getString("comicName"));
        comic.setIssueNumber(Integer.toString(queryResult.getInt("issue")));
        comic.setPublisher(queryResult.getString("publisher"));
        comicList.add(comic);
      }
      ComicAdapter adapter = new ComicAdapter(ComicListActivity.this, R.layout.comic_list_item, comicList);
      setListAdapter(adapter);

    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_comiclist);

    new RemoteDataTask().execute();
    registerForContextMenu(getListView());
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.comic_list, menu);
    return true;
  }

}
