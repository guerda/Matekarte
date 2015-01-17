package de.guerda.matekarte.details;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import de.guerda.matekarte.R;
import de.guerda.matekarte.dealers.DrinkStatus;

/**
 * Created by philip on 28.12.2014.
 */
public class DrinkListAdapter extends ArrayAdapter<DrinkStatus> {

  private static final String LOGTAG = DrinkListAdapter.class.getName();

  private final List<DrinkStatus> drinkList;
  private final Context context;

  public DrinkListAdapter(Context aContext, List<DrinkStatus> aDrinkList) {
    super(aContext, 0, aDrinkList);
    context = aContext;
    drinkList = aDrinkList;
  }

  @Override
  public View getView(int aPosition, View aConvertView, ViewGroup aParent) {
    View tmpView;
    if (aConvertView == null) {
      LayoutInflater inflater = (LayoutInflater) context
              .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      tmpView = inflater.inflate(R.layout.list_item_drink, aParent, false);
    } else {
      tmpView = aConvertView;
    }

    DrinkStatus tmpDrink = drinkList.get(aPosition);

    if (tmpDrink != null) {
      // Updating the drink logo
      ImageView tmpDrinkLogo = (ImageView) tmpView.findViewById(R.id.drink_logo);
      String tmpResourceUri = "@drawable/drink_" + tmpDrink.getDrinkId();
      Log.i(LOGTAG, "Loading drink logo '" + tmpResourceUri + "'...");
      int tmpImageResource = getResourceId(tmpResourceUri);
      Drawable tmpDrawable = context.getResources().getDrawable(tmpImageResource);

      tmpDrinkLogo.setImageDrawable(tmpDrawable);

      //TODO Update alternative icon text

      // Updating drink status
      View tmpDrinkStatusColor = tmpView.findViewById(R.id.drink_status_color);

      tmpResourceUri = "@color/drink_status_" + tmpDrink.getStatus();
      Log.i(LOGTAG, "Loading status color '" + tmpResourceUri + "'...");
      int tmpColorResource = getResourceId(tmpResourceUri);
      int tmpStatusColor = context.getResources().getColor(tmpColorResource);
      tmpDrinkStatusColor.setBackgroundColor(tmpStatusColor);
    } else {
      Log.e(LOGTAG, "Could not find drink");
    }

    return tmpView;
  }

  private int getResourceId(String tmpResourceUri) {
    return context.getResources().getIdentifier(tmpResourceUri, null, context.getPackageName());
  }
}
