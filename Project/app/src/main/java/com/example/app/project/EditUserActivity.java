package com.example.app.project;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseUser;


public class EditUserActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        ParseUser user = ParseUser.getCurrentUser();

        TextView name = (TextView) findViewById(R.id.name);
        TextView email = (TextView) findViewById(R.id.email);
        TextView date = (TextView) findViewById(R.id.date);
        TextView height = (TextView) findViewById(R.id.height);
        TextView weight = (TextView) findViewById(R.id.weight);
        TextView bmi = (TextView) findViewById(R.id.bmi);


        name.setText(user.getString("name"));
        email.setText(user.getEmail());
        date.setText(user.getString("birthDate"));
        height.setText(user.getNumber("height").toString());
        weight.setText(user.getNumber("weight").toString());
        bmi.setText(user.getNumber("bmi").toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_user, menu);
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

    public void SaveBtn(View v) {
        ParseUser user = ParseUser.getCurrentUser();

        Boolean isHeigetChange = false;
        Boolean isWeightChange = false;

        TextView name = (TextView) findViewById(R.id.name);
        TextView height = (TextView) findViewById(R.id.height);
        TextView weight = (TextView) findViewById(R.id.weight);

        String namePars =user.getString("name");
        String heightPars = user.getNumber("height").toString();
        String weightPars = user.getNumber("weight").toString();



        if (name.getText().toString().length() != 0 && namePars !=name.getText().toString()) {
            user.put("name", name.getText().toString());
        }

        if (height.getText().toString().length() != 0 && heightPars != height.getText().toString()) {
            user.put("height", Integer.valueOf(height.getText().toString()));
            isHeigetChange = true;
        }

        if (weight.getText().toString().length() != 0 && weightPars != weight.getText().toString()) {
            user.put("weight", Integer.valueOf(weight.getText().toString()));
            isWeightChange = true;
        }

        if (isHeigetChange && isWeightChange)
        {
            Double bmi = Double.valueOf(weight.getText().toString()) / (Math.sqrt(Double.valueOf(height.getText().toString())/ 10 ));
            user.put("bmi",bmi);

        }

        else if(isHeigetChange)
        {

            Double bmi = Double.valueOf(weightPars) / (Math.sqrt(Double.valueOf(height.getText().toString())/ 10 ));
            user.put("bmi",bmi);

        }

        else if (isWeightChange)
        {

            Double bmi = Double.valueOf(weight.getText().toString()) / (Math.sqrt(Double.valueOf(heightPars)/ 10 ));
            user.put("bmi",bmi);

        }

        finish();




    }

    public void CancelBtn(View v)
    {
        finish();
    }


}
