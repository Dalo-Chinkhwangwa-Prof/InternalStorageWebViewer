package com.britishbroadcast.storage_internal_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private String fileName = "MyPage.html";

    @BindView(R.id.main_textview)
    public WebView mainWebView;

    @BindView(R.id.main_button)
    public Button mainButton;

    @BindView(R.id.main_edittext)
    public EditText mainEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Initially read from file
        try {
            readFromInternal();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveToInternalStorage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveToInternalStorage() throws IOException {
        FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
        String value = mainEditText.getText().toString().trim();
        mainEditText.setText("");

        outputStream.write((value + "\n").getBytes());
        outputStream.close();

        //Read from internal storage after reading
        readFromInternal();
    }

    private void readFromInternal() throws IOException {
        mainWebView.loadUrl("file://"+getFilesDir()+"/"+fileName);
//        FileReader fileReader = new FileReader(getFilesDir()+"/"+fileName);
//        BufferedReader bufferedReader = new BufferedReader(fileReader);
//
//        StringBuilder stringBuilder = new StringBuilder();
//        String input = null;
//
//        while( (input = bufferedReader.readLine()) != null ){
//            stringBuilder.append(input).append("\n");
//        }
//
//        mainTextView.setText(stringBuilder.toString());
    }

}