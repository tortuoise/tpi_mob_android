package biz.stratadigm.tpi.ui.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import biz.stratadigm.tpi.R;
import biz.stratadigm.tpi.ui.fragment.UploadPhotoFragment;
import biz.stratadigm.tpi.util.BitmapResizer;
import biz.stratadigm.tpi.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TakePictureActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TPI";
    public static final String ARG_ADD_PIC_THALI_ID = "THALIID";
    public long VAL_ADD_PIC_THALI_ID;
    private static final String APP_PICTURE_DIRECTORY = "/TPI";
    private static final String MIME_TYPE_IMAGE = "image/";
    private static final String FILE_SUFFIX_JPG = ".jpg";
    private static final int TAKE_PHOTO_REQUEST_CODE = 1;
    private final static String IMAGE_URI_KEY = "IMAGE_URI";
    private final static String BITMAP_WIDTH = "BITMAP_WIDTH";
    private final static String BITMAP_HEIGHT = "BITMAP_HEIGHT";

    private UploadPhotoFragment uploadFragment;
    private Uri selectedPhotoPath;
    private boolean originalImage = false;
    private boolean pictureTaken = false;

    @BindView(R.id.picture_imageview)
    ImageView takePictureImageView;
    @BindView(R.id.looking_good_textview)
    TextView lookingGoodTextView;
    //@BindView(R.id.upload_button)
    //Button nextScreenButton;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);

        ButterKnife.bind(this);

        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        takePictureImageView.setOnClickListener(this);

        //nextScreenButton.setOnClickListener(this);
        VAL_ADD_PIC_THALI_ID = getIntent().getLongExtra(ARG_ADD_PIC_THALI_ID, 1000001L);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        checkReceivedIntent();
    }

    private void takePictureWithCamera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = createImageFile(); 
        selectedPhotoPath = Uri.parse(photoFile.getAbsolutePath());
        
        captureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(captureIntent, TAKE_PHOTO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == TAKE_PHOTO_REQUEST_CODE && resultCode == RESULT_OK) 
            setImageViewWithImage();
    }

    private void setImageViewWithImage() {
        Bitmap pictureBitmap = BitmapResizer.ShrinkBitmap(selectedPhotoPath.toString(), takePictureImageView.getWidth(), takePictureImageView.getHeight());
        takePictureImageView.setImageBitmap(pictureBitmap);
        lookingGoodTextView.setVisibility(View.VISIBLE);
        pictureTaken = true;
        String absolutePath = saveImageToGallery(pictureBitmap);
        // Add fragment to activity
        try {
            uploadFragment = (UploadPhotoFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
            if (uploadFragment == null) {
                uploadFragment = new UploadPhotoFragment();
            }
            Bundle bundle = new Bundle();
            bundle.putString(UploadPhotoFragment.ARG_PICTURE_URI, absolutePath);
            bundle.putLong(UploadPhotoFragment.ARG_THALI_ID, VAL_ADD_PIC_THALI_ID);
            uploadFragment.setArguments(bundle);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), uploadFragment, R.id.contentFrame);
        } catch (Exception e) {
                Log.v(TAG,  "add UploadPhotoFragment to TakePictureActivity " + e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.thalis, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.picture_imageview:
                takePictureWithCamera();
                break;

            //case R.id.button_submit:
            //    moveToNextScreen(v);
            //    Log.v(TAG, "Submit button ");
            //    break;

            default:
                break;
        }
    }

    private File createImageFile() {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES + APP_PICTURE_DIRECTORY);
        storageDir.mkdirs();

        File imageFile = null;

        try {
            imageFile = File.createTempFile(
                    imageFileName,  /* prefix */
                    FILE_SUFFIX_JPG,         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageFile;
    }

    private Uri getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return Uri.parse(result);
    }

    private void moveToNextScreen(View view) {
        /*if (pictureTaken) {
            Toast.makeText(this, R.string.save_image, Toast.LENGTH_LONG).show();
            //Intent nextScreenIntent = new Intent(this, AddEditThaliActivity.class);
            //nextScreenIntent.putExtra(BITMAP_WIDTH, takePictureImageView.getWidth());
            //nextScreenIntent.putExtra(BITMAP_HEIGHT, takePictureImageView.getHeight());
            //startActivity(nextScreenIntent);
        } else {
            Toast.makeText(this, R.string.select_a_picture, Toast.LENGTH_LONG).show();
        }*/
        uploadFragment.onClick(view);
    }

    private void checkReceivedIntent() {
        Intent imageReceivedIntent = getIntent();
        String intentAction = imageReceivedIntent.getAction();
        String intentType = imageReceivedIntent.getType();

        if(Intent.ACTION_SEND.equals(intentAction) && intentType != null) {
            Uri contentUri = imageReceivedIntent.getParcelableExtra(Intent.EXTRA_STREAM);
            selectedPhotoPath = getRealPathFromURI(contentUri);
            setImageViewWithImage();    
        }
    }

    private String saveImageToGallery(Bitmap memeBitmap) {

        if (!originalImage) {

            // save bitmap to file
            File imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + APP_PICTURE_DIRECTORY), memeBitmap + FILE_SUFFIX_JPG);

            try {
                // create outputstream, compress image and write to file, flush and close outputstream
                FileOutputStream fos = new FileOutputStream(imageFile);
                memeBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                Toast.makeText(this, getResources().getText(R.string.save_image_failed).toString(), Toast.LENGTH_SHORT).show();
            }

            // Create intent to request newly created file to be scanned, pass picture uri and broadcast intent
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(Uri.fromFile(imageFile));
            sendBroadcast(mediaScanIntent);

            Toast.makeText(this, getResources().getText(R.string.save_image_succeeded).toString(), Toast.LENGTH_SHORT).show();
            return imageFile.getAbsolutePath();

        } else {
            Toast.makeText(this, getResources().getText(R.string.add_meme_message).toString(), Toast.LENGTH_SHORT).show();
            return "";
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
