package de.c0depl3x.askme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends ReadingView implements SwipeRefreshLayout.OnRefreshListener {

    public String andID = "";
    public String countID = "";
    public static ArrayList<Question> allQuestions;
    private SwipeRefreshLayout refreshLayout;
    private QuestionAdapter adapter;
    public static String EXTRA_QUESTION_ID = "de.c0depl3x.askme.qID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar actionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(actionBar);

        SQLiteDatabase mydatabase = openOrCreateDatabase("user",MODE_PRIVATE,null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS info(author_id TEXT,id TEXT);");

        Cursor resultID = mydatabase.rawQuery("SELECT count(author_id) FROM info;",null);
        resultID.moveToFirst();
        countID = resultID.getString(0);

        String androidId = md5("" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID));

        andID = androidId;

        if(Integer.parseInt(countID) == 0) {
            mydatabase.execSQL("INSERT INTO info (author_id,id) VALUES('" + andID + "','0');");
        }

        allQuestions = new ArrayList<Question>();
        final ListView listView = (ListView) findViewById(R.id.listdata);
        adapter = new QuestionAdapter(this, allQuestions);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long j) {
                Question qClicked = (Question) listView.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, StatsActivity.class);
                intent.putExtra(EXTRA_QUESTION_ID, position);
                startActivity(intent);
            }
        });

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshWrapper);
        refreshLayout.setOnRefreshListener(this);
        updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.btn_profile:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
        }

        return true;
    }

    @Override
    public void setRefreshing(boolean refresh) {
        refreshLayout.setRefreshing(refresh);
    }

    void vote(boolean value) {
        if(value == true){
            // --- left vote ---
        }else{
            // --- right vote ---
        }
    }

    @Override
    public void updateList() {
        ReadData data = new ReadData(this, DataType.Question);
        data.execute("","");
    }

    @Override
    public void fillListView(JSONObject QuesData) throws ExecutionException, InterruptedException, JSONException {
        JSONArray result = QuesData.getJSONArray("server_response");
        String ques, ans1, ans2, pro, contra, weight, authorid, id, time;

        allQuestions.clear();   // clear question list to not add same questions twice

        for(int i=0;i<result.length();i++) {
            ques = result.getJSONObject(i).getString("ques");
            ans1 = result.getJSONObject(i).getString("ans_1");
            ans2 = result.getJSONObject(i).getString("ans_2");
            pro = result.getJSONObject(i).getString("pro");
            contra = result.getJSONObject(i).getString("contra");
            weight = result.getJSONObject(i).getString("weight");
            time = result.getJSONObject(i).getString("time");
            authorid = result.getJSONObject(i).getString("auhor_id");
            id = result.getJSONObject(i).getString("id");

            allQuestions.add(new Question(ques, ans1, ans2, Integer.parseInt(pro),Integer.parseInt(contra),Integer.parseInt(weight),Integer.parseInt(time),authorid,Integer.parseInt(id)));

            // --- test ausgabe eines elementes aus allQuestions ---
            //((Question) (allQuestions.elementAt(i))).displayQuestion();
        }

        setRefreshing(false);
        adapter.notifyDataSetChanged();
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onRefresh() {
        updateList();
    }

    public void vSwitchToEdit(View view) {

        Intent showCreate = new Intent(this, EditActivity.class);
        startActivity(showCreate);
    }
}