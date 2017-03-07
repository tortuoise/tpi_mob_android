package biz.stratadigm.tpi.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import javax.inject.Inject;

import biz.stratadigm.tpi.App;
import biz.stratadigm.tpi.R;
import biz.stratadigm.tpi.di.component.DaggerThaliComponent;
import biz.stratadigm.tpi.di.module.ThaliModule;
import biz.stratadigm.tpi.presenter.ThaliPicturePresenter;
import biz.stratadigm.tpi.ui.activity.TakePictureActivity;
import biz.stratadigm.tpi.ui.activity.BrowseActivity;
import biz.stratadigm.tpi.ui.view.ThaliPictureView;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.PresenterFactory;

public class UploadPhotoFragment extends BaseFragment<ThaliPicturePresenter> implements ThaliPictureView, View.OnClickListener {

    private static final String TAG = "TPI";
    public static final String ARG_PICTURE_URI = "PIC_URI";
    public static final String ARG_THALI_ID = "THALI_ID";
    public String VAL_PICTURE_URI = "";
    private long VAL_ADD_PIC_THALI_ID = 1000001L;
    private long VAL_ADD_PIC_THALI_ID_DEFAULT = 1000001L;
    private boolean submit_enable = false;

    @BindView(R.id.button_submit)
    Button uploadButton;

    @Inject
    ThaliPicturePresenter thaliPicturePresenter;
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
        Bundle args = getArguments(); //getActivity().getIntent().getExtras();
        if (args != null) {
            VAL_ADD_PIC_THALI_ID = args.getLong(ARG_THALI_ID); 
            VAL_PICTURE_URI = args.getString(ARG_PICTURE_URI);
        }
        if (VAL_ADD_PIC_THALI_ID != 0L && VAL_PICTURE_URI != "") {
            submit_enable = true; 
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        //thaliAdapter = new ThaliAdapter();
        uploadButton.setOnClickListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public PresenterFactory getPresenterFactory() {
        return () -> thaliPicturePresenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                //Log.v(TAG, "submit_enable " + submit_enable);
                if (submit_enable) {
                    thaliPicturePresenter.onSubmitPictureButtonClicked(VAL_ADD_PIC_THALI_ID, Uri.parse(VAL_PICTURE_URI));
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Submit not enabled", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void backToBrowse() {
        Intent browse = new Intent(getContext(), BrowseActivity.class);
        startActivity(browse);
        getActivity().finish();
    }

    @Override
    public void showAuthError() {
        Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_LONG).show();
    }
}
