package byu.codemonkeys.tickettoride.async;

import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by Ryan on 10/6/2017.
 */

public class MockTask implements ITask {
	@Override
	public void executeTask(ICommand task, ICallback callback) {
		Result result = task.execute();
		callback.callback(result);
	}
}
