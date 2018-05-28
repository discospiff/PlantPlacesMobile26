package edu.uc.jonesbr.plantplaces;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GPSAPlant extends AppCompatActivity {

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
    }


    @OnClick(R.id.btnPause)
    public void doIt() {
        String plantName = actPlantName.getText().toString();
        String location = actLocation.getText().toString();
        Toast.makeText(this, "Plant Name: " + plantName + " Location " + location, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btnOpen)
    public void goToColorCapture() {
        // Explict intent to invoke another screen.
        Intent colorCaptureIntent = new Intent(this, ColorCaptureActivity.class);
        startActivity(colorCaptureIntent);
        // finish();
    }

}
