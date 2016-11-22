package com.example.root.testgit;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

class InsertData extends AsyncTask<String,Void,String> {
    String connectUrl;
    public MainActivity.AsyncResponse delegate = null; // --- Call back interface ---

    public InsertData(MainActivity.AsyncResponse asyncResponse) {
        // --- Assigning call back interfacethrough constructor ---
        delegate = asyncResponse;
    }

    @Override
    protected void onPreExecute() {
        connectUrl = "http://www.varbrooker-heide.de/add_question.php";
    }

    @Override
    protected String doInBackground(String... args) {
        String ques, ans1,ans2;
        ques = args[0];
        ans1 = args[1];
        ans2 = args[2];

        try {
            URL url = new URL(connectUrl);
            HttpURLConnection UrlCon = (HttpURLConnection) url.openConnection();
            UrlCon.setRequestMethod("POST");
            UrlCon.setDoOutput(true);

            OutputStream OutStr = UrlCon.getOutputStream();
            BufferedWriter BufWr = new BufferedWriter(new OutputStreamWriter(OutStr,"UTF-8"));

            String data_string = URLEncoder.encode("question","UTF-8") + "=" + URLEncoder.encode(ques,"UTF-8") + "&" +
                    URLEncoder.encode("answer_1","UTF-8") + "=" + URLEncoder.encode(ans1,"UTF-8") + "&" +
                    URLEncoder.encode("answer_2","UTF-8") + "=" + URLEncoder.encode(ans2,"UTF-8");

            BufWr.write(data_string);
            BufWr.flush();
            BufWr.close();
            OutStr.close();

            InputStream inStr = UrlCon.getInputStream();
            inStr.close();
            UrlCon.disconnect();

            return "One shit inside";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            delegate.processFinish(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}