package de.guerda.matekarte.dealers;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import de.janmatuschek.GeoLocation;

public class DealersDownloadTask extends MatekarteTask<DealersList> {

  private static final String LOGTAG = DealersDownloadTask.class.getName();
  private static final double EARTH_RADIUS = 6371.01;
  private static final String URL_DEALERS = "dealers/map";
  private final Radius radius;
  private DealersList dealersList;
  private Location location;

  public DealersDownloadTask(Context aContext, Location aLocation, Radius aRadius, Activity aParentActivity) {
    super(aContext, aParentActivity);
    location = aLocation;
    radius = aRadius;
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
    try {
      //TODO reactivate bounding box parameters, but currently, they are not supported
      String tmpUrl = URL_BASE + URL_DEALERS; //+ getBoundingBoxParameters();
      HttpGet tmpRequest = new HttpGet(tmpUrl);

      Log.i(LOGTAG, "Downloading Dealers '" + tmpUrl.toString() + "' ...");
      tmpRequest.addHeader("Accept", "application/json");
      HttpResponse tmpResponse = getHttpClient().execute(tmpRequest);
      Header[] tmpAllHeaders = tmpRequest.getAllHeaders();
      for (Header tmpHeader : tmpAllHeaders) {
        Log.i(LOGTAG, "Header " + tmpHeader.getName() + ": " + tmpHeader.getValue());
      }
      StatusLine tmpStatusLine = tmpResponse.getStatusLine();

      if (tmpStatusLine.getStatusCode() == HttpStatus.SC_OK) {
        HttpEntity tmpEntity = tmpResponse.getEntity();
        InputStream tmpContent = tmpEntity.getContent();


        BufferedInputStream tmpInputStream = new BufferedInputStream(tmpContent);
        Reader tmpReader = new InputStreamReader(tmpInputStream);
        Gson tempGson = getGsonReader();
        DealersList tmpDealersList = tempGson.fromJson(tmpReader, DealersList.class);
        if (tmpDealersList == null || tmpDealersList.getDealers() == null) {
          Log.e(LOGTAG, "No dealers downloaded");
        } else {
          Log.i(LOGTAG, "Downloaded dealers: " + tmpDealersList.getDealers().size() + " dealers retrieved");
          dealersList = tmpDealersList;
        }
      } else {
        Log.e(LOGTAG, "Received error response from server: " + tmpStatusLine.getStatusCode());
        handleDownloadError("Received error response from server: " + tmpStatusLine.getStatusCode());
      }
    } catch (JsonSyntaxException e) {
      handleDownloadError("Could not download dealers, illegal format");
      Log.e(LOGTAG, "Could not download dealers, illegal format", e);
    } catch (MalformedURLException e) {
      handleDownloadError("Could not download dealers");
      Log.e(LOGTAG, "Could not download dealers", e);
    } catch (IOException e) {
      handleDownloadError("Could not download dealers");
      Log.e(LOGTAG, "Could not download dealers", e);
    } finally {

    }
    Log.i(LOGTAG, "Downloading finished");
    sortDealersListByDistance(dealersList, location);
    return dealersList;

  }

  private String getBoundingBoxParameters() {
    double tmpLat = location.getLatitude();
    double tmpLon = location.getLongitude();
    GeoLocation tmpGeoLocation = GeoLocation.fromDegrees(tmpLat, tmpLon);
    GeoLocation[] tmpBoundingBoxLocations = tmpGeoLocation.boundingCoordinates(radius.getKilometers(), EARTH_RADIUS);


    double[] tmpBoundingBox = new double[]{tmpBoundingBoxLocations[0].getLatitudeInDegrees(), tmpBoundingBoxLocations[0].getLongitudeInDegrees(), tmpBoundingBoxLocations[1].getLatitudeInDegrees(), tmpBoundingBoxLocations[1].getLongitudeInDegrees()};
    return getBoundingBoxParameters(tmpBoundingBox);
  }

  private String getBoundingBoxParameters(double[] aBoundingBox) {
    //example: "?t=48.165856&r=11.640015&b=48.112933&l=11.525345";
    //0: MINLAT --> bottom
    //1: MINLON --> left
    //2: MAXLAT --> top
    //3: MAXLON --> right
    return String.format(Locale.US, "?b=%f&l=%f&t=%f&r=%f", aBoundingBox[0], aBoundingBox[1], aBoundingBox[2], aBoundingBox[3]);
  }

  private void sortDealersListByDistance(DealersList aList, final Location aLocation) {
    if (aList == null || aList.getDealers() == null) {
      return;
    }
    Comparator<Dealer> tmpDistanceComparator = new Comparator<Dealer>() {
      @Override
      public int compare(Dealer aDealer1, Dealer aDealer2) {
        float tmpDistance1 = calculateDistance(aLocation, aDealer1);
        float tmpDistance2 = calculateDistance(aLocation, aDealer2);
        return (int) (tmpDistance1 - tmpDistance2);
      }
    };
    Collections.sort(aList.getDealers(), tmpDistanceComparator);
  }

  private float calculateDistance(Location aLocation, Dealer aDealer) {
    Location tmpLocation = new Location("FAKE");
    tmpLocation.setLatitude(aDealer.getLatitude());
    tmpLocation.setLongitude(aDealer.getLongitude());
    float tmpDistance = tmpLocation.distanceTo(location);
    return tmpDistance;
  }

}
