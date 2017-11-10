package byu.codemonkeys.tickettoride.async;

import android.os.Handler;

import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by Ryan on 11/10/2017.
 */

public class MainThreadTask implements ITask{
	private Handler handler;
	
	public MainThreadTask(Handler handler) {
		this.handler = handler;
	}
	
	@Override
	public void executeTask(final ICommand task, final ICallback callback) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				task.execute();
			}
		});
	}
}
