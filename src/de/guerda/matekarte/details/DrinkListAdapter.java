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

/**
 * Created by philip on 28.12.2014.
 */
public class DrinkListAdapter extends ArrayAdapter<String> {

  private static final String LOGTAG = DrinkListAdapter.class.getSimpleName();

  private final List<String> drinkList;
  private final Context context;

  public DrinkListAdapter(Context aContext, List<String> aDrinkList) {
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

    String tmpDrink = drinkList.get(aPosition);

    ImageView tmpDrinkLogo = (ImageView) tmpView.findViewById(R.id.drink_logo);
    String tmpResourceUri = "@drawable/drink_" + tmpDrink;
    Log.i(LOGTAG, "Loading drink logo '" + tmpResourceUri + "'...");
    int tmpImageResource = context.getResources().getIdentifier(tmpResourceUri, null, context.getPackageName());
    Drawable tmpDrawable = context.getResources().getDrawable(tmpImageResource);
    tmpDrinkLogo.setImageDrawable(tmpDrawable);
    return tmpView;
  }
}
