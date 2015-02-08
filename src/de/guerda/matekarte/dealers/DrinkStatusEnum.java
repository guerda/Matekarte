package de.guerda.matekarte.dealers;

/**
 * Created by philip on 08.02.2015.
 */
public enum DrinkStatusEnum {

  IN_STOCK("1"),
  SHORT("2"),
  SOLD_OUT("3");

  private final String statusId;

  DrinkStatusEnum(String aStatusId) {
    statusId = aStatusId;
  }

  public static DrinkStatusEnum fromString(String aDrinkStatusId) {
    if (aDrinkStatusId != null) {
      for (DrinkStatusEnum b : DrinkStatusEnum.values()) {
        if (aDrinkStatusId.equalsIgnoreCase(b.statusId)) {
          return b;
        }
      }
    }
    throw new IllegalArgumentException("No DrinkStatusEnum with id " + aDrinkStatusId + " found");
  }

  @Override
  public String toString() {
    return "DrinkStatusEnum [statusId=" + statusId + "]";
  }

  public String getStatusId() {
    return statusId;
  }
}
