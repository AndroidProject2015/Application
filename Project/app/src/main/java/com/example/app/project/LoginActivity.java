package com.example.app.project;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app.project.model.ParseModel;
import com.example.app.project.model.User;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ParseModel.getInstance().init(getApplicationContext());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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


    public  void LogInBtn(View e)
    {
        String email = ((EditText)findViewById(R.id.eMail)).getText().toString();
        String pass = ((EditText)findViewById(R.id.password)).getText().toString();

        TextView  err= (TextView)findViewById(R.id.errorText);


        String msg = ParseModel.login(email,pass);
//        if (msg == "Success")
//        {
//            err.setText(msg);
//
//        }
//
//        else
//        {
//            err.setText(msg);
//        }
        Intent mainIntent = new Intent(this,MainActivity.class);
        startActivity(mainIntent);
        

    }
}
