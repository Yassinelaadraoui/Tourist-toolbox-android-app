// code for the list view inflate is taken from the following tutorial website
// http://www.icoderslab.com/tutorial-of-custom-listview-using-volley/


package com.example.yassi.mytoolbox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class arealist extends AppCompatActivity {

    private static final String tag = arealist.class.getSimpleName();
    private List<DataSet> list = new ArrayList<DataSet>();
    private ListView listView;
    private Adapter adapter;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arealist);
        Intent list_intent = getIntent();
        String message = list_intent.getStringExtra(displaymessage.itemtype);

        String itemValue = list_intent.getStringExtra(displaymessage.local);
        Bundle b = getIntent().getExtras();
        final double longitude = b.getDouble("longitude");
        final double latitude = b.getDouble("latitude");
        Log.v(String.valueOf(longitude),"longitude");
        Log.v(String.valueOf(latitude),"latitude");
        Log.v(message,"typex");
        Log.v(itemValue,"typex1");

        listView = findViewById(R.id.list);
        adapter = new Adapter(this, list);
        listView.setAdapter(adapter);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);


        if(itemValue == null) {
            Log.v(itemValue,"url items");
           // ,
            url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius=500&type="+message+"&key=AIzaSyDXfNhEBynRq5FSY-6p9wEDU_MHa2MwAeY";
            Log.v(url,"url");
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {

                        @SuppressLint("LongLogTag")
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            try {

                                JSONObject json = new JSONObject(response);

                                for (int i = 0; i < 7; i++) {
                                    try {
                                        JSONArray cast = json.getJSONArray("results");
                                        JSONObject obj = cast.getJSONObject(i);
                                        JSONArray image = obj.getJSONArray("photos");
                                        JSONObject imageobj = image.getJSONObject(0);
                                        JSONObject openning = obj.getJSONObject("opening_hours");
                                        String opennow = openning.getString("open_now");


                                        String ref = imageobj.getString("photo_reference");
                                        String reference ="https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+ref+"&key=AIzaSyDXfNhEBynRq5FSY-6p9wEDU_MHa2MwAeY";
                                        Log.v(reference,"image");
                                        DataSet dataSet = new DataSet();
                                        dataSet.setName(obj.getString("name"));
                                        dataSet.setImage(reference);
                                        dataSet.setWorth("Location: "+obj.getString("vicinity"));
                                        dataSet.setYear(obj.getInt("rating"));
                                        if (opennow == "false")dataSet.setSource("It's Not Open Now");
                                        else dataSet.setSource("Open Now");

                                        list.add(dataSet);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                adapter.notifyDataSetChanged();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                public void onErrorResponse(VolleyError error) {
                    //TextViewx.setText("That didn't work!");
                    AlertDialog.Builder add = new AlertDialog.Builder(arealist.this);
                    add.setMessage(error.getMessage()).setCancelable(true);
                    AlertDialog alert = add.create();
                    alert.setTitle("Error!!!");
                    alert.show();
                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);


            Controller.getPermission().addToRequestQueue(stringRequest);
        }
        else{
            url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+message+"in"+itemValue+"&key=AIzaSyDXfNhEBynRq5FSY-6p9wEDU_MHa2MwAeY";

// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {

                        @SuppressLint("LongLogTag")
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            try {

                                JSONObject json = new JSONObject(response);

                                for (int i = 0; i < 7; i++) {
                                    try {
                                        JSONArray cast = json.getJSONArray("results");
                                        JSONObject obj = cast.getJSONObject(i);
                                        JSONArray image = obj.getJSONArray("photos");
                                        JSONObject imageobj = image.getJSONObject(0);
                                        JSONObject openning = obj.getJSONObject("opening_hours");
                                        String opennow = openning.getString("open_now");


                                        String ref = imageobj.getString("photo_reference");
                                        String reference ="https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+ref+"&key=AIzaSyDXfNhEBynRq5FSY-6p9wEDU_MHa2MwAeY";
                                        Log.v(reference,"image");
                                        DataSet dataSet = new DataSet();
                                        dataSet.setName(obj.getString("name"));
                                        dataSet.setImage(reference);
                                        dataSet.setWorth("Location: "+obj.getString("formatted_address"));
                                        dataSet.setYear(obj.getInt("rating"));
                                        if (opennow == "false")dataSet.setSource("It's Not Open Now");
                                        else dataSet.setSource("Open Now");

                                        list.add(dataSet);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                adapter.notifyDataSetChanged();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                public void onErrorResponse(VolleyError error) {
                    //TextViewx.setText("That didn't work!");
                    AlertDialog.Builder add = new AlertDialog.Builder(arealist.this);
                    add.setMessage(error.getMessage()).setCancelable(true);
                    AlertDialog alert = add.create();
                    alert.setTitle("Error!!!");
                    alert.show();
                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);


            Controller.getPermission().addToRequestQueue(stringRequest);
        }




    }
    public void businessSearchTest() throws IOException {




    }
}
