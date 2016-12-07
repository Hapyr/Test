package com.example.root.testgit;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.R.attr.author;

public class Stats extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private TextView questionField, ansLeftField, ansRightField;
    private ProgressBar progress;
    private CommentAdapter adapter;
    private Question ques;
    private String author;
    public static ArrayList<Comment> allComments;
    private SwipeRefreshLayout refreshLayout;

    public interface AsyncResponse {
        void processFinish(String output) throws JSONException;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        View QuestionsFrame = ((View) findViewById(R.id.quess));

        questionField = (TextView) QuestionsFrame.findViewById(R.id.qQuestion);
        ansLeftField = (TextView) QuestionsFrame.findViewById(R.id.qAnsLeft);
        ansRightField = (TextView) QuestionsFrame.findViewById(R.id.qAnsRight);

        Intent intent = getIntent();
        int qId = intent.getIntExtra(MainActivity.EXTRA_QUESTION_ID, 2);
        ques = (Question) MainActivity.allQuestions.get(qId);
        author = ques.getAuthorID();
        ((TextView) QuestionsFrame.findViewById(R.id.qAnsRight)).setText(ques.getAnswer2());
        ((TextView) QuestionsFrame.findViewById(R.id.qAnsLeft)).setText(ques.getAnswer1());
        ((TextView) QuestionsFrame.findViewById(R.id.qQuestion)).setText(ques.getQuestion());

        ((ProgressBar) findViewById(R.id.progressBar)).setProgress((int)(100*((float)(ques.getPro()))/((float)(ques.getPro() + ques.getContra()))));


        allComments = new ArrayList<Comment>();
        final ListView listView = (ListView) findViewById(R.id.listdataComment);
        adapter = new CommentAdapter(this, allComments);
        listView.setAdapter(adapter);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshWrapperStats);
        refreshLayout.setOnRefreshListener(this);
        // Refresh on create
        refreshLayout.post(new Runnable() {
            @Override
            public void run() { refreshLayout.setRefreshing(true); updateList(); }
        });


        // -----------------------------------------------------------------|
        // ----------- Button Votes ----------------------------------------|
        // -----------------------------------------------------------------.
        Button proVote = (Button) QuestionsFrame.findViewById(R.id.button5);
        proVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ques.setVote(true);
            }});
        Button contraVote = (Button) QuestionsFrame.findViewById(R.id.button4);
        contraVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ques.setVote(false);
            }});
        // -----------------------------------------------------------------.
        // -----------------------------------------------------------------|

    }

    @Override
    public void onRefresh() {
        updateList();
        refreshLayout.setRefreshing(false);
    }

    private void updateList() {
        try {
            FillCommentArrayList();
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


    public void FillCommentArrayList() throws ExecutionException, InterruptedException, JSONException {
        // --- LOAD JSON STRING FROM SERVER and CONVERT to QUESTION VECTOR ---
        // --- STRING SAVED on 'gesamt' --- QUESTION VEC on 'allQuestion' ---
        JSONObject CommentData;
        CommentData = new JSONObject(  vGetData(this.findViewById(R.id.listdataComment).getRootView()) );
        JSONArray result = CommentData.getJSONArray("server_response");
        String text, author, id;
        int time;

        allComments.clear();   // clear question list to not add same questions twice

        for(int i=0;i<result.length();i++) {
            text = result.getJSONObject(i).getString("com");
            time = Integer.parseInt(result.getJSONObject(i).getString("time"));
            author = result.getJSONObject(i).getString("author");
            id = result.getJSONObject(i).getString("id");

            allComments.add(new Comment(text, author, id, time));
        }
    }

    public String vGetData(View view) throws ExecutionException, InterruptedException {
        ReadData data = new ReadData(new MainActivity.AsyncResponse() {
            @Override
            public void processFinish(String output) throws JSONException {
                // --- After finish the execute this method will called ---
            }
        }, this);
        data.setDatatype(DataType.Comment);
        return data.execute("" + ques.getAuthorID(), "" + ques.getID()).get();
    }

    public void vSendComment(View view){
        TextView comment = (TextView) findViewById(R.id.editText);
        String com = comment.getText().toString();

        InsertData insert = new InsertData(new EditActivity.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                // --- After finish the execute this Mothode will called ---
                Toast.makeText(Stats.this, "Kommentar gespeichert!", Toast.LENGTH_LONG).show();
            }
        });
        insert.setDataType(DataType.Comment);
        insert.setUploadType(UploadType.Insert);
        insert.execute(com, author, ""+(ques.getID()),"","","","","");
        this.finish();
    }
}
