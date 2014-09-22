package de.guerda.matekarte.list;

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
