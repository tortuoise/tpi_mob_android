package biz.stratadigm.tpi.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

import biz.stratadigm.tpi.App;
import biz.stratadigm.tpi.R;
import biz.stratadigm.tpi.ui.activity.TakePictureActivity;
import biz.stratadigm.tpi.ui.view.ThaliView;
import biz.stratadigm.tpi.di.component.DaggerThaliComponent;
import biz.stratadigm.tpi.di.module.ThaliModule;
import biz.stratadigm.tpi.presenter.ThaliPresenter;
import biz.stratadigm.tpi.ui.adapter.NothingSelectedSpinnerAdapter;
import nucleus.factory.PresenterFactory;
import biz.stratadigm.tpi.tools.Constant;

/**
 * Created by tamara on 12/11/16.
 * ThaliFragment class
 */

public class ThaliFragment extends BaseFragment<ThaliPresenter> implements ThaliView, OnItemSelectedListener{

    public static final String ARGUMENT_EDIT_THALI_ID = "EDIT_THALI_ID";
    public static final String ARGUMENT_VIEW_THALI_ID = "VIEW_THALI_ID";
    public static final String ARGUMENT_ADD_THALI_VENUEID = "ADD_THALI_VENUEID";
    private static final String TAG = "TPI";
    // components
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText nameEditText, priceEditText;
    private Spinner spinner;
    public static TextView venueTextView, thaliIdTextView;
    private Button mButSend;
    private Switch switcher;
    private CheckBox north, west, east, south;
    private String region, target;
    private String filePath;
    private SharedPreferences sharedPreferences;
    private long venueId;

    @Inject
    ThaliPresenter thaliPresenter;
    {
        DaggerThaliComponent.builder()
                .appComponent(App.getAppComponent())
                .thaliModule(new ThaliModule())
                .build()
                .inject(this);
    }


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        Bundle args = getActivity().getIntent().getExtras();
        if (args != null) {
            venueId = args.getLong(ARGUMENT_ADD_THALI_VENUEID); 
            Log.v(TAG, "Getting intent args: " + venueId);
            //thaliListPresenter.setCurrentVenueName(args.getString(ARG_NAME)); 
        } //else if (thaliListPresenter.getCurrentPosition() != -1) {
            //thaliListPresenter.setCurrentPosition(0); 
            //thaliListPresenter.setCurrentVenueName(getResources().getString(R.string.unknown)); 
        //}
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
        View view = inflater.inflate(R.layout.fragment_thali, container, false);
        setHasOptionsMenu(true);

        // Init components and listneres
        sharedPreferences = getActivity().getSharedPreferences(Constant.TAG, Context.MODE_PRIVATE);
        mButSend = (Button) view.findViewById(R.id.btsend);

        //target = (EditText) view.findViewById(R.id.taget);
        spinner = (Spinner) view.findViewById(R.id.target);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),   
            R.array.target_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setPrompt("Select Target Market...");
        spinner.setAdapter(
            new NothingSelectedSpinnerAdapter(
            adapter,
            R.layout.spinner_nothing_selected,
            // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
            getActivity().getApplicationContext()));
        //spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        nameEditText = (EditText) view.findViewById(R.id.name);
        priceEditText = (EditText) view.findViewById(R.id.price);
        //  price.setInputType(InputType.NU);
        venueTextView = (TextView) view.findViewById(R.id.venue);
        thaliIdTextView = (TextView) view.findViewById(R.id.thaliId);
        venueTextView.setInputType(InputType.TYPE_NULL);
        venueTextView.setText(""+venueId);
        //venueTextView.setText(sharedPreferences.getString("venue", ""));

        switcher = (Switch) view.findViewById(R.id.limited);
        north = (CheckBox) view.findViewById(R.id.north);
        west = (CheckBox) view.findViewById(R.id.west);
        south = (CheckBox) view.findViewById(R.id.south);
        east = (CheckBox) view.findViewById(R.id.east);
        // Chack region
        north.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    region = "north";
                    west.setChecked(false);
                    east.setChecked(false);
                    south.setChecked(false);
                }
            }
        });
        west.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    region = "west";
                    north.setChecked(false);
                    east.setChecked(false);
                    south.setChecked(false);
                }
            }
        });
        east.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    region = "east";
                    west.setChecked(false);
                    north.setChecked(false);
                    south.setChecked(false);
                }
            }
        });
        south.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    region = "south";
                    west.setChecked(false);
                    east.setChecked(false);
                    north.setChecked(false);
                }
            }
        });


        mButSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                sendThali();
                } catch (Exception e) {
                    Log.v(TAG, e.toString());
                }
            }
        });
        return view;
        } catch (Exception e) {
            Log.v(TAG, e.toString());
            return new View(getActivity().getApplicationContext());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //ButterKnife.bind(this, view);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        //thaliAdapter = new ThaliAdapter();
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Sending thali on server
     */
    public void sendThali() {
        if (!verify())
            Toast.makeText(getApplicationContext(), "Incomplete data", Toast.LENGTH_LONG).show();
        else 
            thaliPresenter.onCreateButtonClicked();
    /*
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("target", target.getText().toString());
        params.put("venue", Integer.parseInt(venue.getText().toString()));
        params.put("limited", switcher.isChecked());
        params.put("region", region);
        if (price.getText().toString().equalsIgnoreCase("")) {
            params.put("price", Integer.parseInt("0"));
        } else {
            params.put("price", Integer.parseInt(price.getText().toString()));
        }
        params.put("photo", "");
        //  params.put("action",null);
        JsonObjectRequest stringReguest = new JsonObjectRequest(Request.Method.POST, Constant.THALIS,

                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity().getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        Log.e("tamara", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null) {
                            Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                        }

                        if (error instanceof TimeoutError) {
                            Log.e("Volley", "TimeoutError");
                        } else if (error instanceof NoConnectionError) {
                            Log.e("Volley", "NoConnectionError");
                        } else if (error instanceof AuthFailureError) {
                            Log.e("Volley", "AuthFailureError");
                        } else if (error instanceof ServerError) {
                            Log.e("Volley", "ServerError");
                        } else if (error instanceof NetworkError) {
                            Log.e("Volley", "NetworkError");
                        } else if (error instanceof ParseError) {
                            Log.e("Volley", "ParseError");
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
    */
    }

    public void selected() {
        venueTextView.setText(sharedPreferences.getString("venue", ""));
    }

    @Override
    public PresenterFactory getPresenterFactory() {
        return () -> thaliPresenter;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        target = (String) parent.getItemAtPosition(position);
    }

    @Override 
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public Long getIdent() {
        return Long.parseLong(thaliIdTextView.getText().toString());
    }

    @Override
    public String getName() {
        return nameEditText.getText().toString();
    }

    @Override
    public String getTarget() {
        return region;
    }

    @Override
    public String getRegion() {
        return region;
    }

    @Override
    public Boolean getLimited() {
        return true;
    }

    @Override
    public Integer getPrice() {
        return Integer.parseInt(priceEditText.getText().toString());
    }

    @Override
    public Long getVenue() {
        return Long.parseLong(venueTextView.getText().toString());
    }

    @Override
    public Long getUserId() {
        return 1L;
        // TODO: return saved user id from app preferences
    }

    @Override
    public void showThali() {

    }

    @Override
    public void showTakePhoto(long id) {
        Toast.makeText(getApplicationContext(), "Thanks for the input " + id , Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getContext(), TakePictureActivity.class);
        intent.putExtra(TakePictureActivity.ARG_ADD_PIC_THALI_ID, id);
        try {
        startActivity(intent);
        getActivity().finish();
        } catch (Exception e) {
            Log.v(TAG, e.toString());
        }
    }
    
    @Override
    public void showAuthError() {
        Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_LONG).show();
    }

    public boolean verify() {
        if (getName() == "" || getTarget() == null || getRegion() == "" || getPrice() == 0 )
            return false;
        return true;
    }
}
