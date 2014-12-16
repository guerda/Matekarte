import org.acra.*;
import org.acra.annotation.*;
import android.app.Application;

@ReportCrashes(formKey="", formUri="http://guerda/")
public class Matekarte extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    ACRA.init(this);
  }
}
