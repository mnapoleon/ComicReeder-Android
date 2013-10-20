package com.mike.comicreeder.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by michaelnapoleon on 10/9/13.
 */
public class Comic implements Parcelable {

  private String _objectId;
  private String comicName;
  private String writer;
  private String publisher;
  private int issueNumber;

  public Comic(){};

  private Comic(Parcel in) {
    _objectId = in.readString();
    comicName = in.readString();
    writer = in.readString();
    publisher = in.readString();
    issueNumber = in.readInt();
  }

  public String getObjectId() {
    return _objectId;
  }

  public void setObjectId(String objectId) {
    this._objectId = objectId;
  }

  public int getIssueNumber() {
    return issueNumber;
  }

  public void setIssueNumber(int issueNumber) {
    this.issueNumber = issueNumber;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getWriter() {
    return writer;
  }

  public void setWriter(String writer) {
    this.writer = writer;
  }

  public String getComicName() {
    return comicName;
  }

  public void setComicName(String comicName) {
    this.comicName = comicName;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(_objectId);
    dest.writeString(comicName);
    dest.writeString(writer);
    dest.writeString(publisher);
    dest.writeInt(issueNumber);

  }

  // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
  public static final Parcelable.Creator<Comic> CREATOR = new Parcelable.Creator<Comic>() {
    public Comic createFromParcel(Parcel in) {
      return new Comic(in);
    }

    public Comic[] newArray(int size) {
      return new Comic[size];
    }
  };
}
