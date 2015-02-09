package de.guerda.matekarte.common.request;

import android.util.Log;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.UrlEncodedContent;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import de.guerda.matekarte.dealers.Dealer;
import de.guerda.matekarte.dealers.DealerDetailsDeserializer;
import de.guerda.matekarte.dealers.DrinkStatus;
import de.guerda.matekarte.dealers.DrinkStatusEnum;

/**
 * Created by philip on 03/02/15.
 */
public class DrinkStatusUpdateRequest extends MatekarteRequest<DrinkStatus> {

    private static final String LOGTAG = DrinkStatusUpdateRequest.class.getName();
    private static final String URL_STATUS_UPDATE = "api/v2/statuses";
    private String dealerId;
    private String drinkId;
    private DrinkStatusEnum drinkStatus;

    public DrinkStatusUpdateRequest(String aDealerId, DrinkStatusEnum aDrinkStatus) {
        super(DrinkStatus.class);
        dealerId = aDealerId;
        drinkStatus = aDrinkStatus;
    }


    /**
     * <pre>
     * status[drink_id]=50fb3d16ce007c40fc00080d&status[dealer_id]=52cd8b5e7a58b40eae004e40&status[status]=1
     * </pre>
     */
    @Override
    public DrinkStatus loadDataFromNetwork() throws Exception {
        Map<String, String> tempParameters = new HashMap<>();
        tempParameters.put("status[drink_id]", drinkId);
        tempParameters.put("status[dealer_id]",dealerId);
        tempParameters.put("status[status]",drinkStatus.getStatusId());

        HttpContent tempContent = new UrlEncodedContent(tempParameters);
        HttpRequest request = getHttpRequestFactory().buildPostRequest(new GenericUrl(URL_BASE + URL_STATUS_UPDATE), tempContent);
        GsonBuilder tmpBuilder = new GsonBuilder();
        tmpBuilder.registerTypeAdapter(Dealer.class, new DealerDetailsDeserializer());
        Gson tempGson = tmpBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        DrinkStatus tmpDealer;
        try (InputStream in = request.execute().getContent()) {
            tmpDealer = tempGson.fromJson(new InputStreamReader(in), DrinkStatus.class);
            if (tmpDealer == null) {
                Log.e(LOGTAG, "No dealer details downloaded");
            } else {
                Log.i(LOGTAG, "Downloaded dealer details: " + tmpDealer);
            }
        }

        return tmpDealer;
    }
}
