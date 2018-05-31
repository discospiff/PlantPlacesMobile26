package edu.uc.jonesbr.plantplaces;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.uc.jonesbr.plantplaces.dto.PlantDTO;

public class ColorCaptureActivity extends PlantPlacesActivity {

    public static final int CAMERA_REQUEST_CODE = 1997;
    public static final int GALLERY_REQUEST_CODE = 1998;

    @BindView(R.id.imgThumbnail)
    ImageView imgThumbnail;
    private Uri selectedImage;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_capture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected int getCurrentMenuId() {
        return R.id.capturecolor;
    }

    /**
     * Show the camera when clicked.
     */
    @OnClick(R.id.btnTakePhoto)
    public void btnTakePhotoClicked() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @OnClick(R.id.btnOpenGallery)
    public void btnOpenGalleryClicked() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        File filePictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Uri data = Uri.parse(filePictureDirectory.getPath());
        String type = "image/*";
        intent.setDataAndType(data, type);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // if we are here , the user selected OK
            if (requestCode == CAMERA_REQUEST_CODE) {
                // if we are here, we are hearing back from the camera.
                Object returnedObject = data.getExtras().get("data");

                if (returnedObject instanceof Bitmap) {
                    /// the object actually is a bitmap; safe to cast.
                    Bitmap bmpImage = (Bitmap) returnedObject;

                    imgThumbnail.setImageBitmap(bmpImage);
                }
            } else if (requestCode == GALLERY_REQUEST_CODE) {
                selectedImage = data.getData();
                
                openImage();

            }

        }
    }

    private void openImage() {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImage);
            image = BitmapFactory.decodeStream(inputStream);
            imgThumbnail.setImageBitmap(image);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Unable to retrieve image", Toast.LENGTH_LONG).show();
        }
    }

}
