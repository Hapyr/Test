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
    Vector allQuestions = new Vector();

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
        insert.execute(ques,ans1,ans2);
    }

    public void vGetData(View view)
    {
        ReadData data = new ReadData(new AsyncResponse() {
            @Override
            public void processFinish(String output) throws JSONException {
                // --- After finish the execute this Mothode will called ---
                TextView test = (TextView) findViewById(R.id.textView);
                //test.setText(output);

                JSONObject jObject  = new JSONObject(output);
                String gesammt = "";

                try {
                    JSONArray result = jObject.getJSONArray("server_response");
                    String ques,ans1,ans2;

                    for(int i=0;i<result.length();i++)
                    {
                        ques = result.getJSONObject(i).getString("ques");
                        ans1 = result.getJSONObject(i).getString("ans_1");
                        ans2 = result.getJSONObject(i).getString("ans_2");

                        allQuestions.add(new Question(ques,ans1,ans2));

                        // --- test ausgabe eines elementes aus allQuestions ---
                        // ((Question) (allQuestions.elementAt(i))).displayQuestion();
                        gesammt += " -> " + ques + " - " + ans1 + " - " + ans2 + "\n";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                test.setText(gesammt);
            }
        });
        data.execute();
    }
    /// ------------------------------------------------------

}
