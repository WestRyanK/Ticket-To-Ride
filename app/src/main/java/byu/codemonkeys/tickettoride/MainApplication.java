package byu.codemonkeys.tickettoride;

import android.app.Application;

import byu.codemonkeys.tickettoride.async.AndroidTask;
import byu.codemonkeys.tickettoride.models.ModelFacade;

/**
 * Created by Ryan on 11/10/2017.
 */

public class MainApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		
		((ModelFacade) ModelFacade.getInstance()).setAsyncTask(new AndroidTask());
		getApplicationContext()
	}
}
