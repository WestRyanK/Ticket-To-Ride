package byu.codemonkeys.tickettoride.shared.model;

import java.util.Observable;

/**
 * Created by Ryan on 11/1/2017.
 */

public class ObservableExt extends Observable{
	
	public boolean observesChildren() {
		return observesChildren;
	}
	
	private transient boolean observesChildren = false;
	
	public void setObservesChildren(boolean observesChildren) {
		this.observesChildren = observesChildren;
	}
}
