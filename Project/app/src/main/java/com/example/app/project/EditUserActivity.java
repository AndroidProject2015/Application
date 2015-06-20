package com.example.app.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;


public class EditUserActivity extends ActionBarActivity {

    ImageButton pic;
    boolean isProfilePicChanged;

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

        pic = (ImageButton) findViewById(R.id.profilePic);
        isProfilePicChanged = false;

        name.setText(user.getString("name"));
        email.setText(user.getEmail());
        date.setText(user.getString("birthDate"));
        height.setText(user.getNumber("height").toString());
        weight.setText(user.getNumber("weight").toString());
        bmi.setText(user.getNumber("bmi").toString());

        try {

            byte[] bytes = user.getParseFile("profilePicture").getData();
            Bitmap b = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            pic.setImageDrawable( new BitmapDrawable(getResources(),b));

        } catch (ParseException e) {
            e.printStackTrace();
        }


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

        if(isProfilePicChanged)
        {
            Drawable d = ((ImageButton)findViewById(R.id.profilePic)).getDrawable();

            Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();

            ParseFile imageFile = new ParseFile("image.png", bitmapdata);

            user.put("profilePicture" , imageFile);


        }




        user.saveInBackground();

        finish();




    }

    public void CancelBtn(View v)
    {
        finish();
    }


    public void profilePic(View v)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a profile picture"), 1);

    }

    public void onActivityResult( int reqCode, int resCode , Intent intent)
    {
        if (resCode == RESULT_OK)
        {
            if (reqCode == 1)
            {
                pic.setImageURI(intent.getData());
                isProfilePicChanged = true;


            }
        }
    }


}
