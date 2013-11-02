package com.mike.comicreeder.remote;

import com.mike.comicreeder.model.Comic;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by michaelnapoleon on 11/1/13.
 */
public class ComicReederSearchTask {


  public List<ParseObject> searchForComics(Map<String, String> searchParams) {
    List<ParseObject> queryResults = null;
    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Comics");
    for (String key : searchParams.keySet()) {
      query.whereEqualTo(key, searchParams.get(key));
    }
    query.orderByAscending("comicName");
    query.addAscendingOrder("issue");
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
      comic.setWriter(result.getString("writer"));
      comic.setComicName(result.getString("comicName"));
      comic.setIssueNumber(result.getInt("issue"));
      comic.setPublisher(result.getString("publisher"));
      comicList.add(comic);
    }
    return comicList;
  }

}
