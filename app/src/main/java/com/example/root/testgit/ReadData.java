package com.example.root.testgit;

import android.os.AsyncTask;
import android.text.Editable;
import android.widget.TextView;

import com.example.root.testgit.EditActivity;
import com.example.root.testgit.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class ReadData extends AsyncTask<String,EditActivity,String> {
    String connectUrl;
    public EditActivity.AsyncResponse delegate = null; // --- Call back interface ---

    public ReadData(EditActivity.AsyncResponse asyncResponse) {
        // --- Assigning call back interfacethrough constructor ---
        delegate = asyncResponse;
    }

    public StringBuilder StBu = new StringBuilder();

    @Override
    protected void onPreExecute() {
        connectUrl = "http://www.varbrooker-heide.de/get_question.php";

    }

    @Override
    protected String doInBackground(String... args) {
        String JSON;

        try {
            URL url = new URL(connectUrl);
            HttpURLConnection UrlCon = (HttpURLConnection) url.openConnection();

            InputStream InSt = UrlCon.getInputStream();
            BufferedReader BufRe = new BufferedReader(new InputStreamReader(InSt));

            while((JSON = BufRe.readLine()) != null)
            {
                StBu.append(JSON + "\n");
            }

            BufRe.close();
            InSt.close();
            UrlCon.disconnect();

            return StBu.toString().trim();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    @Override
    protected void onProgressUpdate(EditActivity... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        delegate.processFinish(result);
    }
}