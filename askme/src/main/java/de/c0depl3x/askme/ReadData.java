package de.c0depl3x.askme;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.concurrent.ExecutionException;

enum DataType{Question,Comment}

enum UploadType{Insert,Update}

class ReadData extends AsyncTask<String,EditActivity,String> {

    private String connectUrl;
    private DataType datatype;
    private ReadingView appContext;     // Um auf die Eigenschaften der Activity zuzugreifen
    public StringBuilder StBu = new StringBuilder();

    public ReadData(ReadingView context, DataType datatype) {
        this.appContext = context;
        this.datatype = datatype;
    }

    @Override
    protected void onPreExecute() {
        if(this.datatype == DataType.Question){
            connectUrl = "http://app.webclient5.de/get/get_question.php";
            //connectUrl = "http://127.0.0.1/askm3/get_question.php";
        }
        if(this.datatype == DataType.Comment){
            connectUrl = "http://app.webclient5.de/get/get_comment.php";

            //connectUrl = "http://127.0.0.1/askm3/get_comment.php";
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
        if (result == "fail") {
            Toast.makeText(appContext, "Vebindung fehlgeschlagen", Toast.LENGTH_LONG).show();
            appContext.setRefreshing(false);
            return;
        }

        try {
            JSONObject newData = new JSONObject(result);
            appContext.fillListView(newData);
            return;
        } catch (JSONException e) {
            Toast.makeText(appContext, "Ungültige Daten", Toast.LENGTH_LONG).show();
            Log.d("Test", result);
            //Toast.makeText(appContext, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (ExecutionException e) {
            Toast.makeText(appContext, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (InterruptedException e) {
            Toast.makeText(appContext, "Anfrage konnte nicht beendet werden", Toast.LENGTH_LONG).show();
            //Toast.makeText(appContext, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        appContext.setRefreshing(false);
    }
}