package de.guerda.matekarte.dealers;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.location.Location;
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

import de.janmatuschek.GeoLocation;

public class DealersDownloadTask extends AsyncTaskLoader<DealersList> {

    private static final String DEALERS_URL = "https://www.matekarte.de/dealers/map";
    private static final String LOGTAG = "Matekarte." + DealersDownloadTask.class.getSimpleName();
    private static final double EARTH_RADIUS = 6371.01;
    private final Radius radius;
    private Context context;
    private DealersList dealersList;
    private Location location;

    public DealersDownloadTask(Context aContext, Location aLocation, Radius aRadius) {
        super(aContext);
        context = aContext;
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
        return String.format("?b=%f&l=%f&t=%f&r=%f", aBoundingBox[0], aBoundingBox[1], aBoundingBox[2], aBoundingBox[3]);
    }

}
