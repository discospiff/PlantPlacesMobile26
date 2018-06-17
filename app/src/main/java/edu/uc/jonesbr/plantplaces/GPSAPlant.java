package edu.uc.jonesbr.plantplaces;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
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
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GPSAPlant extends PlantPlacesActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final int CAMERA_PERMISSION_REQUEST_CODE = 1996;
    public static final int CAMERA_REQUEST_CODE = 1995;
    public static final int ONE_MINUTE = 60000;
    public static final int ACCESS_FINE_LOCATION_REQUEST_CODE = 1995;
    @BindView(R.id.actPlantName)
    AutoCompleteTextView actPlantName;

    @BindView(R.id.actLocation)
    AutoCompleteTextView actLocation;

    @BindView(R.id.txtLatitude)
    TextView txtLongitude;

    @BindView(R.id.txtLongitude)
    TextView txtLatitude;

    @BindView(R.id.chronGPS)
    Chronometer chronoGPS;

    @BindView(R.id.btnPause)
    ImageButton btnPause;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private double longitude;
    private double latitude;
    private boolean updatesRequested = true;

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

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        locationRequest = new LocationRequest();
        locationRequest.setInterval(ONE_MINUTE);
        locationRequest.setFastestInterval(ONE_MINUTE/4);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }


    @OnClick(R.id.btnPause)
    public void toggleUpdates() {
        if (updatesRequested) {
            removeLocationUpdates();
            updatesRequested = false;
            // change the icon to indicate that location updates are playable.
            btnPause.setImageDrawable(getDrawable(R.drawable.ic_play));
        } else {
            prepRequestLocationUpdates();
            updatesRequested = true;
            // show a pause icon
            btnPause.setImageDrawable(getDrawable(R.drawable.ic_pause));
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        prepRequestLocationUpdates();
    }

    private void prepRequestLocationUpdates() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // if the user has already given us this permission, then request updates.
            requestLocationUpdates();
        } else {
            // the user has revoked permission, or never gave us permission, so let's request it.
            String [] permissionRequest = {Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissionRequest, ACCESS_FINE_LOCATION_REQUEST_CODE);
        }

    }

    private void requestLocationUpdates() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        chronoGPS.stop();
        chronoGPS.setBase(SystemClock.elapsedRealtime());
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        txtLatitude.setText(Double.toString(latitude));
        txtLongitude.setText(Double.toString(longitude));
        // restart the chronometer.
        chronoGPS.start();
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
        } else if (requestCode == ACCESS_FINE_LOCATION_REQUEST_CODE) {
            // if we are here, we are hearing back from the location permission.
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // user gave us permission.
                requestLocationUpdates();
            } else {
                Toast.makeText(this, R.string.nogpspermission, Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected int getCurrentMenuId() {
        return R.id.gpsAPlant;
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        prepRequestLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeLocationUpdates();
    }

    private void removeLocationUpdates() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }
}
