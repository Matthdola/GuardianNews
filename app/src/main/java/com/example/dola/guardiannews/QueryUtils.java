package com.example.dola.guardiannews;

import android.text.TextUtils;
import android.util.Log;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {


    private QueryUtils(){

    }

    public static List<GuardianNew> fetchGuardianNewData(String requestUrl) {
        // Create URL object
        URL url = createURL(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e){
            Log.e(MainActivity.LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<GuardianNew> GuardianNews = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link GuardianNew}s
        return GuardianNews;
    }


    /**
     * Returns new URL object from the given string URL
     * @param strUrl
     * @return
     */
    private static URL createURL(String strUrl){
        URL url = null;
        try{
            url = new URL(strUrl);
        } catch (MalformedURLException e){
            Log.e(MainActivity.LOG_TAG, "Problem building the URL", e);
        }

        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        // If the URL is null, then return early

        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200)
            // then read the input stream and parse the response
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                Log.e(MainActivity.LOG_TAG, "Error response code:" + urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(MainActivity.LOG_TAG, "Problem retrieving the GuardianNew JSON results.", e);

        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }

            if (inputStream != null){
                // Closing the input stream coulf throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream}  into a String which contains the
     * whole JSON response from the server
     */
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<GuardianNew> extractFeatureFromJson(String GuardianNewJson){
        // If the JSON string is empty or null, then return early
        if (TextUtils.isEmpty(GuardianNewJson)){
            return  null;
        }

        // Create an empty ArrayList that we can start adding GuardianNews to
        ArrayList<GuardianNew> GuardianNews = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(GuardianNewJson);
            JSONObject responseObject = jsonObject.getJSONObject("response");
            JSONArray resultsArray = responseObject.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++){
                JSONObject guardianNewObj = resultsArray.getJSONObject(i);
                String id = "";
                String type = "";
                String sectionId = "";
                String sectionName = "";
                String publiDate = "";
                String webTitle = "";
                String webUrl = "";
                String apiUrl = "";
                boolean isHosted = false;
                String pillarId = "";
                String pillarName = "";

                try {
                    id = guardianNewObj.getString("id");
                }catch (Exception e){

                }
                try {
                    type = guardianNewObj.getString("type");
                }catch (Exception e){

                }
                try {
                    sectionId = guardianNewObj.getString("sectionId");
                }catch (Exception e){

                }
                try {
                    sectionName = guardianNewObj.getString("sectionName");
                }catch (Exception e){

                }

                try {
                    publiDate = guardianNewObj.getString("webPublicationDate");
                }catch (Exception e){

                }
                try {
                    webTitle = guardianNewObj.getString("webTitle");
                }catch (Exception e){

                }

                try {
                    webUrl = guardianNewObj.getString("webUrl");
                }catch (Exception e){

                }
                try {
                    apiUrl = guardianNewObj.getString("apiUrl");
                }catch (Exception e){

                }

                try {
                    isHosted = guardianNewObj.getBoolean("isHosted");
                }catch (Exception e){

                }
                try {
                    pillarId = guardianNewObj.getString("pillarId");
                }catch (Exception e){

                }

                try {
                    pillarName = guardianNewObj.getString("pillarName");
                }catch (Exception e){

                }

                GuardianNews.add(new GuardianNew(id, type, sectionId, sectionName,
                        publiDate,webTitle,webUrl, apiUrl,isHosted,pillarId, pillarName));
            }
        } catch (JSONException e){
            Log.e("QueryUtils", "Problem parsing the GuardianNew JSON results", e);
        }

        // Return the list of GuardianNews
        return GuardianNews;
    }
}

