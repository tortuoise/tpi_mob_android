package com.biz.stratadigm.tpi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.biz.stratadigm.tpi.R;
import com.biz.stratadigm.tpi.activity.MainActivity;
import com.biz.stratadigm.tpi.components.CustomEditText;
import com.biz.stratadigm.tpi.tools.Constant;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tamara on 12/22/16.
 */

public class LoginFragment extends Fragment {
    private CustomEditText mEtPass, mEtEmail;
    private Button mConfirm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);
        mEtEmail = (CustomEditText) view.findViewById(R.id.editTextEmail);
        mEtPass = (CustomEditText) view.findViewById(R.id.editTextPass);
        mConfirm = (Button) view.findViewById(R.id.buttonLogin);

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // trysm();
               startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
            }
        });
        return view;
    }

    /**
     * Method for reistration user on server. Using volley request
     */
    private void registerUser() {


        Map<String, String> params = new HashMap<String, String>();

        params.put("email", mEtEmail.getText().toString());//param email of user
        params.put("password", mEtPass.getText().toString());

        //making json object of params
        JsonObjectRequest stringReguest = new JsonObjectRequest(Request.Method.POST, "https://thalipriceindex.appspot.com/token_auth",

                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("tamara", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("tamara", error.getLocalizedMessage());
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("USerFragment-agent", System.getProperty("http.agent"));
                return headers; // headers of request (needs for https)
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringReguest);//post request on queue
    }

    public void trysm() {
        final Map<String, String> params = new HashMap<String, String>();

        //making json object of params
        StringRequest stringReguest = new StringRequest(Request.Method.GET, Constant.TEST,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("tamara", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("tamara", error.toString());
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("USerFragment-agent", System.getProperty("http.agent"));
                headers.put("authorization","eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE0ODI3ODQ3OTksImlhdCI6MTQ4MjUyNTU5OSwic3ViIjoiMCJ9.mSFLIJ2wRr1em_ANYuMw7TAFcyPah4WqUwAG1le9DEiQKIaQrXSmWxCfMqCchFg40JHT7aNhgVPd_iiLkzg_zn4C42AYZ3xezsem2cnR0WtJYsBlQqBAsRKOXWzyLa2IFl_WMT_niwbH5wR8Xub9E38Hued8A5gNQWe16MXhaK796du21kr4HukplRNixnjwPnojOpvOpUbvt5WScho43sJMW0sGBBY3QwDxE-q8HUKuiykmGzKwpS5l50NtKIqWYvfg3t0UkqJmB63ZQ0Trccii_P0ny-fFwe_J9oRtwgIgV5h7wSPJk71-XbufgejmgM_abr651ZvUakPqrVJdjw");
                return headers; // headers of request (needs for https)
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringReguest);//post request on queue
    }
}
