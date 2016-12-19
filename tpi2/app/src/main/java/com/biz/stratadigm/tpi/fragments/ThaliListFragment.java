package com.biz.stratadigm.tpi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.biz.stratadigm.tpi.DataVenue;
import com.biz.stratadigm.tpi.R;
import com.biz.stratadigm.tpi.activity.MainActivity;
import com.biz.stratadigm.tpi.tools.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tamara on 12/15/16.
 * Class represent list of thali
 */

public class ThaliListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thali_list_fragment, container, false);

        getThaliList();

        return view;
    }
    private void getThaliList() {

            final StringRequest stringRequest = new StringRequest(Request.Method.GET,Constant.THAILSLIST,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("tamara", response);
                            try {
                                JSONArray response1 = new JSONArray(response);
                                for (int i = 0; i < response1.length(); i++) {
//                                    String id = response1.getJSONObject(i).getString("id");
//                                    String name = response1.getJSONObject(i).getString("name");
//                                    String submitted = response1.getJSONObject(i).getString("submitted");
//                                    JSONObject location = response1.getJSONObject(i).getJSONObject("location");
//                                    String lat = location.getString("Lat");
//                                    String lng = location.getString("Lng");
//                                    String thalis = response1.getJSONObject(i).getString("thalis");
//
//                                    DataVenue venue = new DataVenue(id, name, submitted, lat, lng, thalis);
//                                    mListVenue.add(venue);
//                                    Log.e("tamara", "size " + mListVenue.size());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("tamara", "That didn't work!");
                }
            });
        // Add the request to the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);//post request on queue
        }

}
