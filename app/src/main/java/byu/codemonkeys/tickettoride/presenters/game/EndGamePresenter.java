package byu.codemonkeys.tickettoride.presenters.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.IMediaPlayer;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.game.EndGameContract;
import byu.codemonkeys.tickettoride.networking.GamePoller;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;
import byu.codemonkeys.tickettoride.shared.model.EndGamePlayerStats;
import byu.codemonkeys.tickettoride.shared.model.GameSummary;
import byu.codemonkeys.tickettoride.shared.model.PlayerColor;

public class EndGamePresenter extends PresenterBase implements EndGameContract.Presenter {
	private EndGameContract.View view;
	
	public EndGamePresenter(EndGameContract.View view,
							INavigator navigator,
							IDisplaysMessages messageDisplayer,
							IModelFacade modelFacade,
							IMediaPlayer mediaPlayer) {
		super(navigator, messageDisplayer, modelFacade, mediaPlayer);
		this.view = view;
	}
	
	@Override
	public void navigateLobby() {
		navigator.navigateBack(PresenterEnum.Game);
		navigator.navigate(PresenterEnum.Lobby, false);
	}
	
	@Override
	public void loadEndGameResults() {
		GameSummary summary = ModelRoot.getInstance().getSummary();

		this.view.setWinner(summary.getWinner());
		this.view.setEndGameStats(summary.getSummaries());

		GamePoller.getInstance().stopPolling();
		ModelRoot.getInstance().resetHistory();
	}
}
