package de.guerda.matekarte;

import java.util.List;

public class DealersList {

  private List<Dealer> dealers;

  public DealersList(List<Dealer> dealers) {
    super();
    this.dealers = dealers;
  }

  public List<Dealer> getDealers() {
    return dealers;
  }

  public void setDealers(List<Dealer> dealers) {
    this.dealers = dealers;
  }

}
