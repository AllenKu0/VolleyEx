package com.example.volleyexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private TextView responseGet_txt,responsePost_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseGet_txt = findViewById(R.id.responseGet_txt);
        responsePost_txt = findViewById(R.id.responsePost_txt);
        //Volley 執行QUEUE
        requestQueue = Volley.newRequestQueue(this);

        getApi("https://jsonplaceholder.typicode.com/posts/1");
        postApi("https://jsonplaceholder.typicode.com/posts");
        try {
            postJsonObjectApi("https://jsonplaceholder.typicode.com/posts");
        } catch (JSONException e) {
            Log.e("TAG", "onCreate: "+e.getMessage() );
            e.printStackTrace();
        }
    }
    //StringRequest(GET)
    private void getApi(String url){
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                responseGet_txt.setText(response);
                Log.d("Get Success", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseGet_txt.setText(error.getMessage());
                Log.e("Get Fail", error.getMessage(), error);
            }
        });
        requestQueue.add(stringRequest);
    }
    //StringRequest(POST)
    private void postApi(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                responsePost_txt.setText(response);
                Log.d("Post Success", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responsePost_txt.setText(error.getMessage());
                Log.e("Post Fail", error.getMessage(), error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("userId", "value1");
                map.put("title", "value2");
                map.put("body","value3");
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
    //JsonObjectRequest
    private void postJsonObjectApi(String url) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", "value1");
        jsonObject.put("title", "value2");
        jsonObject.put("body","value3");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("PostJsonObject Success" ,response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("PostJsonObject Fail", error.getMessage(), error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}