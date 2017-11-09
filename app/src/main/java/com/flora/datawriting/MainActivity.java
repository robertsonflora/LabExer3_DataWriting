package com.flora.datawriting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText etMessage, etFilename;
    SharedPreferences preferences;
    FileOutputStream fos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etMessage = (EditText) findViewById(R.id.etMessage);
        etFilename = (EditText) findViewById(R.id.etFilename);

        preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
    }

    public void saveInternalCache(View view){
        File folder = getCacheDir();
        File file =  new File(folder, etFilename.getText().toString());
        String message = etMessage.getText().toString();

        writeData(file, message);

        Toast.makeText(this, "Successfully written in Internal Cache!", Toast.LENGTH_LONG).show();
    }

    public void saveExternalCache(View view){
        File folder = getExternalCacheDir();
        File file =  new File(folder, etFilename.getText().toString());
        String message = etMessage.getText().toString();

        writeData(file, message);

        Toast.makeText(this, "Successfully written in External Cache!", Toast.LENGTH_LONG).show();
    }

    public void saveExternalStorage(View view){
        File folder = getExternalFilesDir("Temp");
        File file =  new File(folder, etFilename.getText().toString());
        String message = etMessage.getText().toString();

        writeData(file, message);

        Toast.makeText(this, "Successfully written in External Storage!", Toast.LENGTH_LONG).show();
    }

    public void saveExternalPubic(View view){
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file =  new File(folder, etFilename.getText().toString());
        String message = etMessage.getText().toString();

        writeData(file, message);

        Toast.makeText(this, "Successfully written in External Public Storage!", Toast.LENGTH_LONG).show();
    }

    public void savePreferences(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(etFilename.getText().toString(), etMessage.getText().toString());
        editor.commit();
        Toast.makeText(this, "Saved in Shared Preferences!", Toast.LENGTH_LONG).show();
    }

    public void saveInternalStorage (View view) {
        String message = etMessage.getText().toString();
        try{
            fos =  openFileOutput(etFilename.getText().toString(), Context.MODE_PRIVATE);
            fos.write(message.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(this, "Saved in Internal Storage!", Toast.LENGTH_LONG).show();
    }

    public void nextPage(View view){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
        finish();
    }

    public void writeData(File file, String message){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(message.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
