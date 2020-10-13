package com.example.webapplicationwithspring;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EventData extends AsyncTask<Void,Void,Void> {
    String data = "";
    String parsed = "";
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://192.168.0.119:8080/eventsList");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null){
                line = bufferedReader.readLine();
                data += line;
            }
            JSONArray ja = new JSONArray(data);
            for(int i = 0; i < ja.length();i ++){
                JSONObject jo = (JSONObject) ja.get(i);
                parsed += "EventName " + jo.get("eventName") + "\n" +
                        "eventData " + jo.get("eventDate") + "\n" +
                        "eventDescription " + jo.get("eventDescription") + "\n" +
                        "eventPlace " + jo.get("eventPlace") + "\n";

            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        EventsActivity.eventData.setText(parsed);
    }
}
