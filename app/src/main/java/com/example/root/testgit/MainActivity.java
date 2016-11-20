package com.example.root.testgit;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    Button B1,B2;
    TextView question,answer1, answer2;
    String ques,ans1,ans2;

int k = 1200;
    int a = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        B1 = (Button) findViewById(R.id.button);
        B2 = (Button) findViewById(R.id.button2);
        question = (TextView) findViewById(R.id.question);
        answer1 = (TextView) findViewById(R.id.editText2);
        answer2 = (TextView) findViewById(R.id.editText3);
    }

    public void test() {
        int i = 1;
        int as = 0;
    }

    public void vInsertData(View view)
    {
        ques = question.getText().toString();
        ans1 = answer1.getText().toString();
        ans2 = answer2.getText().toString();

        BackgroundTask background = new BackgroundTask();
        background.execute(ques,ans1,ans2);
    }
}
