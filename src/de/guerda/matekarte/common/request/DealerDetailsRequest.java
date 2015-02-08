package de.guerda.matekarte.common.request;

import android.util.Log;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;

import de.guerda.matekarte.dealers.Dealer;
import de.guerda.matekarte.dealers.DealerDetailsDeserializer;

/**
 * Created by iulius on 18/01/15.
 */
public class DealerDetailsRequest extends MatekarteRequest<Dealer> {

  private static final String LOGTAG = DealerDetailsRequest.class.getSimpleName();
  private static final String URL_DEALER_DETAIL = "api/v2/dealers/";
  private Dealer dealer;
  private String dealerId;

  public DealerDetailsRequest(String dealerId) {
    super(Dealer.class);
    this.dealerId = dealerId;
  }

  @Override
  public Dealer loadDataFromNetwork() throws Exception {
    HttpRequest request = getHttpRequestFactory().buildGetRequest(new GenericUrl(URL_BASE + URL_DEALER_DETAIL + dealerId.trim()));
    GsonBuilder tmpBuilder = new GsonBuilder();
    tmpBuilder.registerTypeAdapter(Dealer.class, new DealerDetailsDeserializer());
    Gson tempGson = tmpBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    Dealer tmpDealer;
    try (InputStream in = request.execute().getContent()) {
      tmpDealer = tempGson.fromJson(new InputStreamReader(in), Dealer.class);
      if (tmpDealer == null) {
        Log.e(LOGTAG, "No dealer details downloaded");
      } else {
        Log.i(LOGTAG, "Downloaded dealer details: " + tmpDealer);
        dealer = tmpDealer;
      }
    }

    return tmpDealer;
  }
}
