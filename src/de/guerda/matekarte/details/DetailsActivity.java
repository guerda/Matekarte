package de.guerda.matekarte.details;

import android.net.Uri;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import de.guerda.matekarte.R;

public class DetailsActivity extends Activity {

    private String dealerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent tmpIntent = getIntent();
        dealerText = tmpIntent.getStringExtra("message");
        TextView tmpTextView = (TextView) findViewById(R.id.detail_text);
        tmpTextView.setText(dealerText);
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
      intent.setData(Uri.parse(dealerText));
      if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
      }
    }


}
