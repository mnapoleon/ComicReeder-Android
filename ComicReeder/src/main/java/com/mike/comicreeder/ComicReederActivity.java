package com.mike.comicreeder;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

import com.parse.Parse;
import com.parse.ParseAnalytics;


public class ComicReederActivity extends Activity {
  private static String PARSE_APP_ID = "uKoPYsEPCuxyfZT3M5lyTytsiyZij0RHCSY1VuZ4";
  private static String PARSE_CLIENT_ID = "G1lqqnyoKiwTLOWj50ZWNoRcVIm25jN5IMAgtzxd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

      Parse.initialize(this, PARSE_APP_ID, PARSE_CLIENT_ID);

      ParseAnalytics.trackAppOpened(getIntent());
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
    }

    public void getComics(View view) {
      Intent intent = new Intent(this, ComicListActivity.class);
      startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.comic_reeder, menu);
        return true;
    }
    
}
