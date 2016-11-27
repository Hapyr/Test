package com.example.root.testgit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Stats extends AppCompatActivity {

    private TextView questionField, ansLeftField, ansRightField;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        questionField = (TextView) findViewById(R.id.statsQuestion);
        ansLeftField = (TextView) findViewById(R.id.statsLeft);
        ansRightField = (TextView) findViewById(R.id.statsRight);

        Intent intent = getIntent();
        int qId = intent.getIntExtra(MainActivity.EXTRA_QUESTION_ID, 2);
        questionField.setText(Integer.toString(qId));

    }
}
