package biz.stratadigm.tpi.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import biz.stratadigm.tpi.R;
import biz.stratadigm.tpi.entity.dto.ThaliDTO;
import biz.stratadigm.tpi.tools.Constant;
import biz.stratadigm.tpi.ui.adapter.ThaliAdapter;

/**
 * Created by tamara on 01/01/17.
 */

public class VenueListTahli extends AppCompatActivity {
    private RecyclerView mList;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ThaliDTO> mListThali;
    private ThaliAdapter mVenueAdapter;
    private int positiion, offset = 0;
    private TextView more, less;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_lisst_tahli);
        positiion = getIntent().getExtras().getInt("id");

        mListThali = new ArrayList<>();
        mList = (RecyclerView) findViewById(R.id.venueList);
//        mVenueAdapter = new ThaliAdapter(mListThali, this);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mList.setLayoutManager(mLayoutManager);
        mList.setAdapter(mVenueAdapter);
        getThaliList();
        more = (TextView) findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offset = offset + 20;
                mListThali.clear();
                getThaliList();
                mVenueAdapter.notifyDataSetChanged();
            }
        });
        less = (TextView) findViewById(R.id.less);
        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (offset >= 20) {
                    offset = offset - 20;
                    mListThali.clear();
                    getThaliList();
                    mVenueAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getThaliList() {

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.VENUESLISTFORTHALI + positiion + "&offset=" + offset,
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
                                String target = response1.getJSONObject(i).getString("target");


                                ThaliDTO thali = new ThaliDTO(id, name, submitted, target, limited, region, price,
                                        image, userid, venue, verified, accepted);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);//post request on queue
    }
}
