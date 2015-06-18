package com.example.app.project;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseUser;


public class UserDataActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        ParseUser user = ParseUser.getCurrentUser();

        TextView name = (TextView)findViewById(R.id.name);
        TextView email = (TextView)findViewById(R.id.email);
        TextView date = (TextView)findViewById(R.id.date);
        TextView height = (TextView)findViewById(R.id.height);
        TextView weight = (TextView)findViewById(R.id.weight);
        TextView bmi = (TextView)findViewById(R.id.bmi);


        name.setText(user.getString("name"));
        email.setText(user.getEmail());
        date.setText(user.getString("birthDate"));
        height.setText(user.getNumber("height").toString());
        weight.setText(user.getNumber("weight").toString());
        bmi.setText(user.getNumber("bmi").toString());


    }

    @Override

    public void onResume()
    {
        super.onResume();

        ParseUser user = ParseUser.getCurrentUser();

        TextView name = (TextView)findViewById(R.id.name);
        TextView height = (TextView)findViewById(R.id.height);
        TextView weight = (TextView)findViewById(R.id.weight);
        TextView bmi = (TextView)findViewById(R.id.bmi);


        name.setText(user.getString("name"));
        height.setText(user.getNumber("height").toString());
        weight.setText(user.getNumber("weight").toString());
        bmi.setText(user.getNumber("bmi").toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_data, menu);
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

    public void EditBtn(View v)
    {
        Intent edit = new Intent(this,EditUserActivity.class);
        startActivity(edit);
    }
}
