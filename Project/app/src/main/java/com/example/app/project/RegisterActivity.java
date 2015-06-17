package com.example.app.project;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

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
        EditText email = (EditText) findViewById(R.id.eMail);
        EditText pass = (EditText) findViewById(R.id.pass1);
        EditText fName = (EditText) findViewById(R.id.fName);
        EditText lName = (EditText) findViewById(R.id.lName);
        EditText height = (EditText) findViewById(R.id.height);
        EditText weight = (EditText) findViewById(R.id.weight);
        RadioButton gender  = (RadioButton)findViewById(((RadioGroup)findViewById(R.id.gender)).getCheckedRadioButtonId());

        if (!IsEmailValid(email.getText().toString()))
        {
            email.requestFocus();
            return;
        }
        if (!PassMatch())
        {
            pass.requestFocus();
            return;
        }
        if(fName.getText().toString().length() == 0)
        {
            fName.requestFocus();
            return;
        }
        if(lName.getText().toString().length() == 0)
        {
            lName.requestFocus();
            return;
        }

        if(height.getText().toString().length() == 0)
        {
            height.requestFocus();
            return;
        }
        if(weight.getText().toString().length() == 0)
        {
            weight.requestFocus();
            return;
        }

        Double bmi = Double.valueOf(weight.getText().toString()) /(Math.sqrt(Double.valueOf(height.getText().toString())/10));



        ParseUser user = new ParseUser();
        user.setUsername(email.getText().toString());
        user.setPassword(pass.getText().toString());
        user.setEmail(email.getText().toString());

// other fields can be set just like with ParseObject
        user.put("name", fName.getText().toString()+" " +lName.getText().toString());
        user.put("birthDate", fName.getText().toString());
        user.put("gender", gender.getText().toString());
        user.put("height", Integer.valueOf(height.getText().toString()));
        user.put("weight", Integer.valueOf(weight.getText().toString()));
        user.put("bMI", bmi);

        final Intent mainIntent = new Intent(this, MainActivity.class);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                   startActivity(mainIntent);
                } else {
                    Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });




    }

    public static boolean IsEmailValid(String email) {
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
                if (!IsEmailValid(s.toString())) {
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

    public boolean PassMatch() {
        final EditText pass1 = (EditText) findViewById(R.id.pass1);

        final EditText pass2 = (EditText) findViewById(R.id.pass2);
        if (pass1.getText().toString().equals(pass2.getText().toString()) && pass1.getText().toString().length() !=0) {
            return true;

        }
        return false;

    }



}
