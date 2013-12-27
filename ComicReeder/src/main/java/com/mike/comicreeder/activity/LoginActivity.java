package com.mike.comicreeder.activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mike.comicreeder.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class LoginActivity extends RoboActivity {

  @InjectView(R.id.login_username) EditText mUsernameField;
  @InjectView(R.id.login_password) EditText mPasswordField;
  @InjectView(R.id.error_messages) TextView mErrorField;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.login, menu);
    return true;
  }

  public void signIn(final View view) {
    view.setEnabled(false);

    ParseUser.logInInBackground(mUsernameField.getText().toString(), mPasswordField.getText().toString(), new LogInCallback() {
      @Override
      public void done(ParseUser parseUser, ParseException e) {
        if (parseUser != null) {
          Intent intent = new Intent(LoginActivity.this, ComicReederActivity.class);
          startActivity(intent);
          finish();
        } else {
          // Signup failed. Look at the ParseException to see what happened.
          switch (e.getCode()) {
            case ParseException.USERNAME_TAKEN:
              mErrorField.setText(getString(R.string.username_taken));
              break;
            case ParseException.USERNAME_MISSING:
              mErrorField.setText(getString(R.string.username_missing));
              break;
            case ParseException.PASSWORD_MISSING:
              mErrorField.setText(getString(R.string.password_missing));
              break;
            case ParseException.OBJECT_NOT_FOUND:
              mErrorField.setText(getString(R.string.login_invalid));
              break;
            default:
              mErrorField.setText(e.getLocalizedMessage());
              break;
          }
          view.setEnabled(true);
        }
      }
    });
  }

  public void showRegistration(View view) {
    Intent intent = new Intent(this, RegistrationActivity.class);
    startActivity(intent);
    finish();;
  }
}
