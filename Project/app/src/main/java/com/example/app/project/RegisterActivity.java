package com.example.app.project;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        RegisterCheck();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void finishBtn(View v) {

    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private void RegisterCheck() {
        final EditText email = (EditText) findViewById(R.id.eMail);
        email.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (!isEmailValid(s.toString())) {
                    email.setTextColor(Color.RED);
                } else {
                    email.setTextColor(Color.BLACK);
                }


            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });


         EditText pass2 = (EditText) findViewById(R.id.pass2);

        pass2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {}
                else {
                    if (!PassMatch()) {
                        Toast.makeText(getApplicationContext(), "The Password are not match", Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
//
    }

    private boolean PassMatch() {
        final EditText pass1 = (EditText) findViewById(R.id.pass1);

        final EditText pass2 = (EditText) findViewById(R.id.pass2);
        if (pass1.getText().toString().equals(pass2.getText().toString()) && pass1.getText().toString().length() !=0) {
            return true;

        }
        return false;

    }

}
