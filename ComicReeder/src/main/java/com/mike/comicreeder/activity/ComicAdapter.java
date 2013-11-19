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
import com.loopj.android.image.SmartImage;
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

      // This is how you obtain a reference to the TextViews.
      // These TextViews are created in the XML files we defined.
      TextView tt = (TextView) view.findViewById(R.id.toptext);
      TextView ttd = (TextView) view.findViewById(R.id.toptextdata);
      TextView mt = (TextView) view.findViewById(R.id.middletext);
      TextView mtd = (TextView) view.findViewById(R.id.middletextdata);
      TextView bt = (TextView) view.findViewById(R.id.bottomtext);
      TextView btd = (TextView) view.findViewById(R.id.desctext);
      SmartImageView smartImageView = (SmartImageView) view.findViewById(R.id.smallIcon);

      Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.empty);
      BitmapImage iconSmartImage = new BitmapImage(icon);


      // check to see if each individual textview is null.
      // if not, assign some text!
      if (tt != null){
        tt.setText("Comic: ");
      }
      if (ttd != null){
        ttd.setText(i.getComicName());
      }
      if (mt != null){
        mt.setText("Author: ");
      }
      if (mtd != null){
        mtd.setText(i.getWriter());
      }
      if (bt != null){
        bt.setText("Issue # ");
      }
      if (btd != null){
        btd.setText(String.valueOf(i.getIssueNumber()));
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

    // the view must be returned to our activity
    return view;
  }
}