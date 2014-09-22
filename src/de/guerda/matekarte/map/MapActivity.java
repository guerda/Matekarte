package de.guerda.matekarte.map;

import de.guerda.matekarte.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.tileprovider.util.CloudmadeUtil;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.content.res.AssetManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MapActivity extends Activity implements LocationListener, LoaderCallbacks<DealersList> {

  private static final String LOGTAG = "Matekarte." + MapActivity.class.getSimpleName();

  private LocationManager mLocationManager;
  private MapView mMap;
  private MyLocationNewOverlay mMyLocationOverlay;
  // private ItemizedOverlay dealersOverlay;
  private int backButtonCount;
  private ResourceProxy resourceProxy;

  private DealersDownloadTask dealersDownloadTask;

  private String bestProvider;

  private XYTileSource lyrkTileSource;

  private LocationManager getLocationManager() {
    if (this.mLocationManager == null)
      this.mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    return this.mLocationManager;
  }

  private void initLocation() {
    getLocationManager().requestLocationUpdates(bestProvider, 2000, 20, this);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Initialize location requests
    Criteria criteria = new Criteria();
    criteria.setAccuracy(Criteria.ACCURACY_FINE);
    bestProvider = getLocationManager().getBestProvider(criteria, true);

    // Create map
    setContentView(R.layout.activity_map);
    backButtonCount = 0;

    mMap = (MapView) this.findViewById(R.id.map);
    mMap.setTileSource(getTileSource());
    mMap.setBuiltInZoomControls(true);
    mMap.setMultiTouchControls(true);

    mMap.getController().setZoom(3);
    initLocation();

    // Add your own location
    mMyLocationOverlay = new MyLocationNewOverlay(getApplicationContext(), mMap);
    mMyLocationOverlay.enableMyLocation();
    mMyLocationOverlay.setDrawAccuracyEnabled(true);

    mMap.getOverlays().add(mMyLocationOverlay);
     dealersDownloadTask = new DealersDownloadTask(getApplicationContext());

    resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());

    // Download dealers
    loadDealersInBackground();

  }

  private void loadDealersInBackground() {
    DealersDownloadTask tmpInitLoader = (DealersDownloadTask) getLoaderManager().initLoader(0, null, this);
    Location pLoc = mMyLocationOverlay.getLastFix();
    tmpInitLoader.setLocation(pLoc);
    tmpInitLoader.forceLoad();
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
    case R.id.action_locate_me:
      zoomToMyLocation();
      return true;
    case R.id.action_refresh_dealers:
      loadDealersInBackground();
      return true;
    default:
      return super.onOptionsItemSelected(anItem);
    }
  }

  private void showDealersOnMap(DealersList aDealersList) {
    Log.i(LOGTAG, "Showing dealers on map");
    if (aDealersList == null) {
      return;
    }
    List<Dealer> tmpDealers = aDealersList.getDealers();
    if (tmpDealers == null) {
      return;
    }
    ArrayList<OverlayItem> tmpMarkers = new ArrayList<OverlayItem>();
    for (Dealer tmpDealer : tmpDealers) {
      tmpMarkers.add(new OverlayItem(tmpDealer.getName(), "Test", new GeoPoint(tmpDealer.getLat(), tmpDealer.getLon())));
    }
    // TODO insert tap action
    ItemizedIconOverlay<OverlayItem> tmpDealerOverlay = new ItemizedIconOverlay<OverlayItem>(tmpMarkers, null, resourceProxy);
    mMap.getOverlays().add(tmpDealerOverlay);
  }

  private ITileSource getTileSource() {
    if (lyrkTileSource == null) {
      lyrkTileSource = new XYTileSource("Lyrk", ResourceProxy.string.unknown, 1, 18, 256, ".png?apikey=" + BuildConfig.LYRK_API_KEY,
        new String[] { "http://tiles.lyrk.org/ls/" });
    }
    return lyrkTileSource;
  }

  @Override
  protected void onResume() {
    // register location listener
    initLocation();

    super.onResume();
  }

  public void onLocationLost() {

  }

  public void onLocationChanged(final Location pLoc) {
  }

  public void zoomToMyLocation() {
    Location pLoc = mMyLocationOverlay.getLastFix();
    if (pLoc != null) {
      double tmpLatitude = pLoc.getLatitude();
      double tmpLongitude = pLoc.getLongitude();
      float tmpAccuracy = pLoc.getAccuracy();
      IMapController tmpController = mMap.getController();
      tmpController.animateTo(new GeoPoint(tmpLatitude, tmpLongitude));
      tmpController.setZoom(getZoomLevelToAccuracy(tmpAccuracy));
      Log.i(LOGTAG, "lat=" + tmpLatitude + ";lon=" + tmpLongitude + ";acc=" + tmpAccuracy);
    }
  }

  private int getZoomLevelToAccuracy(float anAccuracy) {
    Log.i(LOGTAG, "Zoom level to accuracy: " + anAccuracy);
    if (anAccuracy < 100) {
      return 16;
    } else if (anAccuracy < 1000) {
      return 5;
    }
    return 0;
  }

  @Override
  protected void onPause() {
    getLocationManager().removeUpdates(this);
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

  public void onProviderDisabled(String provider) {
    // TODO Auto-generated method stub

  }

  public void onProviderEnabled(String provider) {
    // TODO Auto-generated method stub

  }

  public void onStatusChanged(String provider, int status, Bundle extras) {
    // TODO Auto-generated method stub

  }

  public Loader<DealersList> onCreateLoader(int aId, Bundle aArgs) {
    Log.i(LOGTAG, "onCreateLoader");
    return new DealersDownloadTask(getApplicationContext());

  }

  public void onLoadFinished(Loader<DealersList> aLoader, DealersList aData) {
    showDealersOnMap(aData);
  }

  public void onLoaderReset(Loader<DealersList> aLoader) {
    // TODO clear UI

  }
}
