package byu.codemonkeys.tickettoride.async;

import byu.codemonkeys.tickettoride.shared.commands.ICommand;

/**
 * Created by Ryan on 10/6/2017.
 */

public interface ITask {
	void executeTask(ICommand task, ICallback callback);
}
