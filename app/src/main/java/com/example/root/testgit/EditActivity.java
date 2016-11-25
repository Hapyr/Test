package com.example.root.testgit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class EditActivity extends AppCompatActivity {

    Button B1,B2;
    TextView question,answer1, answer2;

    public interface AsyncResponse {
        void processFinish(String output) throws JSONException;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        B1 = (Button) findViewById(R.id.button);
        B2 = (Button) findViewById(R.id.button2);
        question = (TextView) findViewById(R.id.question);
        answer1 = (TextView) findViewById(R.id.editText2);
        answer2 = (TextView) findViewById(R.id.editText3);
    }

    /// ------------------------------------------------------
    /// --------- LOAD and SEND DATA to SERVER ---------------
    /// ------------------------------------------------------
    public void vInsertData(View view)
    {
        String ques = question.getText().toString();
        String ans1 = answer1.getText().toString();
        String ans2 = answer2.getText().toString();

        InsertData insert = new InsertData(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                // --- After finish the execute this Mothode will called ---
                Toast.makeText(EditActivity.this, "new data saved", Toast.LENGTH_LONG).show();
            }
        });
        insert.execute(ques, ans1, ans2);
        this.finish();
    }
    /// ------------------------------------------------------

}
