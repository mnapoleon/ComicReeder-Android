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

import com.loopj.android.image.SmartImageView;
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
  @InjectView(R.id.cv_smallIcon) SmartImageView mSmartImageView;
  @InjectView(R.id.cv_comic_name) TextView mCVComicName;
  @InjectView(R.id.cv_issue_num) TextView mIssuNum;
  @InjectView(R.id.cv_publisher) TextView mPublisher;

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


  private class RemoteComicVineSearchTask extends AsyncTask<Void, Void, List<ComicVineVolume>> {

    private static final String TAG = "RemoteComicVineSearchTask";

    @Override
    protected List<ComicVineVolume> doInBackground(Void... params) {

      String comicVineUrl = "http://www.comicvine.com/api/volumes/?";
      String apiKey = "api_key=0447b47f0d12e229dfef9cdafec888806410e871";
      String filterString = "filter=name:Morning%20Glories,publisher:Image";
      String sortString = "sort=name";
      String fieldListString = "field_list=id,name,start_year,publisher,count_of_issues,image";
      String formatString = "format=json";
      String limitString = "limit=2";

      String completeUrl = comicVineUrl.concat(apiKey).concat("&").concat(filterString).concat("&")
          .concat(sortString).concat("&").concat(fieldListString).concat("&").concat(formatString)
          .concat("&").concat(limitString);

      Log.i(TAG, "URL:: " + completeUrl);

      AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

      HttpGet request =  new HttpGet(completeUrl);
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
      finally {
        mClient.close();
      }
      return null;

    }

    @Override
    protected void onPostExecute(List<ComicVineVolume> s) {
      super.onPostExecute(s);

      //ComicVineResultTextView.setText(s.get(0));
      ComicVineVolume comic = s.get(1);

      mCVComicName.setText(comic.comicName);
      mIssuNum.setText(comic.issueNum);
      mPublisher.setText(comic.publisher);
      mSmartImageView.setImageUrl(comic.imageSrc);
    }

    private class JSONResponseHandler implements ResponseHandler<List<ComicVineVolume>> {

      private static final String RESULTS = "results";
      private static final String NAME_TAG = "name";
      private static final String ISSUE_COUNT = "count_of_issues";
      private static final String PUBLISHER = "publisher";
      private static final String IMAGE = "image";
      private static final String THUMB_URL = "thumb_url";

      @Override
      public List<ComicVineVolume> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        List<ComicVineVolume> result = new ArrayList<ComicVineVolume>();
        String JSONResponse = new BasicResponseHandler().handleResponse(response);

        try {
          JSONObject responseObject = (JSONObject) new JSONTokener(
              JSONResponse).nextValue();

          JSONArray results = responseObject.getJSONArray(RESULTS);

          for (int idx = 0; idx < results.length(); idx++) {
            JSONObject comic = (JSONObject)results.get(idx);
            JSONObject publisher = (JSONObject)comic.get(PUBLISHER);
            JSONObject image = (JSONObject)comic.get(IMAGE);

            String cv_name = comic.getString(NAME_TAG);
            String cv_issue_count = comic.getString(ISSUE_COUNT);
            String cv_publisher = publisher.getString(NAME_TAG);
            String cv_image_url = image.getString(THUMB_URL);

            ComicVineVolume cvv =  new ComicVineVolume(cv_name, cv_issue_count, cv_publisher, cv_image_url);

            result.add(cvv);
          }
        }
        catch (JSONException e1) {
          e1.printStackTrace();
        }
        return result;
      }
    }
  }

  private class ComicVineVolume {
    public String comicName;
    public String issueNum;
    public String publisher;
    public String imageSrc;

    ComicVineVolume(String comicName, String issueNum, String publisher, String imageSrc) {
      this.comicName = comicName;
      this.issueNum = issueNum;
      this.publisher = publisher;
      this.imageSrc = imageSrc;
    }
  }
}
