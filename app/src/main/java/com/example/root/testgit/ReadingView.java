package com.example.root.testgit;

import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Luis on 21.12.2016.
 */

public abstract class ReadingView extends AppCompatActivity {

    public abstract void fillListView(JSONObject listData) throws ExecutionException, InterruptedException, JSONException;
    public abstract void updateList();

}
