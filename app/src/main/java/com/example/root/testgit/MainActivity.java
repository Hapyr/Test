package com.example.root.testgit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity{

    ArrayList<Question> allQuestions = new ArrayList<Question>();
    public interface AsyncResponse {
        void processFinish(String output) throws JSONException;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FillQuestionArrayList();
        FillListView(allQuestions);

        ListView listView = (ListView) findViewById(R.id.listdata);
        QuestionAdapter adapter = new QuestionAdapter(this, allQuestions);
        listView.setAdapter(adapter);
    }

    public void vSwitchToEdit(View view) {

        Intent showCreate = new Intent(this, EditActivity.class);
        startActivity(showCreate);
    }

    // --- with vGetData - LOAD ALL DATA from SERVER and SAVE in 'allWuestions' ---
    public void FillQuestionArrayList(){
        // --- LOAD JSON STRING FROM SERVER and CONVERT to QUESTION VECTOR ---
        // --- STRING SAVED on 'gesammt' --- QUESTION VEC on 'allQuestion' ---

        JSONObject QuesData  = null;
        try {
            QuesData = new JSONObject(  vGetData(this.findViewById(R.id.activity_main).getRootView()) );
            JSONArray result = QuesData.getJSONArray("server_response");
            String ques,ans1,ans2;

            for(int i=0;i<result.length();i++)
            {
                ques = result.getJSONObject(i).getString("ques");
                ans1 = result.getJSONObject(i).getString("ans_1");
                ans2 = result.getJSONObject(i).getString("ans_2");

                allQuestions.add(new Question(ques,ans1,ans2));

                // --- test ausgabe eines elementes aus allQuestions ---
                //((Question) (allQuestions.elementAt(i))).displayQuestion();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String vGetData(View view) throws ExecutionException, InterruptedException {
        ReadData data = new ReadData(new AsyncResponse() {
            @Override
            public void processFinish(String output) throws JSONException {
                // --- After finish the execute this Mothode will called ---
            }
        });
        return data.execute().get();
    }

    public void FillListView(ArrayList<Question> quesArrayList){
        // --- QUESTION LIST ARRAY to 3 STRINGS ---
        String[] QuesList = new String[allQuestions.size()];
        String[] Ans1List = new String[allQuestions.size()];
        String[] Ans2List = new String[allQuestions.size()];

        for(int i = 0;i<allQuestions.size();i++){
            QuesList[i] = ((Question) allQuestions.get(i)).getQuestion();
            Ans1List[i] = ((Question) allQuestions.get(i)).getAnswer1();
            Ans2List[i] = ((Question) allQuestions.get(i)).getAnswer2();
        }

        ListView listView1 = (ListView) findViewById(R.id.listdata);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, QuesList);

        listView1.setAdapter(adapter);
    }
}
