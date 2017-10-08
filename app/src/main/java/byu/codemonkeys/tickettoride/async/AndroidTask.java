package byu.codemonkeys.tickettoride.async;

import android.os.AsyncTask;

import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by Ryan on 10/6/2017.
 */

public class AndroidTask  implements ITask {
	
	private ICallback callback;
	private ICommand task;
	
	private class InnerAndroidTask extends AsyncTask<Void, Void, Result>{
		
		@Override
		protected Result doInBackground(Void... voids) {
			return task.execute();
		}
		
		@Override
		protected void onPostExecute(Result result) {
			super.onPostExecute(result);
			callback.callback(result);
		}
	}
	
	@Override
	public void executeTask(ICommand task, ICallback callback) {
		this.task = task;
		this.callback = callback;
		new InnerAndroidTask().execute();
	}
}
