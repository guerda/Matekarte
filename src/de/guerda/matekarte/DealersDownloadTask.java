package de.guerda.matekarte;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class DealersDownloadTask extends AsyncTaskLoader<DealersList> {

  private static final String DEALERS_URL = "https://www.matekarte.de/dealers/map";
  private static final String LOGTAG = "Matekarte." + DealersDownloadTask.class.getSimpleName();
  private Context context;
  private DealersList dealersList;
  private Location location;

  public DealersDownloadTask(Context aContext) {
    super(aContext);
    context = aContext;
    Log.i(LOGTAG, "Creating DealersDownloadTask");
  }

  @Override
  protected void onStartLoading() {
    Log.i(LOGTAG, "onStartLoading");
    forceLoad();
    deliverResult(dealersList);
  }

  @Override
  public DealersList loadInBackground() {
    HttpURLConnection tempConnection = null;
    try {
      URL tmpUrl = new URL(DEALERS_URL + getBoundingBoxParameters());
      Log.i(LOGTAG, "Downloading Dealers '" + tmpUrl.toString() + "' ...");
      tempConnection = (HttpURLConnection) tmpUrl.openConnection();
      BufferedInputStream tmpInputStream = new BufferedInputStream(tempConnection.getInputStream());
      Reader tmpReader = new InputStreamReader(tmpInputStream);
      GsonBuilder tmpBuilder = new GsonBuilder();
      tmpBuilder.registerTypeAdapter(DealersList.class, new DealersListDeserializer());
      Gson tempGson = tmpBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
      DealersList tmpDealersList = tempGson.fromJson(tmpReader, DealersList.class);
      if (tmpDealersList == null || tmpDealersList.getDealers() == null) {
	Log.e(LOGTAG, "No dealers downloaded");
      } else {
	Log.i(LOGTAG, "Downloaded dealers: " + tmpDealersList.getDealers().size() + " dealers retrieved");
	dealersList = tmpDealersList;
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
    return dealersList;

  }

  private String getBoundingBoxParameters() {
    // TODO be more flexible
//    location.get
    return "?t=48.165856&r=11.640015&b=48.112933&l=11.525345";
  }

  public void setLocation(Location aLocation) {
    if (aLocation != null) {
      location = aLocation;
    }
  }

}
