package de.guerda.matekarte.details;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import de.guerda.matekarte.R;
import de.guerda.matekarte.dealers.Dealer;
import de.guerda.matekarte.dealers.DealerDetailsTask;
import de.guerda.matekarte.dealers.DrinkStatus;
import it.sephiroth.android.library.widget.HListView;

public class DetailsActivity extends Activity implements LoaderManager.LoaderCallbacks<Dealer> {

  private static final String LOGTAG = DetailsActivity.class.getSimpleName();

  private Dealer dealer;
  private DrinkListAdapter drinkListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    Bundle tmpExtras = getIntent().getExtras();
    dealer = tmpExtras.getParcelable("message");
    TextView tmpTextView = (TextView) findViewById(R.id.detail_text);
    tmpTextView.setText(dealer.getName());

    // Get drink list adapter to later update all available drinks
    drinkListAdapter = new DrinkListAdapter(this, new ArrayList<DrinkStatus>());
    ((HListView) findViewById(R.id.detail_drinks)).setAdapter(drinkListAdapter);

    // Initialize loader to load dealers details.
    getLoaderManager().initLoader(0, null, this);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.detail, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    return super.onOptionsItemSelected(item);
  }

  public void onAddressClick(View aView) {
    Intent intent = new Intent(Intent.ACTION_VIEW);

    String label = dealer.getName();
    String uriBegin = "geo:" + dealer.getLatitude() + "," + dealer.getLongitude();
    String query = dealer.getLatitude() + "," + dealer.getLongitude() + "(" + label + ")";
    String encodedQuery = Uri.encode(query);
    String uriString = uriBegin + "?q=" + encodedQuery + "&z=10";
    Uri uri = Uri.parse(uriString);

    intent.setData(uri);
    if (intent.resolveActivity(getPackageManager()) != null) {
      startActivity(intent);
    }
  }

  public void onPhoneClick(View aView) {
    Intent intent = new Intent(Intent.ACTION_CALL);
    intent.setData(Uri.parse("tel:" + dealer.getPhone()));
  }


  @Override
  public Loader<Dealer> onCreateLoader(int id, Bundle args) {
    return new DealerDetailsTask(getApplicationContext(), dealer.getId());
  }

  @Override
  public void onLoadFinished(Loader<Dealer> aLoader, Dealer aDealer) {
    if (aDealer == null) {
      return;
    }

    dealer = aDealer;
    String tmpAddressText = String.format("%s\n%s %s\n%s", dealer.getAddress(), dealer.getZip(), dealer.getCity(), dealer.getCountry());
    ((TextView) findViewById(R.id.detail_address)).setText(tmpAddressText);
    if (dealer.getPhone() == null || dealer.getPhone().trim().length() == 0) {
      findViewById(R.id.button_phone).setVisibility(View.INVISIBLE);
    }
    ((TextView) findViewById(R.id.detail_phone)).setText(dealer.getPhone());


    drinkListAdapter.clear();
    Map<String, DrinkStatus> tmpStatusesMap = aDealer.getStatuses();
    for (String tmpDrinkId : tmpStatusesMap.keySet()) {
      drinkListAdapter.add(tmpStatusesMap.get(tmpDrinkId));
    }
    Log.i(LOGTAG, "Displaying " + aDealer.getStatuses().size() + " dealers");

    drinkListAdapter.notifyDataSetChanged();

  }

  @Override
  public void onLoaderReset(Loader<Dealer> loader) {

  }
}
