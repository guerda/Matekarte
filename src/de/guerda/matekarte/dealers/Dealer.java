package de.guerda.matekarte.dealers;


import android.os.Parcel;
import android.os.Parcelable;

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
  String id;
  String name;
  double lat;
  double lon;


  public Dealer() {
    super();
  }

  public Dealer(Parcel aParcel) {
    this();
    id = aParcel.readString();
    name = aParcel.readString();
    lat = aParcel.readDouble();
    lon = aParcel.readDouble();
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

  /**
   * Simple getter for {@link #lat}
   *
   * @return the lat
   */
  public double getLat() {
    return lat;
  }

  /**
   * Simple setter for {@link #lat}
   *
   * @param aLat the lat to set
   */
  public void setLat(double aLat) {
    lat = aLat;
  }

  /**
   * Simple getter for {@link #lon}
   *
   * @return the lon
   */
  public double getLon() {
    return lon;
  }

  /**
   * Simple setter for {@link #lon}
   *
   * @param aLon the lon to set
   */
  public void setLon(double aLon) {
    lon = aLon;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Dealer [id=" + id + ", name=" + name + ", lat=" + lat + ", lon=" + lon + "]";
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel aParcel, int aFlagMask) {
    aParcel.writeString(id);
    aParcel.writeString(name);
    aParcel.writeDouble(lat);
    aParcel.writeDouble(lon);
  }
}
