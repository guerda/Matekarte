package de.guerda.matekarte.common;

import android.app.Activity;

import com.octo.android.robospice.GsonGoogleHttpClientSpiceService;
import com.octo.android.robospice.SpiceManager;

/**
 * Created by iulius on 18/01/15.
 */
public class SpicedActivity extends Activity {

    private SpiceManager spiceManager = new SpiceManager(GsonGoogleHttpClientSpiceService.class);

    public SpicedActivity() {
    }

    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

}
