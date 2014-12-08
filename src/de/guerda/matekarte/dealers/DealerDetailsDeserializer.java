package de.guerda.matekarte.dealers;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by philip on 08.12.2014.
 */
public class DealerDetailsDeserializer implements JsonDeserializer<Dealer> {

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
    System.out.println("Dealer element: " + tmpDealerElement);

    Dealer tmpDealer = new Dealer();
    tmpDealer.setId(tmpDealerElement.get("id").getAsString());
    tmpDealer.setName(tmpDealerElement.get("name").getAsString());
    tmpDealer.setAddress(tmpDealerElement.get("address").getAsString());
    tmpDealer.setCity(tmpDealerElement.get("city").getAsString());
    tmpDealer.setZip(tmpDealerElement.get("zip").getAsString());
    tmpDealer.setCountry(tmpDealerElement.get("country").getAsString());
    tmpDealer.setWww(tmpDealerElement.get("www").getAsString());
    tmpDealer.setNote(tmpDealerElement.get("note").getAsString());
    tmpDealer.setPhone(tmpDealerElement.get("phone").getAsString());
    tmpDealer.setType(tmpDealerElement.get("type").getAsInt());
    tmpDealer.setLongitude(tmpDealerElement.get("longitude").getAsDouble());
    tmpDealer.setLatitude(tmpDealerElement.get("latitude").getAsDouble());
    //TODO drink_ids
    //TODO status_ids
    return tmpDealer;

  }
}
