package de.guerda.matekarte.dealers;


/**
 * Example:
 * 
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
public class Dealer {

  String id;
  String name;

  double lat;
  double lon;

  /**
   * Simple getter for {@link id}
   * 
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Simple setter for {@link id}
   * 
   * @param aId
   *          the id to set
   */
  public void setId(String aId) {
    id = aId;
  }

  /**
   * Simple getter for {@link name}
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Simple setter for {@link name}
   * 
   * @param aName
   *          the name to set
   */
  public void setName(String aName) {
    name = aName;
  }

  /**
   * Simple getter for {@link lat}
   * 
   * @return the lat
   */
  public double getLat() {
    return lat;
  }

  /**
   * Simple setter for {@link lat}
   * 
   * @param aLat
   *          the lat to set
   */
  public void setLat(double aLat) {
    lat = aLat;
  }

  /**
   * Simple getter for {@link lon}
   * 
   * @return the lon
   */
  public double getLon() {
    return lon;
  }

  /**
   * Simple setter for {@link lon}
   * 
   * @param aLon
   *          the lon to set
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

}
