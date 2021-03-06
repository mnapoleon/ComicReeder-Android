package com.mike.comicreeder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mike.comicreeder.R;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;

public class ComicReederActivity extends FragmentActivity {

  private static String PARSE_APP_ID = "uKoPYsEPCuxyfZT3M5lyTytsiyZij0RHCSY1VuZ4";
  private static String PARSE_CLIENT_ID = "G1lqqnyoKiwTLOWj50ZWNoRcVIm25jN5IMAgtzxd";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initializeParse();

    ParseUser currentUser = ParseUser.getCurrentUser();

    if (currentUser == null) {
      Intent intent = new Intent(this, LoginActivity.class);
      startActivity(intent);
      finish();
    }

    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment = fm.findFragmentById(R.id.comicreeder_fragmentcontainer);

    if (fragment == null) {
      fragment = new ComicReederFragment();
      fm.beginTransaction()
          .add(R.id.comicreeder_fragmentcontainer, fragment)
          .commit();
    }
  }

  public void addComic(View view) {
    Intent intent = new Intent(this, AddComicActivity.class);
    startActivity(intent);
  }

  public void searchForComics(View view) {
    Intent intent = new Intent(this, SearchForComicsActivity.class);
    startActivity(intent);
  }

  public void searchComicVine(View view) {
    Intent intent = new Intent(this, ComicVineSearchActivity.class);
    startActivity(intent);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.comic_reeder, menu);
    return true;
  }

  private void initializeParse() {
    Parse.initialize(this, PARSE_APP_ID, PARSE_CLIENT_ID);
    ParseAnalytics.trackAppOpened(getIntent());
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_logout:
        ParseUser.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
    return false;
  }
}
