package byu.codemonkeys.tickettoride.presenters.game;

import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.game.PlayerStatsContract;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;

/**
 * Created by Ryan on 10/17/2017.
 */

public class PlayerStatsPresenter extends PresenterBase implements PlayerStatsContract.Presenters, Observer {
	private PlayerStatsContract.View view;
	
	public PlayerStatsPresenter(PlayerStatsContract.View view,
								INavigator navigator,
								IDisplaysMessages messageDisplayer,
								IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
		this.modelFacade.addObserver(this);
	}
	
	@Override
	public void update(Observable observable, Object o) {
		if (o == ModelFacade.PLAYER_STATS_UPDATE || o == ModelFacade.GAME_UPDATE || o == ModelFacade.PLAYER_TURN_UPDATE ) {
			loadPlayerStats();
		}
//		if (o == ModelFacade.PLAYER_TURN_UPDATE || o == ModelFacade.GAME_UPDATE) {
//			int currentTurn = ModelRoot.getInstance().getGame().getTurn();
//			this.view.setCurrentTurn(currentTurn);
//		}
	}
	
	@Override
	public void loadPlayerStats() {
		this.view.setPlayerStats(this.modelFacade.getPlayerInfo());
		int currentTurn = ModelRoot.getInstance().getGame().getTurn();
		this.view.setCurrentTurn(currentTurn);
	}
}
