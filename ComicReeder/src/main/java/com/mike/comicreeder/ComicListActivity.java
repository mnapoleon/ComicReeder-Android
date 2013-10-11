package com.mike.comicreeder;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComicListActivity extends ListActivity {

  private List<ParseObject> comics;

  private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params) {
      ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Comic");
      try {
        comics = query.find();
      } catch (ParseException e) {

      }
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      List<Map<String, String>> comicList = new ArrayList<Map<String, String>>();
      for (ParseObject comic : comics) {
        Map<String, String> comicMap = new HashMap<String, String>();
        comicMap.put("comicName", comic.getString("comicName"));
        comicMap.put("Writer",comic.getString("Writer"));
        comicList.add(comicMap);
      }
      SimpleAdapter adapter = new SimpleAdapter(ComicListActivity.this,
          comicList,
          android.R.layout.simple_expandable_list_item_2,
          new String[] {"comicName", "Writer"},
          new int[] {android.R.id.text1, android.R.id.text2});
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
