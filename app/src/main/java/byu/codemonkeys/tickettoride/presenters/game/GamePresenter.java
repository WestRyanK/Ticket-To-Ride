package byu.codemonkeys.tickettoride.presenters.game;

import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.IMediaPlayer;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.game.CutScenes;
import byu.codemonkeys.tickettoride.mvpcontracts.game.GameContract;
import byu.codemonkeys.tickettoride.mvpcontracts.game.Sounds;
import byu.codemonkeys.tickettoride.networking.GamePoller;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;
import byu.codemonkeys.tickettoride.shared.model.ActiveGame;

public class GamePresenter extends PresenterBase implements GameContract.Presenter, Observer {
	private GameContract.View view;
	
	public GamePresenter(GameContract.View view,
						 INavigator navigator,
						 IDisplaysMessages messageDisplayer,
						 IModelFacade modelFacade,
						 IMediaPlayer mediaPlayer) {
		super(navigator, messageDisplayer, modelFacade, mediaPlayer);
		this.view = view;
		this.modelFacade.addObserver(this);
		this.mediaPlayer.playSound(Sounds.gameSoundtrack, true);
	}
	
	@Override
	public void startPolling() {
		GamePoller.getInstance().startPolling();
	}
	
	@Override
	public void stopPolling() {
		//		GamePoller.getInstance().stopPolling();
	}
	
	@Override
	public void update(Observable observable, Object o) {
		if (o == ModelFacade.GAME_UPDATE) {
			this.navigator.navigate(PresenterEnum.DrawDestinationCards, true);
		}
		if (o == ActiveGame.STARTED_UPDATE) {
			this.mediaPlayer.playCutScene(CutScenes.openingSequence);
		}
		
		// TODO: Flesh this out when we get to the end game
//		if (o == ActiveGame.END_GAME) {
//			if (ModelRoot.getInstance().getUser().getUsername().equals(winner.getUsername())) {
//				this.mediaPlayer.playCutScene(CutScenes.cheering);
//			} else {
//				this.mediaPlayer.playCutScene(CutScenes.iLikeTrains);
//			}
//			this.navigator.navigate(PresenterEnum.EndGame, false);
//		}
	}
}
