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

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


  private class RemoteComicVineSearchTask extends AsyncTask<Void, Void, List<String>> {

    private static final String TAG = "RemoteComicVineSearchTask";

    @Override
    protected List<String> doInBackground(Void... params) {

      String comicVineUrl = "http://www.comicvine.com/api/volumes/?api_key=0447b47f0d12e229dfef9cdafec888806410e871&filter=name:Morning%20Glories,publisher:Image&sort=name&field_list=id,name,start_year,publisher,count_of_issues,image&format=json&limit=2";

      AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

      HttpGet request =  new HttpGet(comicVineUrl);
      JSONResponseHandler responseHandler = new JSONResponseHandler();
      try {
        return mClient.execute(request, responseHandler);
      }
      catch (ClientProtocolException e1) {
        Log.e(TAG, "ClientProtocolException");
      }
      catch (IOException e2) {
        Log.e(TAG, "IOException");
      }
      return null;

    }

    @Override
    protected void onPostExecute(List<String> s) {
      super.onPostExecute(s);

      mComicVineResultTextView.setText(s.get(0));
    }

    private class JSONResponseHandler implements ResponseHandler<List<String>> {

      private static final String RESULTS = "results";
      private static final String NAME_TAG = "name";
      private static final String ISSUE_COUNT = "count_of_issues";
      private static final String PUBLISHER = "publisher";

      @Override
      public List<String> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        List<String> result = new ArrayList<String>();
        String JSONResponse = new BasicResponseHandler().handleResponse(response);

        try {
          JSONObject responseObject = (JSONObject) new JSONTokener(
              JSONResponse).nextValue();

          JSONArray results = responseObject.getJSONArray(RESULTS);

          for (int idx = 0; idx < results.length(); idx++) {
            JSONObject comic = (JSONObject)results.get(idx);
            JSONObject publisher = (JSONObject)comic.get(PUBLISHER);

            result.add("[Comic name] " + comic.get(NAME_TAG) + " :: " +
                "[Number of Issues] " + comic.get(ISSUE_COUNT) + " :: " +
                "[Publisher] " + publisher.get(NAME_TAG));
          }
        }
        catch (JSONException e1) {
          e1.printStackTrace();
        }
        return result;
      }
    }
  }
}
