package com.mike.comicreeder.activity;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mike.comicreeder.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import roboguice.fragment.RoboFragment;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by michaelnapoleon on 2/20/14.
 */
@ContentView(R.layout.fragment_comic_vine_search)
public class ComicVineSearchFragment extends RoboFragment {

  @InjectView(R.id.search_comicvine_button) Button mSearchComicVineButton;
  @InjectView(R.id.comicvine_result) TextView mComicVineResultTextView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    super.onCreateView(inflater, parent, savedInstanceState);

    View view = inflater.inflate(R.layout.fragment_comic_vine_search, parent, false);

    return view;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mSearchComicVineButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        new RemoteComicVineSearchTask().execute();
      }
    });
  }


  private class RemoteComicVineSearchTask extends AsyncTask<Void, Void, String> {

    private static final String TAG = "RemoteComicVineSearchTask";

    @Override
    protected String doInBackground(Void... params) {

      String comicVineUrl = "http://api.comicvine.com/volumes/?api_key=0447b47f0d12e229dfef9cdafec888806410e871&filter=name:Morning%20Glories,publisher:Image&sort=name&field_list=id,name,start_year,publisher,count_of_issues,image&format=json&limit=1";

      HttpURLConnection urlConnection = null;
      String result = "";

      try {
        urlConnection = (HttpURLConnection) new URL(comicVineUrl).openConnection();
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        result = readStream(in);
      }
      catch (MalformedURLException e1) {
        Log.e(TAG, "MalformedException");
      }
      catch (IOException e2) {
        Log.e(TAG, "IOException");
      }
      finally {
        if (null != urlConnection) {
          urlConnection.disconnect();
        }
      }
      return result;

    }

    @Override
    protected void onPostExecute(String s) {
      super.onPostExecute(s);

      mComicVineResultTextView.setText(s);
    }

    private String readStream(InputStream in) {
      BufferedReader reader = null;
      StringBuffer data = new StringBuffer("");
      try {
        reader = new BufferedReader(new InputStreamReader(in));
        String line = "";
        while ((line = reader.readLine()) != null) {
          data.append(line);
        }
      } catch (IOException e) {
        Log.e(TAG, "IOException");
      } finally {
        if (reader != null) {
          try {
            reader.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
      return data.toString();
    }
  }
}
