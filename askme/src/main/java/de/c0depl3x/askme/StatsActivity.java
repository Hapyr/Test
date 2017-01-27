package de.c0depl3x.askme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StatsActivity extends ReadingView implements SwipeRefreshLayout.OnRefreshListener {

    private TextView questionField, ansLeftField, ansRightField;
    private ProgressBar progress;
    private CommentAdapter adapter;
    private Question ques;
    private String author;
    public static ArrayList<Comment> allComments;
    private SwipeRefreshLayout refreshLayout;

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
        ques = MainActivity.allQuestions.get(qId);
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
        updateList();


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
    }

    @Override
    public void updateList() {
        ReadData data = new ReadData(this, DataType.Comment);
        data.execute("","");
    }

    @Override
    public void setRefreshing(boolean refresh) {
        refreshLayout.setRefreshing(refresh);
    }

    @Override
    public void fillListView(JSONObject commentData) throws ExecutionException, InterruptedException, JSONException {
        JSONArray result = commentData.getJSONArray("server_response");
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

        adapter.notifyDataSetChanged();
        setRefreshing(false);
    }

    public void vSendComment(View view){
        TextView comment = (TextView) findViewById(R.id.editText);
        ListView comcom = (ListView) findViewById(R.id.listdataComment);
        String com = comment.getText().toString();

        InsertData insert = new InsertData(this, DataType.Comment, UploadType.Insert);
        insert.execute(com, author, ""+(ques.getID()),"","","","","");

        comment.setText("");
    }
}
