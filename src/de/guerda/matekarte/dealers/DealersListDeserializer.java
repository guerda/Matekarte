package de.guerda.matekarte.dealers;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class DealersListDeserializer implements JsonDeserializer<DealersList> {

  public static void main(String[] args) throws IOException {
    URL tmpUrl = new URL("http://www.matekarte.de/dealers/map");
    HttpURLConnection tmpConnection = (HttpURLConnection) tmpUrl.openConnection();
    InputStreamReader tmpReader = new InputStreamReader(tmpConnection.getInputStream());

    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(DealersList.class, new DealersListDeserializer());
    Gson gson = builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    DealersList tmpFromJson = gson.fromJson(tmpReader, DealersList.class);
    List<Dealer> tmpDealers = tmpFromJson.getDealers();
    for (Dealer tmpDealer : tmpDealers) {
      System.out.println(tmpDealer);
    }
  }

  public DealersList deserialize(JsonElement element, Type arg1, JsonDeserializationContext context) throws JsonParseException {
    JsonObject tempJsonObject = element.getAsJsonObject();
    List<Dealer> tempDealers = new ArrayList<Dealer>();
    for (Entry<String, JsonElement> entry : tempJsonObject.entrySet()) {
      String tmpId = entry.getKey();
      JsonElement tmpValue = entry.getValue();
      JsonArray tmpAsJsonArray = tmpValue.getAsJsonArray();
      String tmpName = tmpAsJsonArray.get(0).getAsString();
      double tmpLat = tmpAsJsonArray.get(1).getAsDouble();
      double tmpLon = tmpAsJsonArray.get(2).getAsDouble();
      Dealer tempDealer = new Dealer();
      tempDealer.setId(tmpId);
      tempDealer.setName(tmpName);
      tempDealer.setLat(tmpLat);
      tempDealer.setLon(tmpLon);
      tempDealers.add(tempDealer);
    }
    return new DealersList(tempDealers);
  }

}
