package de.guerda.matekarte.list;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.guerda.matekarte.R;
import de.guerda.matekarte.dealers.Dealer;
import de.guerda.matekarte.dealers.DealersDownloadTask;
import de.guerda.matekarte.dealers.DealersList;
import de.guerda.matekarte.dealers.Radius;
import de.guerda.matekarte.details.DetailsActivity;

public class ListActivity extends Activity
        implements LoaderManager.LoaderCallbacks<DealersList>, LocationListener {

  private static final String LOGTAG = "Matekarte.ListActivity";
  private List<Dealer> dealers;
  private String bestProvider;
  private LocationManager locationManager;
  private Location lastLocation;
  private ListView listView;

  @Override
  protected void onCreate(Bundle aSavedInstanceState) {
    super.onCreate(aSavedInstanceState);
    setContentView(R.layout.activity_list);

    lastLocation = null;

    //Initialize list view
    listView = (ListView) findViewById(R.id.activity_list_list);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> aParent, View aView, int aPosition, long anId) {
        handleListViewItemOnItemClick(aParent, aView, aPosition, anId);
      }
    });

    // Initialize location requests
    Criteria criteria = new Criteria();
    criteria.setAccuracy(Criteria.ACCURACY_FINE);
    bestProvider = getLocationManager().getBestProvider(criteria, true);
    initLocation();

    DealerListAdapter tmpDealerListAdapter = new DealerListAdapter(this, new ArrayList<Dealer>());
    listView.setAdapter(tmpDealerListAdapter);

    // Load dealers initially
    loadDealersInBackground();
  }

  @Override
  protected void onPause() {
    getLocationManager().removeUpdates(this);
    super.onPause();
  }


  private void loadDealersInBackground() {
    initLocation();
    DealersDownloadTask tmpLoader = (DealersDownloadTask) getLoaderManager().initLoader(0, null, this);
    if (tmpLoader != null) {
      tmpLoader.forceLoad();
    }

  }

  private void handleListViewItemOnItemClick(AdapterView<?> parent, View v, int position, long id) {
    Dealer tmpSelectedItem = (Dealer) parent.getItemAtPosition(position);

    Intent tmpIntent = new Intent(this, DetailsActivity.class);
    tmpIntent.putExtra("message", tmpSelectedItem);
    startActivity(tmpIntent);
  }


  @Override
  public Loader<DealersList> onCreateLoader(int id, Bundle args) {
    if (lastLocation == null) {
      lastLocation = getLocationManager().getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }
    Log.i(LOGTAG, "onCreateLoader  " + lastLocation + " TWO KILOMETERS");
    return new DealersDownloadTask(getApplicationContext(), lastLocation, Radius.TWO_KILOMETERS);
  }

  private LocationManager getLocationManager() {
    if (locationManager == null)
      locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    return locationManager;
  }

  private void initLocation() {
    getLocationManager().requestLocationUpdates(bestProvider, 1, 0.1F, this);
  }

  @Override
  public void onLoadFinished(Loader<DealersList> loader, DealersList data) {
    if (data == null) {
      Log.e(LOGTAG, "Empty result set returned");
      return;
    }
    DealerListAdapter tmpListAdapter = (DealerListAdapter) listView.getAdapter();
    tmpListAdapter.setLocation(lastLocation);
    tmpListAdapter.clear();
    tmpListAdapter.addAll(data.getDealers());
    Log.i(LOGTAG, "Displaying " + data.getDealers().size() + " dealers");

    setContentView(R.layout.activity_list);
    tmpListAdapter.notifyDataSetChanged();
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

  public void onLocationLost() {

  }

  public void onLocationChanged(final Location pLoc) {
    lastLocation = pLoc;
    getLocationManager().removeUpdates(this);
  }

  public void onProviderDisabled(String provider) {
  }

  public void onProviderEnabled(String provider) {
  }

  public void onStatusChanged(String provider, int status, Bundle extras) {
  }

}
