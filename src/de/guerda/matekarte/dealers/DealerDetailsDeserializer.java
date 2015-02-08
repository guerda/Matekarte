package de.guerda.matekarte.dealers;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by philip on 08.12.2014.
 */
public class DealerDetailsDeserializer implements JsonDeserializer<Dealer> {

  private static final String LOGTAG = DealerDetailsDeserializer.class.getName();

  public static void main(String[] args) {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Dealer.class, new DealerDetailsDeserializer());
    Gson gson = builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    Dealer tmpFromJson = gson.fromJson("{\"dealer\":{\"name\":\"Chaosdorf\",\"address\":\"Hüttenstraße 25\",\"city\":\"Düsseldorf\",\"zip\":\"40215\",\"country\":\"Deutschland\",\"www\":\"https://chaosdorf.de\",\"note\":\"\",\"phone\":\"\",\"type\":\"3\",\"id\":\"51f22b147a58b4f6a5000595\",\"longitude\":51.21660900000001,\"latitude\":6.7836145,\"drink_ids\":[\"50fb3d16ce007c40fc00080e\",\"50fb3d16ce007c40fc00080f\",\"50fb3d16ce007c40fc000812\",\"50fb3d16ce007c40fc000810\",\"50fb3d16ce007c40fc000811\",\"50fb3d16ce007c40fc00080d\"],\"status_ids\":[\"53de3c9c7a58b4ce6c000001\"]},\"statuses\":[{\"status\":1,\"id\":\"53de3c9c7a58b4ce6c000001\",\"drink_id\":\"50fb3d16ce007c40fc00080f\",\"dealer_id\":\"51f22b147a58b4f6a5000595\"}]}", Dealer.class);
    System.out.println("Result: " + tmpFromJson);
  }


  @Override
  public Dealer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    JsonObject tmpJsonObject = jsonElement.getAsJsonObject();
    JsonObject tmpDealerElement = tmpJsonObject.get("dealer").getAsJsonObject();

    Dealer tmpDealer = new Dealer();

    JsonElement tmpId = tmpDealerElement.get("id");
    if (tmpId != null && !(tmpId instanceof JsonNull)) {
      tmpDealer.setId(tmpId.getAsString());
    }

    JsonElement tmpName = tmpDealerElement.get("name");
    if (tmpName != null && !(tmpName instanceof JsonNull)) {
      tmpDealer.setName(tmpName.getAsString());
    }

    JsonElement tmpAddress = tmpDealerElement.get("address");
    if (tmpAddress != null && !(tmpAddress instanceof JsonNull)) {
      tmpDealer.setAddress(tmpAddress.getAsString());
    }

    JsonElement tmpCity = tmpDealerElement.get("city");
    if (tmpCity != null && !(tmpCity instanceof JsonNull)) {
      tmpDealer.setCity(tmpCity.getAsString());
    }

    JsonElement tmpZip = tmpDealerElement.get("zip");
    if (tmpZip != null && !(tmpZip instanceof JsonNull)) {
      tmpDealer.setZip(tmpZip.getAsString());
    }

    JsonElement tmpCountry = tmpDealerElement.get("country");
    if (tmpCountry != null && !(tmpCountry instanceof JsonNull)) {
      tmpDealer.setCountry(tmpCountry.getAsString());
    }

    JsonElement tmpWww = tmpDealerElement.get("www");
    if (tmpWww != null && !(tmpWww instanceof JsonNull)) {
      tmpDealer.setWww(tmpWww.getAsString());
    }

    JsonElement tmpNote = tmpDealerElement.get("note");
    if (tmpNote != null && !(tmpNote instanceof JsonNull)) {
      tmpDealer.setNote(tmpNote.getAsString());

    }

    JsonElement tmpPhone = tmpDealerElement.get("phone");
    if (tmpPhone != null && !(tmpPhone instanceof JsonNull)) {
      tmpDealer.setPhone(tmpPhone.getAsString());
    }

    JsonElement tmpType = tmpDealerElement.get("type");
    if (tmpType != null && !(tmpType instanceof JsonNull)) {
      tmpDealer.setType(tmpType.getAsInt());
    }

    JsonElement tmpLongitude = tmpDealerElement.get("longitude");
    if (tmpLongitude != null && !(tmpLongitude instanceof JsonNull)) {
      // This confusion of Latitude and Longitude is intentional, as the details api mixed up both. I correct it here.
      //TODO Fix this mix up, when Pascal fixed the bug in the API
      tmpDealer.setLatitude(tmpLongitude.getAsDouble());
    }

    JsonElement tmpLatitude = tmpDealerElement.get("latitude");
    if (tmpLatitude != null && !(tmpLatitude instanceof JsonNull)) {
      // This confusion of Latitude and Longitude is intentional, as the details api mixed up both. I correct it here.
      //TODO Fix this mix up, when Pascal fixed the bug in the API
      tmpDealer.setLongitude(tmpLatitude.getAsDouble());
    }

    HashMap<String, DrinkStatus> tmpStatusMap = new HashMap<>();

    JsonArray tmpDrinksElement = tmpDealerElement.get("drink_ids").getAsJsonArray();
    for (JsonElement tmpEntry : tmpDrinksElement) {
      tmpStatusMap.put(tmpEntry.getAsString(), null);
    }


    JsonArray tmpStatusesElement = tmpJsonObject.get("statuses").getAsJsonArray();

    for (JsonElement tmpEntry : tmpStatusesElement) {
      JsonObject tmpValue = tmpEntry.getAsJsonObject();
      DrinkStatus tmpDrinkStatus = new DrinkStatus();

      JsonElement tmpStatus = tmpValue.get("status");
      if (tmpStatus != null) {
        int tmpStatusId = tmpStatus.getAsInt();
        tmpDrinkStatus.setStatus(tmpStatusId);
      }

      JsonElement tmpDrinkStatusId = tmpValue.get("id");
      if (tmpDrinkStatusId != null) {
        String tmpIdString = tmpDrinkStatusId.getAsString();
        tmpDrinkStatus.setId(tmpIdString);
      }

      JsonElement tmpDrinkId = tmpValue.get("drink_id");
      String tmpDrinkIdString = null;
      if (tmpDrinkId != null) {
        tmpDrinkIdString = tmpDrinkId.getAsString();
        tmpDrinkStatus.setDrinkId(tmpDrinkIdString);
      }

      JsonElement tmpDealerId = tmpValue.get("dealer_id");
      if (tmpDealerId != null) {
        String tmpDealerIdString = tmpDealerId.getAsString();
        tmpDrinkStatus.setDealerId(tmpDealerIdString);
      }

      if (tmpDrinkIdString != null) {
        tmpStatusMap.put(tmpDrinkIdString, tmpDrinkStatus);
      } else {
        Log.e(LOGTAG, "Could not read drink for dealer " + tmpDealerId + ".");
      }
    }
    for (String tmpKey : tmpStatusMap.keySet()) {
      Log.d(LOGTAG, "Status Map Entry: " + tmpKey
      );
    }

    tmpDealer.setStatuses(tmpStatusMap);

    return tmpDealer;

  }
}
