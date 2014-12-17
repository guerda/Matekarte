package de.guerda.matekarte.list;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import de.guerda.matekarte.R;
import de.guerda.matekarte.dealers.Dealer;
import de.guerda.matekarte.dealers.DealersDownloadTask;
import de.guerda.matekarte.dealers.DealersList;
import de.guerda.matekarte.dealers.Radius;
import de.guerda.matekarte.details.DetailsActivity;

public class ListActivity extends Activity
        implements LoaderManager.LoaderCallbacks<DealersList>, LocationListener {

  private static final String LOGTAG = "ListActivity";
  private LocationManager locationManager;
  private Location lastLocation;
  private ListView listView;
  private SwipeRefreshLayout swipeRefreshLayout;

  @Override
  protected void onCreate(Bundle aSavedInstanceState) {
    super.onCreate(aSavedInstanceState);

    setContentView(R.layout.activity_list);

    lastLocation = null;

    //Initialize swipe refresh layout
    swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_list_container);
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        loadDealersInBackground();
      }
    });

    //Initialize list view
    listView = (ListView) findViewById(R.id.activity_list_list);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> aParent, View aView, int aPosition, long anId) {
        handleListViewItemOnItemClick(aParent, aView, aPosition, anId);
      }
    });

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
    Log.i(LOGTAG, "Start Refreshing");
    swipeRefreshLayout.setRefreshing(true);
    Log.i(LOGTAG, "request location updates");
    getLocationManager().requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
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

  @Override
  public void onLoadFinished(Loader<DealersList> loader, DealersList data) {
    Log.i(LOGTAG, "loader finished");
    swipeRefreshLayout.setRefreshing(false);
    if (data == null) {
      return;
    }

    DealerListAdapter tmpListAdapter = (DealerListAdapter) listView.getAdapter();
    tmpListAdapter.setLocation(lastLocation);
    tmpListAdapter.clear();
    for (Dealer tmpDealer : data.getDealers()) {
      tmpListAdapter.add(tmpDealer);
    }
    Log.i(LOGTAG, "Displaying " + data.getDealers().size() + " dealers");

    tmpListAdapter.notifyDataSetChanged();
  }

  @Override
  public void onLoaderReset(Loader<DealersList> loader) {

  }

  public void onLocationChanged(final Location pLoc) {
    Log.i(LOGTAG, "location changed");
    lastLocation = pLoc;
    getLocationManager().removeUpdates(this);

    Log.i(LOGTAG, "init loader");
    getLoaderManager().initLoader(0, null, this);

  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {

  }

  @Override
  public void onProviderEnabled(String provider) {

  }

  @Override
  public void onProviderDisabled(String provider) {

  }

  public void onStop() {
    super.onStop();
    getLocationManager().removeUpdates(this);
  }

}
