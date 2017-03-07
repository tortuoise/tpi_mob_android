package biz.stratadigm.tpi.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.net.HttpURLConnection;
import java.io.File;
import java.net.URI;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import biz.stratadigm.tpi.interactor.ThaliInteractor;
import biz.stratadigm.tpi.manager.AppSchedulers;
import biz.stratadigm.tpi.ui.view.ThaliPictureView;
import biz.stratadigm.tpi.entity.dto.ThaliDTO;

public class ThaliPicturePresenter extends BasePresenter<ThaliPictureView> {
    private static final String TAG = "TPI";
    private final ThaliInteractor thaliInteractor;

    public ThaliPicturePresenter(Context applicationContext, AppSchedulers appSchedulers, ThaliInteractor thaliInteractor) {
        super(applicationContext, appSchedulers);
        this.thaliInteractor = thaliInteractor;
    }

    public void onSubmitPictureButtonClicked(long id, Uri bitmap) {
        //File photoToSubmit = new File(URI.create(bitmap.toString()));
        try {
        //Log.v(TAG, bitmap.toString() + " " + id + getContext().getContentResolver().getType(bitmap));
        File photoToSubmit = new File(bitmap.toString());
        RequestBody requestBody = RequestBody.create(
                         MediaType.parse("image/jpeg"), photoToSubmit );
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", ""+id+".jpg", requestBody);
        Log.v(TAG, "Length: " +  requestBody.contentLength() + requestBody.contentType());
        executeRequest(thaliInteractor.submitThaliPicture(id, body), new ThaliPictureSubscriber());
        } catch (Exception e) {
            Log.v(TAG, e.toString() + e.getMessage());
        }
    }

    private class ThaliPictureSubscriber extends SimpleSubscriber<Void> {
        @Override
        public void onNext(Void aVoid) {
            getView().backToBrowse();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            if (isNetworkError(e)) {
                getView().showNetworkError();
            } else if (isHttpErrorWithCode(e, HttpURLConnection.HTTP_UNAUTHORIZED)) {
                getView().showAuthError();
            } else {
                getView().showUnexpectedError();
            }
        }
    }
}
