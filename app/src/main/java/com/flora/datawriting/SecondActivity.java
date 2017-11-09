package com.flora.datawriting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    EditText tvMessage, filename;
    FileInputStream fis;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvMessage = (EditText)findViewById(R.id.etMessage);
        filename = (EditText) findViewById(R.id.filename);
        preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
    }

    public void displayPreferences(View view){
        String message = preferences.getString(filename.getText().toString(), "");

        tvMessage.setText(message);
        Toast.makeText(this, "Successfully loaded from Shared Preferences!", Toast.LENGTH_LONG).show();
    }

    public void displayStorage (View view){
        StringBuffer buffer = new StringBuffer();
        int read = 0;

        try{
            fis = openFileInput(filename.getText().toString());
            while((read = fis.read()) != -1){
                buffer.append((char)read);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tvMessage.setText(buffer.toString());
        Toast.makeText(this, "Successfully loaded from Internal Storage!", Toast.LENGTH_LONG).show();
    }

    public void loadInternalCache(View view){
        File folder = getCacheDir();
        File file = new File(folder, filename.getText().toString());

        readData(file);

        Toast.makeText(this, "Successfully loaded from Internal Cache!", Toast.LENGTH_LONG).show();

    }

    public void loadExternalCache(View view){
        File folder = getExternalCacheDir();
        File file = new File(folder, filename.getText().toString());

        readData(file);

        Toast.makeText(this, "Successfully loaded from External Cache!", Toast.LENGTH_LONG).show();
    }

    public void loadExternalStorage(View view){
        File folder = getExternalFilesDir("temp");
        File file = new File(folder, filename.getText().toString());

        readData(file);

        Toast.makeText(this, "Successfully loaded from External Storage!", Toast.LENGTH_LONG).show();
    }

    public void loadExternalPublicStorage(View view){
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(folder, filename.getText().toString());

        readData(file);

        Toast.makeText(this, "Successfully loaded from External Public Storage!", Toast.LENGTH_LONG).show();
    }

    public void previous(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void readData(File file) {
        StringBuffer buffer = new StringBuffer();
        int read = 0;

        try{
            fis = new FileInputStream(file);
            while((read = fis.read()) != -1){
                buffer.append((char)read);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        tvMessage.setText(buffer.toString());
    }

}
