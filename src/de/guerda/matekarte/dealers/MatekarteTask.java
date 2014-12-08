package de.guerda.matekarte.dealers;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by philip on 08.12.2014.
 */
public abstract class MatekarteTask<T> extends AsyncTaskLoader<T> {

  protected static final String URL_BASE = "https://www.matekarte.de/";

  public MatekarteTask(Context context) {
    super(context);
  }

  @Override
  protected abstract void onStartLoading();

  @Override
  public abstract T loadInBackground();
}
