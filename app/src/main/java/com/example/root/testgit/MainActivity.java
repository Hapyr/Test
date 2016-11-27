package com.example.root.testgit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<Question> allQuestions;
    private SwipeRefreshLayout refreshLayout;
    private QuestionAdapter adapter;
    public static String EXTRA_QUESTION_ID = "com.example.root.testgit.qID";

    public interface AsyncResponse {
        void processFinish(String output) throws JSONException;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allQuestions = new ArrayList<Question>();
        final ListView listView = (ListView) findViewById(R.id.listdata);
        adapter = new QuestionAdapter(this, allQuestions);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long j) {
                Question qClicked = (Question) listView.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, Stats.class);
                intent.putExtra(EXTRA_QUESTION_ID, qClicked.getId());
                startActivity(intent);
            }
        });

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshWrapper);
        refreshLayout.setOnRefreshListener(this);
        // Refresh on create
        refreshLayout.post(new Runnable() {
            @Override
            public void run() { updateList(); }
        });
    }

    @Override
    public void onRefresh() {
        updateList();
        refreshLayout.setRefreshing(false);
    }

    private void updateList() {
        try {
            FillQuestionArrayList();
            adapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "Erfolgreich aktualisiert", Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (InterruptedException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void vSwitchToEdit(View view) {

        Intent showCreate = new Intent(this, EditActivity.class);
        startActivity(showCreate);
    }

    // --- with vGetData - LOAD ALL DATA from SERVER and SAVE in 'allQuestions' ---
    public void FillQuestionArrayList() throws ExecutionException, InterruptedException, JSONException {
        // --- LOAD JSON STRING FROM SERVER and CONVERT to QUESTION VECTOR ---
        // --- STRING SAVED on 'gesamt' --- QUESTION VEC on 'allQuestion' ---

        JSONObject QuesData;

        QuesData = new JSONObject(  vGetData(this.findViewById(R.id.activity_main).getRootView()) );
        JSONArray result = QuesData.getJSONArray("server_response");
        String ques, ans1, ans2;

        allQuestions.clear();   // clear question list to not add same questions twice

        for(int i=0;i<result.length();i++) {
            ques = result.getJSONObject(i).getString("ques");
            ans1 = result.getJSONObject(i).getString("ans_1");
            ans2 = result.getJSONObject(i).getString("ans_2");

            allQuestions.add(new Question(ques, ans1, ans2));

            // --- test ausgabe eines elementes aus allQuestions ---
            //((Question) (allQuestions.elementAt(i))).displayQuestion();
        }
    }

    public String vGetData(View view) throws ExecutionException, InterruptedException {
        ReadData data = new ReadData(new AsyncResponse() {
            @Override
            public void processFinish(String output) throws JSONException {
                // --- After finish the execute this method will called ---
            }
        });
        return data.execute().get();
    }
}