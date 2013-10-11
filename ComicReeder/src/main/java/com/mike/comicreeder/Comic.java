package com.mike.comicreeder;

/**
 * Created by michaelnapoleon on 10/9/13.
 */
public class Comic {

  private String comicName;
  private String author;
  private String issueNumber;
  private String publisher;

  public String getIssueNumber() {
    return issueNumber;
  }

  public void setIssueNumber(String issueNumber) {
    this.issueNumber = issueNumber;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getComicName() {
    return comicName;
  }

  public void setComicName(String comicName) {
    this.comicName = comicName;
  }
}
