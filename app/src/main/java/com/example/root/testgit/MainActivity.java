package com.example.root.testgit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{

    Button B1,B2;
    TextView question,answer1, answer2;
    String ques,ans1,ans2;

    public interface AsyncResponse {
        void processFinish(String output);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        B1 = (Button) findViewById(R.id.button);
        B2 = (Button) findViewById(R.id.button2);
        question = (TextView) findViewById(R.id.question);
        answer1 = (TextView) findViewById(R.id.editText2);
        answer2 = (TextView) findViewById(R.id.editText3);

        // --- Question Class test ---
        Question ques = new Question("Wie alt bin ich?","12","21");
        ques.displayQuestion();
    }

    /// ------------------------------------------------------
    /// --------- LOAD and SEND DATA to SERVER ---------------
    /// ------------------------------------------------------
    public void vInsertData(View view)
    {
        ques = question.getText().toString();
        ans1 = answer1.getText().toString();
        ans2 = answer2.getText().toString();

        InsertData insert = new InsertData(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                // --- After finish the execute this Mothode will called ---
                Toast.makeText(MainActivity.this, "New data saved...", Toast.LENGTH_SHORT).show();
            }
        });
        insert.execute(ques,ans1,ans2);
    }

    public void vGetData(View view)
    {
        ReadData data = new ReadData(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                // --- After finish the execute this Mothode will called ---
                question.setText(output);
            }
        });
        data.execute();
    }
    /// ------------------------------------------------------

}
