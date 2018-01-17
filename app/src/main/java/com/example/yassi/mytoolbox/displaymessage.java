/*
* website used to get started with the Volley library and do the Http requests to APIs
* https://developer.android.com/training/volley/index.html
* */

package com.example.yassi.mytoolbox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;

import static junit.framework.Assert.assertNotNull;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class displaymessage extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;
    public static final String itemtype = "com.example.myfirstapp.MESSAGE";
    public static final String local = "com.example.myfirstapp.MESS";


    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 100;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {

        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            return false;
        }
    };
    ListView listView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_displaymessage);

        mVisible = true;
        hide();
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        final TextView mTextView = (TextView) findViewById(R.id.text);
        final TextView cloudview = (TextView) findViewById(R.id.cloud);
        final TextView location = (TextView) findViewById(R.id.textView2);
        final TextView temperature  = (TextView) findViewById(R.id.textView3);
        final TextView windspeed  = (TextView) findViewById(R.id.textView4);
        final TextView sunrise  = (TextView) findViewById(R.id.sunrise);
        final TextView sunset  = (TextView) findViewById(R.id.sunset);



        final Intent intent = getIntent();
        final String message = intent.getStringExtra(FullscreenActivity.EXTRA_MESSAGE);


        final Bundle b = getIntent().getExtras();
        final double longitude = b.getDouble("longitude");
        final double latitude = b.getDouble("latitude");

        Log.v(String.valueOf(latitude),"latitude");
        Log.v(String.valueOf(longitude),"longitude");



        // Capture the layout's TextView and set the string as its text



        listView = (ListView) findViewById(R.id.list);

        // Defined Array values to show in ListView
        String[] values = new String[] { "food",
                "hospital",
                "night_club",
                "shopping_mall",
                "bar",
                "clothing_store",
                "cafe",
                "museum",
                "movie_theater",
                "library",
                "art_gallery"
        };
        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);
                String locations = message;
                Intent list_intent = new Intent(displaymessage.this, arealist.class);
                Log.v(locations, "messages");

                list_intent.putExtra(itemtype ,  itemValue);
                list_intent.putExtra(local , locations);
                Bundle b = new Bundle();
                Log.v(String.valueOf(longitude)," value longitude");
                Log.v(String.valueOf(latitude)," value latitude");
                b.putDouble("longitude", longitude);
                b.putDouble("latitude", latitude);

                list_intent.putExtras(b);
                startActivity(list_intent);
                // Show Alert
                Toast.makeText(getApplicationContext(),
                        " Loading " , Toast.LENGTH_LONG)
                        .show();

            }
        });
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22dublin%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

        if(message != null) {
            url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + message + "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        }
        else{
            url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(SELECT%20woeid%20FROM%20geo.places%20WHERE%20text%3D%22(" + latitude + "%2C" + longitude + ")%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

        }


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONObject json_query = json.getJSONObject("query");
                            JSONObject json_results = json_query.getJSONObject("results");
                            JSONObject json_channel = json_results.getJSONObject("channel");
                            JSONObject json_item = json_channel.getJSONObject("item");
                            JSONObject json_astronomy = json_channel.getJSONObject("astronomy");
                            JSONObject json_location = json_channel.getJSONObject("location");
                            JSONObject json_wind = json_channel.getJSONObject("wind");

                            JSONObject json_condition = json_item.getJSONObject("condition");
                            String str_temp = json_condition.getString("temp");
                            String str_windspeed = json_wind.getString("speed");
                            String str_temp_text = json_condition.getString("text");
                            String str_sunrise = json_astronomy.getString("sunrise");
                            String str_sunset = json_astronomy.getString("sunset");
                            String str_cityname = json_location.getString("city");
                            String str_country = json_location.getString("country");
                            cloudview.setText(str_temp_text);
                            location.setText(str_cityname + "," + str_country);
                            temperature.setText(str_temp+"°F");
                            windspeed.setText(str_windspeed);
                            sunrise.setText(str_sunrise);
                            sunset.setText(str_sunset);
                            Log.v(str_temp, "indexing: ");
                            Log.v(str_temp_text, "indexing:1 ");
                            Log.v(str_sunrise, "indexing:2 ");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {

        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
