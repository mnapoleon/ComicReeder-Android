package com.mike.comicreeder.remote;

import com.mike.comicreeder.model.Comic;
import com.mike.comicreeder.model.ParseComics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by michaelnapoleon on 11/1/13.
 */
public class ComicReederSearchTask implements ParseComics {

  public List<ParseObject> searchForComics(Map<String, String> searchParams) {
    List<ParseObject> queryResults = null;
    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(CLASS_COMICS);
    for (String key : searchParams.keySet()) {
      query.whereEqualTo(key, searchParams.get(key));
    }
    query.orderByAscending(COLUMN_COMIC_NAME);
    query.addAscendingOrder(COLUMN_ISSUE);
    try {
      queryResults = query.find();
    } catch (ParseException e) {

    }
    return queryResults;
  }

  public ArrayList<Comic> getComicList(List<ParseObject> results) {
    ArrayList<Comic> comicList = new ArrayList<Comic>();
    for (ParseObject result : results) {
      Comic comic = new Comic();
      comic.setObjectId(result.getObjectId());
      comic.setWriter(result.getString(COLUMN_WRITER));
      comic.setComicName(result.getString(COLUMN_COMIC_NAME));
      comic.setIssueNumber(result.getInt(COLUMN_ISSUE));
      comic.setPublisher(result.getString(COLUMN_PUBLISHER));
      comic.setSmallIconUrl(result.getString(COLUMN_SMALL_ICON_URL));
      comic.setCoverImageUrl(result.getString(COLUMN_COVER_IMAGE_URL));
      comicList.add(comic);
    }
    return comicList;
  }

}
