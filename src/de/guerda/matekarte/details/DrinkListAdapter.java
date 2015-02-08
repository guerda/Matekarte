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
import de.guerda.matekarte.dealers.DrinkStatusEnum;

/**
 * Created by philip on 28.12.2014.
 */
public class DrinkListAdapter extends ArrayAdapter<DrinkStatus> {

  private static final String LOGTAG = DrinkListAdapter.class.getName();
  private static final String DRINK_LOGO_ID_NO_DRINK = "@drawable/drink_no_logo";

  private final List<DrinkStatus> drinkList;
  private final Context context;
  private Drawable noDrinkLogoDrawable;

  public DrinkListAdapter(Context aContext, List<DrinkStatus> aDrinkList) {
    super(aContext, 0, aDrinkList);
    context = aContext;
    drinkList = aDrinkList;
  }

  @Override
  public View getView(int aPosition, View aConvertView, ViewGroup aParent) {
    Log.d(LOGTAG, "Inflating detail list");
    View tmpView;
    if (aConvertView == null) {
      LayoutInflater inflater = (LayoutInflater) context
              .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      tmpView = inflater.inflate(R.layout.list_item_drink, aParent, false);
    } else {
      tmpView = aConvertView;
    }

    DrinkStatus tmpDrink = drinkList.get(aPosition);

    Drawable tmpDrawable;
    //Determine drink logo
    String tmpAltText;
    if (tmpDrink != null) {
      String tmpResourceUri = "@drawable/drink_" + tmpDrink.getDrinkId();
      Log.i(LOGTAG, "Loading drink logo '" + tmpResourceUri + "'...");
      int tmpImageResource = getResourceId(tmpResourceUri);

      tmpDrawable = context.getResources().getDrawable(tmpImageResource);


      // Update alternative icon text
      tmpAltText = DrinkIdMap.getInstance().getNameForDrinkId(tmpDrink.getDrinkId());

      // Updating drink status
      //TODO refactor in adapter to DrinkStatusEnum
      updateDrinkStatusColor(tmpView, DrinkStatusEnum.fromString("" + tmpDrink.getStatus()));
    } else {
      Log.e(LOGTAG, "Could not find drink at position " + aPosition + "!");

      tmpDrawable = getNoDrinkLogoLogo();
      // Update alternative text
      tmpAltText = "No drink logo";
    }

    // Updating the drink logo
    ImageView tmpDrinkLogo = (ImageView) tmpView.findViewById(R.id.drink_logo);
    tmpDrinkLogo.setImageDrawable(tmpDrawable);
    Log.d(LOGTAG, "Done Inflating detail list");
    return tmpView;
  }

  private Drawable getNoDrinkLogoLogo() {
    if (noDrinkLogoDrawable == null) {
      String tmpResourceUri = DRINK_LOGO_ID_NO_DRINK;
      Log.i(LOGTAG, "Loading drink logo '" + tmpResourceUri + "'...");
      int tmpImageResource = getResourceId(tmpResourceUri);
      noDrinkLogoDrawable = context.getResources().getDrawable(tmpImageResource);
    }
    return noDrinkLogoDrawable;
  }

  private void updateDrinkStatusColor(View tmpView, DrinkStatusEnum aStatus) {
    String tmpResourceUri;
    View tmpDrinkStatusColor = tmpView.findViewById(R.id.drink_status_color);

    tmpResourceUri = "@color/drink_status_" + aStatus.getStatusId();
    Log.i(LOGTAG, "Loading status color '" + tmpResourceUri + "'...");
    int tmpColorResource = getResourceId(tmpResourceUri);
    int tmpStatusColor = context.getResources().getColor(tmpColorResource);
    tmpDrinkStatusColor.setBackgroundColor(tmpStatusColor);
  }

  private int getResourceId(String tmpResourceUri) {
    return context.getResources().getIdentifier(tmpResourceUri, null, context.getPackageName());
  }
}
