package com.example.root.testgit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;
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

        String whatver = "";

        try {
            whatver = vGetData(this.findViewById(R.id.activity_main).getRootView());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TextView test = (TextView) findViewById(R.id.textView2);

        JSONObject jObject  = null;
        try {
            jObject = new JSONObject(whatver);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                //((Question) (allQuestions.elementAt(i))).displayQuestion();
                gesammt += " -> " + ques + " - " + ans1 + " - " + ans2 + " -------" + allQuestions.size() + "\n";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        test.setText(gesammt);

        System.out.println("" + whatver);
        System.out.println(" --- " + allQuestions.size());

        /*
        String[] QuesList = new String[allQuestions.size()];
        String[] Ans1List = new String[allQuestions.size()];
        String[] Ans2List = new String[allQuestions.size()];

        for(int i = 0;i<allQuestions.size();i++){
            QuesList[i] = ((Question) allQuestions.elementAt(i)).getQuestion();
            Ans1List[i] = ((Question) allQuestions.elementAt(i)).getAnswer1();
            Ans2List[i] = ((Question) allQuestions.elementAt(i)).getAnswer2();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, QuesList);
        */

        ListView listView = (ListView) findViewById(R.id.listdata);
        QuestionAdapter adapter = new QuestionAdapter(this, allQuestions);
        listView.setAdapter(adapter);
    }

    public void vSwitchToEdit(View view) {

        Intent showCreate = new Intent(this, EditActivity.class);
        startActivity(showCreate);
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
}
