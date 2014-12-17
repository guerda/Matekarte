package de.guerda.matekarte.list;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.guerda.matekarte.R;
import de.guerda.matekarte.dealers.Dealer;

/**
 * Created by philip on 06.11.2014.
 */
public class DealerListAdapter extends ArrayAdapter<Dealer> {

  private static final String LOGTAG = "DealerListAdapter";
  private Context context;
  private List<Dealer> dealers;
  private Location location;

  public DealerListAdapter(Context aContext, List<Dealer> aDealerList) {
    super(aContext, 0, aDealerList);
    context = aContext;
    dealers = aDealerList;
  }

  @Override
  public View getView(int aPosition, View aConvertView, ViewGroup aParent) {
    View tmpView;
    if (aConvertView == null) {
      LayoutInflater inflater = (LayoutInflater) context
              .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      tmpView = inflater.inflate(R.layout.list_item_dealer, aParent, false);
    } else {
      tmpView = aConvertView;
    }

    TextView tmpNameView = (TextView) tmpView.findViewById(R.id.dealer_name_text);
    Dealer tmpDealer = dealers.get(aPosition);
    tmpNameView.setText(tmpDealer.getName());

    TextView tmpDrinksView = (TextView) tmpView.findViewById(R.id.dealer_drinks_text);
    tmpDrinksView.setText("drinks");


    TextView tmpDistanceView = (TextView) tmpView.findViewById(R.id.dealer_distance_text);
    String tmpDistanceText = null;
    if (location == null) {
      tmpDistanceText = "No distance calculable";
    } else {
      Location tmpLocation = new Location("FAKE");
      tmpLocation.setLatitude(tmpDealer.getLatitude());
      tmpLocation.setLongitude(tmpDealer.getLongitude());
      float tmpDistance = tmpLocation.distanceTo(location);
      tmpDistanceText = getContext().getResources().getString(R.string.text_distance, Math.round(tmpDistance));
    }
    tmpDistanceView.setText(tmpDistanceText);

    return tmpView;
  }


  public void setLocation(Location aLocation) {
    location = aLocation;
  }
}
