package de.guerda.matekarte.common.request;

import android.location.Location;
import android.util.Log;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import de.guerda.matekarte.dealers.DealersList;
import de.guerda.matekarte.dealers.DealersListDeserializer;
import de.guerda.matekarte.dealers.Radius;
import de.janmatuschek.GeoLocation;

/**
 * Created by iulius on 18/01/15.
 */
public class DealersDownloadRequest extends MatekarteRequest<DealersList> {

  private static final String LOGTAG = DealersDownloadRequest.class.getSimpleName();
  private static final double EARTH_RADIUS = 6371.01;
  private static final String URL_DEALERS = "dealers/map";
  private final DealersListDeserializer dealersListDeserializer = new DealersListDeserializer();
  private final Location location;
  private final Radius radius;
  private DealersList dealersList;

  public DealersDownloadRequest(Location location, Radius radius) {
    super(DealersList.class);
    this.location = location;
    this.radius = radius;
    Log.i(LOGTAG, "Creating DealersDownloadRequest");
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

  @Override
  public DealersList loadDataFromNetwork() throws Exception {
    String url = URL_BASE + URL_DEALERS + getBoundingBoxParameters();
    HttpRequest request = getHttpRequestFactory().buildGetRequest(new GenericUrl(url));
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(DealersList.class, dealersListDeserializer);
    Gson gson = gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    DealersList result;
    try (InputStream in = request.execute().getContent()) {
      result = gson.fromJson(new InputStreamReader(in), DealersList.class);
    } catch (Exception e) {
      throw e;
    }
    return result;
  }
}
