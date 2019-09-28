package edu.uc.jonesbr.plantplaces;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by ucint on 5/27/2018.
 */

abstract class PlantPlacesActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gpsaplant, menu);
        int currentMenuId = getCurrentMenuId();
        if (currentMenuId != 0) {
            menu.removeItem(currentMenuId);
        }
        return super.onCreateOptionsMenu(menu);
    }

    protected abstract int getCurrentMenuId();

    /**
     * This will be invoked when the user clicks the GPS menu
     * @param menuItem
     */
    public void gpsAPlantClicked(MenuItem menuItem) {
        Intent gpsAPlantIntent = new Intent(this, GPSAPlant.class);
        startActivity(gpsAPlantIntent);
        finish();

    }

    public void searchByColorClicked(MenuItem menuItem) {
        // Explict intent to invoke another screen.
        Intent colorCaptureIntent = new Intent(this, ColorCaptureActivity.class);
        startActivity(colorCaptureIntent);
        // finish();
    }
}
