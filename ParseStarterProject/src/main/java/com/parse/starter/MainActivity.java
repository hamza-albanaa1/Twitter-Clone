/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity  {


    public void redirectUser(){
        if(ParseUser.getCurrentUser() !=null){
            Intent intent = new Intent(getApplicationContext(),UsersActivity.class);
            startActivity(intent);
        }
    }






  public void signUpLogin (View view){
      final EditText usernameeditText = (EditText)findViewById(R.id.usernameeditText);
      final EditText Passwordedittext = (EditText) findViewById(R.id.PasswordeditText);
//for login
    ParseUser.logInInBackground(usernameeditText.getText().toString(),
            Passwordedittext.getText().toString(), new LogInCallback() {
              @Override
              public void done(ParseUser user, ParseException e) {
                if(user != null) {
                  Log.i("Login", "Ok!  ");
                    redirectUser();
                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
//for sing in
                }else{
                    ParseUser newuser = new ParseUser();
                    newuser.setUsername(usernameeditText.getText().toString());
                    newuser.setPassword(Passwordedittext.getText().toString());

                  newuser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                      if (e == null) {
                        Log.i("sign up ", " Success");
                          redirectUser();
                      } else {
                        Toast.makeText(MainActivity.this,
                                e.getMessage().substring(e.getMessage().indexOf(" ")),
                                Toast.LENGTH_SHORT).show();
                      }
                    }
                  });
                }
              }
            });
  }







  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setTitle("Twitter");
      redirectUser();

    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }


}