package com.example.root.testgit;

import android.os.AsyncTask;

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
    private DataType dataType;

    public EditActivity.AsyncResponse delegate = null; // --- Call back interface ---

    public InsertData(EditActivity.AsyncResponse asyncResponse) {
        // --- Assigning call back interfacethrough constructor ---
        delegate = asyncResponse;
    }

    @Override
    protected void onPreExecute() {
        if (this.dataType == DataType.Question) {
            connectUrl = "http://www.varbrooker-heide.de/add_question.php";
        }
        if(this.dataType == DataType.Comment){
            connectUrl = "http://www.varbrooker-heide.de/add_comment.php";
        }
    }

    @Override
    protected String doInBackground(String... args) {
        String ques, ans1,ans2,authorid, id;
        ques = args[0]; //also comment text
        ans1 = args[1]; //also authorid
        ans2 = args[2]; //also quesid
        authorid = args[3];
        id = args[4];
        try {
            URL url = new URL(connectUrl);
            HttpURLConnection UrlCon = (HttpURLConnection) url.openConnection();
            UrlCon.setRequestMethod("POST");
            UrlCon.setDoOutput(true);

            OutputStream OutStr = UrlCon.getOutputStream();
            BufferedWriter BufWr = new BufferedWriter(new OutputStreamWriter(OutStr,"UTF-8"));

            String data_string = "";

                data_string = URLEncoder.encode("question","UTF-8") + "=" + URLEncoder.encode(ques,"UTF-8") + "&" +
                        URLEncoder.encode("answer_1","UTF-8") + "=" + URLEncoder.encode(ans1,"UTF-8") + "&" +
                        URLEncoder.encode("authorid","UTF-8") + "=" + URLEncoder.encode(authorid,"UTF-8") + "&" +
                        URLEncoder.encode("id","UTF-8") + "=" + URLEncoder.encode(id,"UTF-8") + "&" +
                        URLEncoder.encode("answer_2","UTF-8") + "=" + URLEncoder.encode(ans2,"UTF-8");

            BufWr.write(data_string);
            BufWr.flush();
            BufWr.close();
            OutStr.close();

            InputStream inStr = UrlCon.getInputStream();
            inStr.close();
            UrlCon.disconnect();

            return "Data saved!";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Something went wrong!";
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

    public void setDataType(DataType type){
        this.dataType = type;
    }
}