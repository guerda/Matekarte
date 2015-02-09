package de.guerda.matekarte.dealers;

/**
 * Created by philip on 28.12.2014.
 */
public class DrinkStatus {

  private int status;
  private String id;
  private String drinkId;
  private String dealerId;

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDrinkId() {
    return drinkId;
  }

  public void setDrinkId(String drinkId) {
    this.drinkId = drinkId;
  }

  public String getDealerId() {
    return dealerId;
  }

  public void setDealerId(String dealerId) {
    this.dealerId = dealerId;
  }
}
