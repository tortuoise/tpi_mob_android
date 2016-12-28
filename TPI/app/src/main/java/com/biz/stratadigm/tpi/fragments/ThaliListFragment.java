package com.biz.stratadigm.tpi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.biz.stratadigm.tpi.DataThali;
import com.biz.stratadigm.tpi.R;
import com.biz.stratadigm.tpi.adapters.ThaliAdapter;
import com.biz.stratadigm.tpi.tools.Constant;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by tamara on 12/15/16.
 * Class represent list of thali
 */

public class ThaliListFragment extends Fragment {
    private RecyclerView mList;
    private RecyclerView.LayoutManager mLayoutMAnager;
    private ArrayList<DataThali> mListThali;
    private ThaliAdapter mVenueAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thali_list_fragment, container, false);
        mListThali=new ArrayList<>();
        mList=(RecyclerView)view.findViewById(R.id.thaliList);
        mVenueAdapter = new ThaliAdapter(mListThali,getActivity());
        mLayoutMAnager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mList.setLayoutManager(mLayoutMAnager);
        mList.setAdapter(mVenueAdapter);
        getThaliList();

        return view;
    }
    private void getThaliList() {

            final StringRequest stringRequest = new StringRequest(Request.Method.GET,Constant.THALISLIST,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray response1 = new JSONArray(response);
                                for (int i = 0; i < response1.length(); i++) {
                                    String id = response1.getJSONObject(i).getString("id");
                                    String name = response1.getJSONObject(i).getString("name");
                                    String submitted = response1.getJSONObject(i).getString("submitted");
                                    String limited = response1.getJSONObject(i).getString("limited");
                                    String region = response1.getJSONObject(i).getString("region");
                                    String price = response1.getJSONObject(i).getString("price");
                                    String image = response1.getJSONObject(i).getString("image");
                                    String userid = response1.getJSONObject(i).getString("userid");
                                    String venue = response1.getJSONObject(i).getString("venue");
                                    String verified = response1.getJSONObject(i).getString("verified");
                                    String accepted = response1.getJSONObject(i).getString("accepted");
                                    String target=response1.getJSONObject(i).getString("target");


                                    DataThali thali = new DataThali(id,name,submitted,target,limited,region,price,
                                            image,userid,venue,verified,accepted);
                                    mListThali.add(thali);
                                    mVenueAdapter.notifyDataSetChanged();
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
