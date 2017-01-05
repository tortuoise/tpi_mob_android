package com.biz.stratadigm.tpi.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.biz.stratadigm.tpi.R;
import com.biz.stratadigm.tpi.tools.Constant;
import com.biz.stratadigm.tpi.ui.activity.MainActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tamara on 12/11/16.
 * <p>
 * Class for reigister user on server
 */

public class UserFragment extends Fragment {
    private EditText mEtPass, mEtEmail, mEtname;
    private Button mConfirm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment_layout, container, false);
        mEtEmail = (EditText) view.findViewById(R.id.editTextEmail);
        mEtPass = (EditText) view.findViewById(R.id.editTextPass);
        mEtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mEtname = (EditText) view.findViewById(R.id.editTextName);
        mConfirm = (Button) view.findViewById(R.id.buttonLogin);

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
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
        params.put("name", mEtname.getText().toString());//param name of user
        params.put("password", mEtPass.getText().toString());

        //making json object of params
        JsonObjectRequest stringReguest = new JsonObjectRequest(Request.Method.POST, Constant.USER,

                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity().getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "error " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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

}
