package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.SimpleFormatter;

public final class QueryUtils {
    /** Sample JSON response for a USGS query */
    private static final String baseURL = "https://earthquake.usgs.gov/fdsnws/event/1/query?" +
            "format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    private static String jsonResponse;

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquake> extractEarthquakesFromJSON(String earthquakeJSON) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject JsonResponseObject = new JSONObject(earthquakeJSON);

            JSONArray features = JsonResponseObject.getJSONArray("features");
            for(int i=0; i<features.length(); i++){
                JSONObject prop = features.getJSONObject(i).getJSONObject("properties");
                Double mag = prop.getDouble("mag");
                String loc = prop.getString("place");
                Long time = prop.getLong("time");
                String url = prop.getString("url");
//                Date date = new Date(time);
//                SimpleDateFormat formatter = new SimpleDateFormat("MMM DD, yyyy");
//                String dateString = formatter.format(date);
                Earthquake quake = new Earthquake(mag, loc, time, url);
                earthquakes.add(quake);
            }
            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    private static String getResponseFromHttpURL(URL url) throws IOException {
        HttpURLConnection urlConnection = null;


        try{
            urlConnection= (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream is = urlConnection.getInputStream();

            Scanner sc = new Scanner(is);
            sc.useDelimiter("\\A");

            boolean hasInput = sc.hasNext();

            if(hasInput){
                return sc.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    private static URL buildUrl(String baseURL) {
        URL url = null;
        try {
            url = new URL(baseURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static List<Earthquake> fetchEarthquakeData(String requestUrl){
        URL url = buildUrl(requestUrl);

        String jsonResponse = null;

        try{
            jsonResponse = getResponseFromHttpURL(url);
        } catch (IOException e){
            e.printStackTrace();
        }

        List<Earthquake> quakes = extractEarthquakesFromJSON(jsonResponse);
        return quakes;
    }

}
