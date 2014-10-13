package de.guerda.matekarte.list;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.guerda.matekarte.R;
import de.guerda.matekarte.dealers.Dealer;
import de.guerda.matekarte.dealers.DealersDownloadTask;
import de.guerda.matekarte.dealers.DealersList;
import de.guerda.matekarte.dealers.Radius;
import de.guerda.matekarte.details.DetailsActivity;

public class ListActivity extends Activity implements LoaderManager.LoaderCallbacks<DealersList> {

    private static final String LOGTAG = "Matekarte.ListActivity";
    private List<Dealer> dealers;
    private ArrayAdapter<Dealer> listAdapter;
    private String bestProvider;
    private LocationManager mLocationManager;

    private LocationManager getLocationManager() {
        if (this.mLocationManager == null)
            this.mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return this.mLocationManager;
    }

    private void initLocation() {
        //getLocationManager().requestLocationUpdates(bestProvider, 2000, 20, this);
    }

    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(R.layout.activity_list);

        // Initialize location requests
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        bestProvider = getLocationManager().getBestProvider(criteria, true);

        ListView tmpListView = (ListView) findViewById(R.id.list_mate_dealers);

        dealers = new ArrayList<Dealer>();

        listAdapter = new ArrayAdapter<Dealer>(this, android.R.layout.simple_list_item_1, dealers);
        tmpListView.setAdapter(listAdapter);

        tmpListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleListViewItemOnItemClick(parent, view, position, id);
            }
        });

        loadDealersInBackground();
    }

    private void loadDealersInBackground() {
        DealersDownloadTask tmpInitLoader = (DealersDownloadTask) getLoaderManager().initLoader(0, null, this);
        tmpInitLoader.forceLoad();
    }

    private void handleListViewItemOnItemClick(AdapterView<?> parent, View v, int position, long id) {
        Object tmpSelectedItem = parent.getItemAtPosition(position);

        Intent tmpIntent = new Intent(this, DetailsActivity.class);
        tmpIntent.putExtra("message", tmpSelectedItem.toString());
        startActivity(tmpIntent);
    }


    @Override
    public Loader<DealersList> onCreateLoader(int id, Bundle args) {
        Location tmpLocation = new Location("fake");
        tmpLocation.setLatitude(51.2168);
        tmpLocation.setLongitude(6.7959);
        Log.i(LOGTAG, "onCreateLoader  " + tmpLocation + " ONE KILOMETER");
        return new DealersDownloadTask(getApplicationContext(), tmpLocation, Radius.ONE_KILOMETER);
    }

    @Override
    public void onLoadFinished(Loader<DealersList> loader, DealersList data) {
        if (data == null) {
            Log.e(LOGTAG, "Empty result set returned");
            return;
        }
        listAdapter.clear();
        for(Dealer tmpDealer : data.getDealers()) {
            listAdapter.add(tmpDealer);
        }
        Log.i(LOGTAG, "Displaying " + dealers.size() + " dealers");
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<DealersList> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem anItem) {
        switch (anItem.getItemId()) {
            case R.id.action_refresh_dealers:
                loadDealersInBackground();
                return true;
            default:
                return super.onOptionsItemSelected(anItem);
        }
    }
=======
import de.guerda.matekarte.*;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ListActivity extends Activity {

  private static final String LOGTAG = "Matekarte." + ListActivity.class.getSimpleName();

  private int backButtonCount;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    backButtonCount = 0;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem anItem) {
//    switch (anItem.getItemId()) {
//    case R.id.action_locate_me:
//      zoomToMyLocation();
//      return true;
//    case R.id.action_refresh_dealers:
//      loadDealersInBackground();
//      return true;
//    default:
      return super.onOptionsItemSelected(anItem);
//    }
//    
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
  }

  /**
   * Back button listener. Will close the application if the back button pressed
   * twice.
   */
  public void onBackPressed() {
    if (backButtonCount >= 1) {
      // Intent intent = new Intent(Intent.ACTION_MAIN);
      // intent.addCategory(Intent.CATEGORY_HOME);
      // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      // startActivity(intent);
      finish();
    } else {
      Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_LONG).show();
      backButtonCount++;
    }
  }

}
