package com.android.volleywithgson;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by h-watanabe on 2016/06/16.
 */
public class GetJsonHelper {
    // Callback Interface
    public interface ResponseCallback {
        public void getResponse(String tag, String responseStr);
    }
    private ResponseCallback responseCallback;

    // Constructer including Callback
    public GetJsonHelper(ResponseCallback responseCallback) {
        this.responseCallback = responseCallback;
    }

    // Constructer
    public GetJsonHelper() {
    }

    // Volley Get Json Object
    public void getJsonObject(final String tag, final String url) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseCallback.getResponse(tag, response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("onErrorResponse:::", "Error: " + error.getMessage());
                responseCallback.getResponse(tag, "Error");
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonObjReq, tag);
    }

    // Volley Get Json ArrayList
    public void getJsonArray(final String tag, final String url) {
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        responseCallback.getResponse(tag, response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("onErrorResponse:::", "Error: " + error.getMessage());
                responseCallback.getResponse(tag, "Error");
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(req, tag);
    }

    // Volley Get Image Loader
    public void getImageLoader(NetworkImageView imgNetWorkView, String url) {
        ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();
        // If you are using NetworkImageView
        imgNetWorkView.setImageUrl(url, imageLoader);
    }
}
