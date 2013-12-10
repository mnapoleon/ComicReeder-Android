package com.mike.comicreeder.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mike.comicreeder.R;

/**
 * Created by michaelnapoleon on 12/9/13.
 */
public class ComicReederFragment extends Fragment {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

    Log.d("IN_FRAG","Message in fragment");

    View view = inflater.inflate(R.layout.fragment_comic_reeder, parent, false);
    return view;
  }
}
