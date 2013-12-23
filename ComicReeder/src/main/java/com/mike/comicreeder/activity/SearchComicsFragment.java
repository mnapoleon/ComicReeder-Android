package com.mike.comicreeder.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mike.comicreeder.R;
import com.mike.comicreeder.components.FloatingLabelEditText;
import com.mike.comicreeder.model.Comic;
import com.mike.comicreeder.model.ParseComics;
import com.mike.comicreeder.remote.ComicReederSearchTask;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.fragment.RoboFragment;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by mnapoleo on 12/23/13.
 */
@ContentView(R.layout.fragement_search_comics)
public class SearchComicsFragment extends RoboFragment implements ParseComics {

  @InjectView(R.id.comicNameSearch) FloatingLabelEditText mComicName;
  @InjectView(R.id.publisherSearch) FloatingLabelEditText mPublisherName;
  @InjectView(R.id.writerSearch)    FloatingLabelEditText mWriterName;
  @InjectView(R.id.search_button)   Button mSearchComicButton;

  private class RemoteSearchTask extends AsyncTask<Map<String, String>, Void, List<ParseObject>> {

    private ComicReederSearchTask searchTask = new ComicReederSearchTask();

    @Override
    protected List<ParseObject> doInBackground(Map<String, String>... params) {
      return searchTask.searchForComics(params[0]);
    }

    @Override
    protected void onPostExecute(List<ParseObject> result) {
      ArrayList<Comic> comicList = searchTask.getComicList(result);

      Intent intent = new Intent(getActivity(), ComicSearchListActivity.class);
      intent.putParcelableArrayListExtra("comicData", comicList);

      startActivity(intent);
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    super.onCreateView(inflater, parent, savedInstanceState);

    View view = inflater.inflate(R.layout.fragement_search_comics, parent, false);

    return view;
  }

  /**
   * Used this because the views aren't injected until after this call.  Needed it to
   * get views.
   * @param view
   * @param savedInstanceState
   */
  @Override
  public void onViewCreated (View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    setSearchComicButtonClickListerner();
  }

  protected void setSearchComicButtonClickListerner () {
    View.OnClickListener searchComicButtonListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Map<String, String> searchParams = new HashMap<String, String>();
        if (mComicName.getTextFieldValue() != null && mComicName.getTextFieldValue().toString().trim().length() > 0) {
          searchParams.put(COLUMN_COMIC_NAME, mComicName.getTextField().getText().toString().trim());
        }
        if (mWriterName.getTextFieldValue()!= null && mWriterName.getTextFieldValue().toString().trim().length() > 0) {
          searchParams.put(COLUMN_WRITER, mWriterName.getTextField().getText().toString().trim());
        }
        if (mPublisherName.getTextFieldValue()!= null && mPublisherName.getTextFieldValue().toString().trim().length() > 0) {
          searchParams.put(COLUMN_PUBLISHER, mPublisherName.getTextField().getText().toString().trim());
        }
        new RemoteSearchTask().execute(searchParams);
      }
    };
    mSearchComicButton.setOnClickListener(searchComicButtonListener);
  }

}
