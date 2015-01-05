package de.guerda.matekarte.dealers;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

/**
 * Example:
 * <p/>
 * <pre>
 * {
 *   "50fb3d14ce007c40fc00000d":[
 *     "Brausedealer",
 *     52.3761,
 *     9.70221,
 *     [
 *        "50fb3d16ce007c40fc00080d",
 *        "50fb3d16ce007c40fc00080e"
 *     ]
 *   ]
 * }
 * </pre>
 * <p/>
 * or:
 * <p/>
 * <pre>
 * {
 * "dealer": {
 *  "name": "Chaosdorf",
 *  "address": "Hüttenstraße 25",
 *  "city": "Düsseldorf",
 *  "zip": "40215",
 *  "country": "Deutschland",
 *  "www": "https://chaosdorf.de",
 *  "note": "",
 *  "phone": "",
 *  "type": "3",
 *  "id": "51f22b147a58b4f6a5000595",
 *  "longitude": 51.21660900000001,
 *  "latitude": 6.7836145,
 *  "drink_ids": [
 *   "50fb3d16ce007c40fc00080e",
 *   "50fb3d16ce007c40fc00080f",
 *   "50fb3d16ce007c40fc000812",
 *   "50fb3d16ce007c40fc000810",
 *   "50fb3d16ce007c40fc000811",
 *   "50fb3d16ce007c40fc00080d"
 *  ],
 *  "status_ids": [
 *   "53de3c9c7a58b4ce6c000001"
 *  ]
 *  },
 *  "statuses": [
 *   {
 *    "status": 1,
 *    "id": "53de3c9c7a58b4ce6c000001",
 *    "drink_id": "50fb3d16ce007c40fc00080f",
 *    "dealer_id": "51f22b147a58b4f6a5000595"
 *   }
 *  ]
 * }
 * </pre>
 */
public class Dealer implements Parcelable {

  public static final Parcelable.Creator<Dealer> CREATOR = new Parcelable.Creator<Dealer>() {
    public Dealer createFromParcel(Parcel in) {
      return new Dealer(in);
    }

    public Dealer[] newArray(int size) {
      return new Dealer[size];
    }
  };
  private String id;
  private String name;
  private double latitude;
  private double longitude;

  private String address;
  private String city;
  private String zip;
  private String country;
  private String www;
  private String note;
  private String phone;
  private int type;
  private Map<String, DrinkStatus> statuses;

  public Dealer() {
    super();
  }

  public Dealer(Parcel aParcel) {
    this();
    //TODO add all members
    id = aParcel.readString();
    name = aParcel.readString();
    latitude = aParcel.readDouble();
    longitude = aParcel.readDouble();
  }

  public Map<String, DrinkStatus> getStatuses() {
    return statuses;
  }

  public void setStatuses(Map<String, DrinkStatus> statuses) {
    this.statuses = statuses;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getWww() {
    return www;
  }

  public void setWww(String www) {
    this.www = www;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  /**
   * Simple getter for {@link #id}
   *
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Simple setter for {@link #id}
   *
   * @param aId the id to set
   */
  public void setId(String aId) {
    id = aId;
  }

  /**
   * Simple getter for {@link #name}
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Simple setter for {@link #name}
   *
   * @param aName the name to set
   */
  public void setName(String aName) {
    name = aName;
  }


  @Override
  public String toString() {
    return "Dealer{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", address='" + address + '\'' +
            ", city='" + city + '\'' +
            ", zip='" + zip + '\'' +
            ", country='" + country + '\'' +
            ", www='" + www + '\'' +
            ", note='" + note + '\'' +
            ", phone='" + phone + '\'' +
            ", type=" + type +
            '}';
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel aParcel, int aFlagMask) {
    //TODO Add all elements to parcelable
    aParcel.writeString(id);
    aParcel.writeString(name);
    aParcel.writeDouble(latitude);
    aParcel.writeDouble(longitude);
  }
}
