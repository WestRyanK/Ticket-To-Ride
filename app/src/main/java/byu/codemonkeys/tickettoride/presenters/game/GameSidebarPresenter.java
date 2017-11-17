package byu.codemonkeys.tickettoride.presenters.game;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.IMediaPlayer;
import byu.codemonkeys.tickettoride.mvpcontracts.game.GameSidebarContract;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.game.Sounds;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;
import byu.codemonkeys.tickettoride.shared.model.map.GameMap;

/**
 * Created by Ryan on 10/16/2017.
 */

public class GameSidebarPresenter extends PresenterBase implements GameSidebarContract.Presenter {
	
	private GameSidebarContract.View view;
	
	public GameSidebarPresenter(GameSidebarContract.View view,
								INavigator navigator,
								IDisplaysMessages messageDisplayer,
								IModelFacade modelFacade,
								IMediaPlayer mediaPlayer) {
		super(navigator, messageDisplayer, modelFacade, mediaPlayer);
		this.view = view;
	}
	
	@Override
	public void navigateDestinationCards() {
		this.navigator.navigate(PresenterEnum.DestinationCards, true);
	}
	
	@Override
	public void navigateDrawTrainCards() {
//		this.navigator.navigate(PresenterEnum.EndGame, true);
//		this.mediaPlayer.playSound(Sounds.gameSoundtrack, true);
		this.navigator.navigate(PresenterEnum.DrawTrainCards, true);
	}
	
	@Override
	public void navigateChatHistory() {
		this.navigator.navigate(PresenterEnum.ChatHistory, true);
	}
}
