package de.guerda.matekarte;

import android.app.Application;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

@ReportsCrashes(
        formKey = "",
        formUri = "http://guerda.iriscouch.com/acra-matekarte/_design/acra-storage/_update/report",
        httpMethod = HttpSender.Method.PUT,
        reportType = HttpSender.Type.JSON,
        formUriBasicAuthLogin = "matekarte-reporter",
        formUriBasicAuthPassword = "vr83kq9a1n130fgn29nn",
        // Your usual ACRA configuration
        resToastText = R.string.acra_toast_text,
        mode = ReportingInteractionMode.TOAST
)
public class Matekarte extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    ACRA.init(this);
  }
}
