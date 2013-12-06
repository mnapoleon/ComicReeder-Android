package com.mike.comicreeder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mike.comicreeder.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class RegistrationActivity extends RoboActivity {

  @InjectView(R.id.register_username) EditText mUsernameField;
  @InjectView(R.id.register_password) EditText mPasswordField;
  @InjectView(R.id.error_messages) TextView mErrorField;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registration);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.registration, menu);
    return true;
  }

  public void register(final View view) {
    if (mUsernameField.getText().length() == 0 ||
        mPasswordField.getText().length() == 0) return;

    view.setEnabled(false);

    ParseUser parseUser = new ParseUser();
    parseUser.setUsername(mUsernameField.getText().toString());
    parseUser.setPassword(mPasswordField.getText().toString());
    mErrorField.setText("");

    parseUser.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {
        if (e == null) {
          Intent intent = new Intent(RegistrationActivity.this, ComicReederActivity.class);
          startActivity(intent);
          finish();
        }
        else {
          // Sign up didn't succeed. Look at the ParseException
          // to figure out what went wrong
          switch(e.getCode()){
            case ParseException.USERNAME_TAKEN:
              mErrorField.setText("Sorry, this username has already been taken.");
              break;
            case ParseException.USERNAME_MISSING:
              mErrorField.setText("Sorry, you must supply a username to register.");
              break;
            case ParseException.PASSWORD_MISSING:
              mErrorField.setText("Sorry, you must supply a password to register.");
              break;
            default:
              mErrorField.setText(e.getLocalizedMessage());
          }
          view.setEnabled(true);
        }
      }
    });
  }

  public void showLogin(View view) {
    Intent intent = new Intent(this, LoginActivity.class);
    startActivity(intent);
    finish();
  }
}
