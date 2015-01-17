package de.guerda.matekarte.dealers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DealerDetailsTask extends MatekarteTask<Dealer> {

  private static final String LOGTAG = DealerDetailsTask.class.getName();
  private static final String URL_DEALER_DETAIL = "api/v2/dealers/";
  private Dealer dealer;
  private String dealerId;

  public DealerDetailsTask(Context aContext, String aDealerId, Activity aParentActivity) {
    super(aContext, aParentActivity);
    dealerId = aDealerId;
    Log.i(LOGTAG, "Creating DealersDownloadTask (" + aDealerId + ")");
  }

  @Override
  protected void onStartLoading() {
    Log.i(LOGTAG, "onStartLoading");
    forceLoad();
    deliverResult(dealer);
  }

  @Override
  public Dealer loadInBackground() {
    if (dealerId != null && dealerId.trim().length() > 0) {
      HttpURLConnection tempConnection = null;
      try {
        URL tmpUrl = new URL(URL_BASE + URL_DEALER_DETAIL + dealerId.trim());
        Log.i(LOGTAG, "Downloading Dealer details '" + tmpUrl.toString() + "' ...");
        tempConnection = (HttpURLConnection) tmpUrl.openConnection();
        BufferedInputStream tmpInputStream = new BufferedInputStream(tempConnection.getInputStream());
        Reader tmpReader = new InputStreamReader(tmpInputStream);
        GsonBuilder tmpBuilder = new GsonBuilder();
        tmpBuilder.registerTypeAdapter(Dealer.class, new DealerDetailsDeserializer());
        Gson tempGson = tmpBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Dealer tmpDealer = tempGson.fromJson(tmpReader, Dealer.class);
        if (tmpDealer == null) {
          Log.e(LOGTAG, "No dealer details downloaded");
        } else {
          Log.i(LOGTAG, "Downloaded dealer details: " + tmpDealer);
          dealer = tmpDealer;
        }

      } catch (JsonSyntaxException e) {
        Log.e(LOGTAG, "Could not download dealers, illegal format", e);
      } catch (MalformedURLException e) {
        Log.e(LOGTAG, "Could not download dealers", e);
      } catch (IOException e) {
        Log.e(LOGTAG, "Could not download dealers", e);
      } finally {
        if (tempConnection != null) {
          tempConnection.disconnect();
        }
      }
      Log.i(LOGTAG, "Downloading finished");
      return dealer;
    } else {
      return null;
    }
  }

}
