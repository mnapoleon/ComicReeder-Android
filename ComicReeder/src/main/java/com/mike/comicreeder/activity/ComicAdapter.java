package com.mike.comicreeder.activity;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.loopj.android.image.BitmapImage;
import com.loopj.android.image.SmartImageView;
import com.mike.comicreeder.R;
import com.mike.comicreeder.model.Comic;

import java.util.List;

/**
 * Created by michaelnapoleon on 10/11/13.
 */
public class ComicAdapter extends ArrayAdapter<Comic> {

  private List<Comic> comics;

  public ComicAdapter(Context context, int textViewResourceId, List<Comic> comics) {
    super(context, textViewResourceId, comics);
    this.comics = comics;
  }

  public View getView(int position, View convertView, ViewGroup parent) {

    View view = convertView;

    if (view == null) {
      LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      view = inflater.inflate(R.layout.comic_list_item, null);
    }

    Comic i = comics.get(position);

    if (i != null) {
      TextView comicNameTV = (TextView) view.findViewById(R.id.result_comic_name);
      TextView writerTV = (TextView) view.findViewById(R.id.result_writer);
      TextView issueNumberTV = (TextView) view.findViewById(R.id.result_issue_number);
      SmartImageView smartImageView = (SmartImageView) view.findViewById(R.id.smallIcon);

      Context context = getContext();

      Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.empty);
      BitmapImage iconSmartImage = new BitmapImage(icon);

      if (comicNameTV != null){
        comicNameTV.setText(getStrings(context, R.string.result_comic_name_label) + " " + i.getComicName());
      }
      if (writerTV != null){
        writerTV.setText(getStrings(context, R.string.result_writer_label) + " " + i.getWriter());
      }
      if (issueNumberTV != null){
        issueNumberTV.setText(getStrings(context, R.string.result_issue_num_label) + " " + String.valueOf(i.getIssueNumber()));
      }
      if (smartImageView != null) {
        if (i.getSmallIconUrl() != null) {
          smartImageView.setImageUrl(i.getSmallIconUrl());
        }
        else {
          smartImageView.setImage(iconSmartImage);
        }
      }
    }
    return view;
  }

  /**
   * Gets string value from resources
   * @param context
   * @param RString
   * @return
   */
  String getStrings(Context context, int RString) {
    return context.getResources().getString(RString);
  }
}