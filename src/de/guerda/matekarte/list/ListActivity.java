package de.guerda.matekarte.list;

import de.guerda.matekarte.dealers.Dealer;
import de.guerda.matekarte.dealers.DealersList;
import de.guerda.matekarte.dealers.DealersDownloadTask;


import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.guerda.matekarte.R;
import de.guerda.matekarte.dealers.Dealer;
import de.guerda.matekarte.dealers.DealersDownloadTask;
import de.guerda.matekarte.dealers.DealersList;
import de.guerda.matekarte.dealers.Radius;
import de.guerda.matekarte.details.DetailsActivity;

public class ListActivity extends Activity implements LoaderManager.LoaderCallbacks<DealersList>, LocationListener {

    private static final String LOGTAG = "Matekarte.ListActivity";
    private List<Dealer> dealers;
    private ArrayAdapter<Dealer> listAdapter;
    private String bestProvider;
    private LocationManager locationManager;
    private Location lastLocation;

    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(R.layout.activity_list);

        lastLocation = null;

        // Initialize location requests
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        bestProvider = getLocationManager().getBestProvider(criteria, true);
        initLocation();

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

    @Override
    protected void onPause() {
      getLocationManager().removeUpdates(this);
      super.onPause();
    }


    private void loadDealersInBackground() {
        initLocation();
        DealersDownloadTask tmpLoader = (DealersDownloadTask) getLoaderManager().initLoader(0, null, this);
        if(tmpLoader != null) {
          tmpLoader.forceLoad();
        }
    }

    private void handleListViewItemOnItemClick(AdapterView<?> parent, View v, int position, long id) {
        Object tmpSelectedItem = parent.getItemAtPosition(position);

        Intent tmpIntent = new Intent(this, DetailsActivity.class);
        tmpIntent.putExtra("message", tmpSelectedItem.toString());
        startActivity(tmpIntent);
    }


    @Override
    public Loader<DealersList> onCreateLoader(int id, Bundle args) {
        if(lastLocation == null) {
          Toast.makeText(getApplicationContext(), "No location found", Toast.LENGTH_LONG).show();
          lastLocation = getLocationManager().getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        Log.i(LOGTAG, "onCreateLoader  " + lastLocation + " ONE KILOMETER");
        return new DealersDownloadTask(getApplicationContext(), lastLocation, Radius.ONE_KILOMETER);
    }

    private LocationManager getLocationManager() {
      if (locationManager == null)
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
      return locationManager;
    }

    private void initLocation() {
      getLocationManager().requestLocationUpdates(bestProvider, 2000, 20, this);
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

  public void onLocationLost() {

  }

  public void onLocationChanged(final Location pLoc) {
    lastLocation = pLoc;
    getLocationManager().removeUpdates(this);
  }
  public void onProviderDisabled(String provider) {
    // TODO Auto-generated method stub
  }
    
  public void onProviderEnabled(String provider) {
    // TODO Auto-generated method stub
  }
    
  public void onStatusChanged(String provider, int status, Bundle extras) {
   // TODO Auto-generated method stub
  }
    
}
