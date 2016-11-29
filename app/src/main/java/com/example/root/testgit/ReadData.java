package com.example.root.testgit;

import android.os.AsyncTask;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

enum DataType{Question,Comment}

class ReadData extends AsyncTask<String,EditActivity,String> {

    private String connectUrl;
    private DataType datatype;
    public StringBuilder StBu = new StringBuilder();
    public MainActivity.AsyncResponse delegate = null; // --- Call back interface ---

    public ReadData(MainActivity.AsyncResponse asyncResponse) {
        // --- Assigning call back interface through constructor ---
        delegate = asyncResponse;
    }

    @Override
    protected void onPreExecute() {
        if(this.datatype == DataType.Question){
            connectUrl = "http://www.varbrooker-heide.de/get_question.php";
        }
        if(this.datatype == DataType.Comment){
            connectUrl = "http://www.varbrooker-heide.de/get_comment.php";
        }
    }

    @Override
    protected String doInBackground(String... args) {
        String JSON;
        String author = args[0];
        String id = args[1];

        try {
            URL url = new URL(connectUrl);
            HttpURLConnection UrlCon = (HttpURLConnection) url.openConnection();

            if (datatype == DataType.Comment) {
                UrlCon.setRequestMethod("POST");
                UrlCon.setDoOutput(true);

                OutputStream OutStr = UrlCon.getOutputStream();
                BufferedWriter BufWr = new BufferedWriter(new OutputStreamWriter(OutStr, "UTF-8"));

                String data_string = "";

                data_string = URLEncoder.encode("author", "UTF-8") + "=" + URLEncoder.encode(author, "UTF-8") + "&" +
                        URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                BufWr.write(data_string);
                BufWr.flush();
                BufWr.close();
                OutStr.close();
            }
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
        try {
            delegate.processFinish(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setDatatype(DataType type){
        this.datatype = type;
    }
}