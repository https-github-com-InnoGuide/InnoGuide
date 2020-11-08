package com.example.webapplicationwithspring.directionhelpers;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.webapplicationwithspring.MapActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class FetchURL extends AsyncTask<String, Void, String> {
    Context mContext;
    String directionMode = "driving";
    public static String distance;
    public static String duration ;

    public FetchURL(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        // For storing data from web service
        String data = "";
        directionMode = strings[1];
        try {
            // Fetching the data from web service
            data = downloadUrl(strings[0]);
            Log.d("mylog", "Background task data " + data.toString());
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        PointsParser parserTask = new PointsParser(mContext, directionMode);
        // Invokes the thread for parsing the JSON data
        parserTask.execute(s);
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            Log.d("mylog", "Downloaded URL: " + data.toString());

            for (int i=0;i<data.length();i++)
                if(data.charAt(i)=='d' && data.charAt(i+1)=='i' && data.charAt(i+2)=='s' && data.charAt(i+3)=='t' && data.charAt(i+4)=='a')
                    for(int j=i;j<data.length();j++)
                        if (data.charAt(j)=='e' && data.charAt(j+1)=='x' && data.charAt(j+2)=='t')
                            for (int k=j+5;k<data.length();k++)
                                if (data.charAt(k)=='"')
                                    for (int l=k+1;l<data.length();l++)
                                        if ((data.charAt(l)=='"'))
                                        {
                                            distance = data.substring(k+1,l);
                                            Log.d("mylog", "k l  " + k + l);
                                            k = l = j = i = data.length();
                                        }
            for (int i=0;i<data.length();i++)
                if(data.charAt(i)=='d' && data.charAt(i+1)=='u' && data.charAt(i+2)=='r' && data.charAt(i+3)=='a' && data.charAt(i+4)=='t')
                    for(int j=i;j<data.length();j++)
                        if (data.charAt(j)=='e' && data.charAt(j+1)=='x' && data.charAt(j+2)=='t')
                            for (int k=j+5;k<data.length();k++)
                                if (data.charAt(k)=='"')
                                    for (int l=k+1;l<data.length();l++)
                                        if ((data.charAt(l)=='"'))
                                        {
                                            duration = data.substring(k+1,l);
                                            Log.d("mylog", "k l  " + k + l);
                                            k = l = j = i = data.length();
                                        }
            Log.d("mylog", "distance " + distance);
            Log.d("mylog", "duration " + duration);
            br.close();
        } catch (Exception e) {
            Log.d("mylog", "Exception downloading URL: " + e.toString());
        } finally {
            assert iStream != null;
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
