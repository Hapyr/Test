package com.example.root.testgit;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.root.testgit.MainActivity;
import com.example.root.testgit.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class ReadData extends AsyncTask<String,MainActivity,String> {
    String connectUrl;
    public MainActivity.AsyncResponse delegate = null; // --- Call back interface ---

    public ReadData(MainActivity.AsyncResponse asyncResponse) {
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
    protected void onProgressUpdate(MainActivity... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        delegate.processFinish(result);
    }
}