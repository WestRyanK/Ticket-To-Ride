package byu.codemonkeys.tickettoride.presenters.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.game.EndGameContract;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;
import byu.codemonkeys.tickettoride.shared.model.EndGamePlayerStats;
import byu.codemonkeys.tickettoride.shared.model.PlayerColor;

/**
 * Created by Ryan on 11/4/2017.
 */

public class EndGamePresenter extends PresenterBase implements EndGameContract.Presenter {
	private EndGameContract.View view;
	
	public EndGamePresenter(EndGameContract.View view,
							INavigator navigator,
							IDisplaysMessages messageDisplayer,
							IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
	}
	
	@Override
	public void navigateLobby() {
		navigator.navigateBack(PresenterEnum.Game);
		navigator.navigate(PresenterEnum.Lobby, false);
	}
	
	@Override
	public void loadEndGameResults() {
		// This beginning part is just temporary code to generate some fake data
		// until we get real support for this in the ModelFacade, ModelRoot.
		// TODO: Replace with getting these stats from ModelRoot
		List<EndGamePlayerStats> endGamePlayerStats = new ArrayList<>();
		Random rand = new Random();
		
		endGamePlayerStats.add(new EndGamePlayerStats("westryank",
													  PlayerColor.Green,
													  rand.nextInt(120),
													  rand.nextInt(100),
													  rand.nextInt(30),
													  rand.nextInt(20),
													  -rand.nextInt(40)));
		
		endGamePlayerStats.add(new EndGamePlayerStats("dcorey3",
													  PlayerColor.Red,
													  rand.nextInt(120),
													  rand.nextInt(100),
													  rand.nextInt(30),
													  rand.nextInt(20),
													  -rand.nextInt(40)));
		
		endGamePlayerStats.add(new EndGamePlayerStats("jacobeastly",
													  PlayerColor.Blue,
													  rand.nextInt(120),
													  rand.nextInt(100),
													  rand.nextInt(30),
													  rand.nextInt(20),
													  -rand.nextInt(40)));
		
		endGamePlayerStats.add(new EndGamePlayerStats("richMeg",
													  PlayerColor.Yellow,
													  rand.nextInt(120),
													  rand.nextInt(100),
													  rand.nextInt(30),
													  rand.nextInt(20),
													  -rand.nextInt(40)));
		
		endGamePlayerStats.add(new EndGamePlayerStats("aaaaaangy",
													  PlayerColor.Black,
													  rand.nextInt(120),
													  rand.nextInt(100),
													  rand.nextInt(30),
													  rand.nextInt(20),
													  -rand.nextInt(40)));
		
		// This is the actual code that the presenter needs
		Collections.sort(endGamePlayerStats, new Comparator<EndGamePlayerStats>() {
			@Override
			public int compare(EndGamePlayerStats statsA, EndGamePlayerStats statsB) {
				return -Integer.compare(statsA.getTotalScore(), statsB.getTotalScore());
			}
		});
		
		EndGamePlayerStats winner = endGamePlayerStats.get(0);
		this.view.setIsWinner(modelFacade.getUser().getUsername().equals(winner.getUsername()));
		this.view.setEndGameStats(endGamePlayerStats);
	}
}
