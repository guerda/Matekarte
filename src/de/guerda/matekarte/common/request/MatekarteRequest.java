package de.guerda.matekarte.common.request;

import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

/**
 * Created by iulius on 18/01/15.
 */
public abstract class MatekarteRequest<RESULT> extends GoogleHttpClientSpiceRequest<RESULT> {

  protected static final String URL_BASE = "https://www.matekarte.de/";

  public MatekarteRequest(Class<RESULT> clazz) {
    super(clazz);
  }


}
