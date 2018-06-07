package edu.uc.jonesbr.plantplaces;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by ucint on 6/6/2018.
 */

public class SynchronizeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // find out who called us.
        String action = intent.getAction();

        // is it power?
        if(action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            Toast.makeText(context, R.string.lowpower, Toast.LENGTH_LONG).show();
        } else if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
            Toast.makeText(context, R.string.enhancedmode, Toast.LENGTH_LONG).show();
        }
    }
}
