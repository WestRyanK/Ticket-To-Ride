package byu.codemonkeys.tickettoride.presenters.game;

import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.IMediaPlayer;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.game.PlayerStatsContract;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.shared.model.ActiveGame;
import byu.codemonkeys.tickettoride.shared.model.Player;

/**
 * Created by Ryan on 10/17/2017.
 */

public class PlayerStatsPresenter extends PresenterBase implements PlayerStatsContract.Presenters, Observer {
	private PlayerStatsContract.View view;
	
	public PlayerStatsPresenter(PlayerStatsContract.View view,
								INavigator navigator,
								IDisplaysMessages messageDisplayer,
								IModelFacade modelFacade,
								IMediaPlayer mediaPlayer) {
		super(navigator, messageDisplayer, modelFacade, mediaPlayer);
		this.view = view;
		this.modelFacade.addObserver(this);
	}
	
	@Override
	public void update(Observable observable, Object o) {
		if (o == ModelFacade.PLAYER_STATS_UPDATE || o == ModelFacade.GAME_UPDATE || o == ModelFacade.PLAYER_TURN_UPDATE  ||
				o == Player.PLAYER_SCORE_UPDATE || o == Player.PLAYER_TRAINS_UPDATE || o == ActiveGame.TURN_UPDATE || o == Player.PLAYER_DESTINATION_CARDS_UPDATE) {
			loadPlayerStats();
		}
	}
	
	@Override
	public void loadPlayerStats() {
		if (ModelRoot.getInstance().getGame() != null) {
			this.view.setPlayerStats(this.modelFacade.getPlayerInfo());
			int currentTurn = ModelRoot.getInstance().getGame().getTurn();
			this.view.setCurrentTurn(currentTurn);
		}
	}
}
