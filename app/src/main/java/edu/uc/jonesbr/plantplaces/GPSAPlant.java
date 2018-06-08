package edu.uc.jonesbr.plantplaces;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GPSAPlant extends PlantPlacesActivity {

    public static final int CAMERA_PERMISSION_REQUEST_CODE = 1996;
    public static final int CAMERA_REQUEST_CODE = 1995;
    @BindView(R.id.actPlantName)
    AutoCompleteTextView actPlantName;

    @BindView(R.id.actLocation)
    AutoCompleteTextView actLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsaplant);
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

        // declare and register our BroadcastReceiver.
        BroadcastReceiver br = new SynchronizeReceiver();

        // add an intent filter to indicate which intents we are intereseted in receiving
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        this.registerReceiver(br, filter);
    }


    @OnClick(R.id.btnPause)
    public void doIt() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.gpsCoordinatorLayout), "Paused GPS", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", new UndoListener());
        snackbar.show();
    }

    class UndoListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            // this is the method that will be invoked when the user clicks on the snackbar's action
            String plantName = actPlantName.getText().toString();
            String location = actLocation.getText().toString();
            Toast.makeText(GPSAPlant.this, "Plant Name: " + plantName + " Location " + location, Toast.LENGTH_LONG).show();

        }
    }



    @OnClick(R.id.btnOpen)
    public void goToColorCapture() {



    }

    @OnClick(R.id.btnPhoto)
    public void takePhoto() {
        prepInvokeCamera();
    }

    private void prepInvokeCamera() {
        // permissions check.
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            invokeCamera();
        } else {
            String[] permissionRequest = {Manifest.permission.CAMERA};
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    private void invokeCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // path to th eimages directory.
        File imagePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // create an image file at this path.
        File picFile = new File(imagePath, getPictureName());

        // convert file to URI
        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", picFile);
        // where do I want to save the image?
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // pass permission to the camera
        cameraIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);

    }

    private String getPictureName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date now = new Date();
        String timestamp = sdf.format(now);
        // assemble a picture name
        String pictureName = "image" + timestamp + ".jpg";
        return pictureName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Toast.makeText(this, R.string.picturesaved, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            // we are hearing back from the camera.
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                invokeCamera();
            } else {
                Toast.makeText(this, R.string.nocamerapermission, Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected int getCurrentMenuId() {
        return R.id.gpsAPlant;
    }
}
