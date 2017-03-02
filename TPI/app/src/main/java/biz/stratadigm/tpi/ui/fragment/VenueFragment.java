package biz.stratadigm.tpi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.android.volley.error.NoConnectionError;
import com.android.volley.error.ParseError;
import com.android.volley.error.ServerError;
import com.android.volley.error.TimeoutError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import biz.stratadigm.tpi.R;
import biz.stratadigm.tpi.tools.Constant;
import biz.stratadigm.tpi.ui.activity.BrowseActivity;

/**
 * Created by tamara on 12/11/16.
 * VenueFragment class
 */

public class VenueFragment extends Fragment {
    private static final String TAG = "TPI";
    public static final String ARGUMENT_EDIT_VENUE_ID = "EDIT_VENUE_ID";
    private EditText mEtName;
    private TextView mPositon;
    private Button mSend;
    private HashMap<String, Float> mLocation = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venue_add, container, false);

        mEtName = (EditText) view.findViewById(R.id.editTextName);
        mSend = (Button) view.findViewById(R.id.buttonSend);
        mPositon = (TextView) view.findViewById(R.id.position);
        mPositon.setText("Longitude " + BrowseActivity.longitude + " Latitude " + BrowseActivity.latitude);
        mLocation.put("Lat", Float.parseFloat("" + BrowseActivity.latitude));
        mLocation.put("Lng", Float.parseFloat("" + BrowseActivity.longitude));

        mSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendVenue();
            }
        });


        return view;
    }

    /**
     * Add new venue on server
     */
    private void sendVenue() {

        Map<String, Object> params = new HashMap<String, Object>();
        // JSONObject ob = new JSONObject(mLocation);
        params.put("name", mEtName.getText().toString());
        params.put("location", mLocation);
        JsonObjectRequest stringReguest = new JsonObjectRequest(Request.Method.POST, Constant.VENUES,

                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject o = new JSONObject(String.valueOf(response));
                            Log.v(TAG, response.toString());
                            Toast.makeText(getActivity().getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        // inspect type of error
                        if (networkResponse != null) {
                            Log.e(TAG, "Error. HTTP Status Code:" + networkResponse.statusCode);
                        }
                        if (error instanceof TimeoutError) {
                            Log.e(TAG, "TimeoutError");
                        } else if (error instanceof NoConnectionError) {
                            Log.e(TAG, "NoConnectionError");
                        } else if (error instanceof AuthFailureError) {
                            Log.e(TAG, "AuthFailureError");
                        } else if (error instanceof ServerError) {
                            Log.e(TAG, "ServerError");
                        } else if (error instanceof NetworkError) {
                            Log.e(TAG, "NetworkError");
                        } else if (error instanceof ParseError) {
                            Log.e(TAG, "ParseError");
                        }
                        Toast.makeText(getActivity().getApplicationContext(), "error " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("USerFragment-agent", System.getProperty("http.agent"));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringReguest);
    }
}
