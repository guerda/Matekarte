package de.guerda.matekarte.dealers;

import android.app.Activity;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.format.DateUtils;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * Created by philip on 08.12.2014.
 */
public abstract class MatekarteTask<T> extends AsyncTaskLoader<T> {

  protected static final String URL_BASE = "https://www.matekarte.de/";
  private final Activity parentActivity;

  public MatekarteTask(Context context, Activity aParentActivity) {
    super(context);
    parentActivity = aParentActivity;
  }

  protected HttpClient getHttpClient() {
    HttpParams httpParams = new BasicHttpParams();
    int some_reasonable_timeout = (int) (30 * DateUtils.SECOND_IN_MILLIS);
    HttpConnectionParams.setConnectionTimeout(httpParams, some_reasonable_timeout);
    HttpConnectionParams.setSoTimeout(httpParams, some_reasonable_timeout);

    DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);

    return httpClient;
  }

  @Override
  protected abstract void onStartLoading();

  @Override
  public abstract T loadInBackground();

  protected Gson getGsonReader() {
    GsonBuilder tmpBuilder = new GsonBuilder();
    tmpBuilder.registerTypeAdapter(DealersList.class, new DealersListDeserializer());
    return tmpBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
  }

  protected void handleDownloadError(final String aText) {

    parentActivity.runOnUiThread(new Runnable() {
      public void run() {
        Context context = getContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, aText, duration);
        toast.show();
      }
    });


  }
}
