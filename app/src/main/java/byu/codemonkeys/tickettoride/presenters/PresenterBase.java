package byu.codemonkeys.tickettoride.presenters;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.IMediaPlayer;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;

/**
 * Created by Ryan on 10/3/2017.
 */

public class PresenterBase {
	
	public PresenterBase(INavigator navigator,
						 IDisplaysMessages messageDisplayer,
						 IModelFacade modelFacade,
						 IMediaPlayer mediaPlayer) {
		this.navigator = navigator;
		this.messageDisplayer = messageDisplayer;
		this.modelFacade = modelFacade;
		this.mediaPlayer = mediaPlayer;
	}
	
	public void setNavigator(INavigator navigator) {
		this.navigator = navigator;
	}
	
	protected INavigator navigator;
	protected IDisplaysMessages messageDisplayer;
	protected IModelFacade modelFacade;
	protected IMediaPlayer mediaPlayer;
}
