package byu.codemonkeys.tickettoride.views.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.mvpcontracts.game.PlayerStatsContract;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.PlayerColor;
import byu.codemonkeys.tickettoride.shared.model.Self;
import byu.codemonkeys.tickettoride.views.widgets.PlayerStatsWidget;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayersStatsFragment extends Fragment implements PlayerStatsContract.View {
	
	private PlayerStatsContract.Presenters presenters;
	private List<PlayerStatsWidget> playerStatsWidgets;
	
	public PlayersStatsFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_players_stats, container, false);
		getViews(view);
		List<Player> players = new ArrayList<>();
//		players.add(new Self("dcorey3", PlayerColor.Blue, 125, 35, 22, 4));
//		players.add(new Self("westryan", PlayerColor.Red, 77, 43, 3, 12));
//		players.add(new Self("richmeg", PlayerColor.Green, 188, 21, 53, 143));
//		players.add(new Self("jacobeast", PlayerColor.Yellow, 188, 21, 53, 143));
//		players.add(new Self("aang314", PlayerColor.Black, 188, 21, 53, 143));
//		setPlayerStats(players);
//		setCurrentTurn(3);
		return view;
	}
	
	private void getViews(View view) {
		playerStatsWidgets = new ArrayList<>();
		playerStatsWidgets.add((PlayerStatsWidget) view.findViewById(R.id.playersStats_player0));
		playerStatsWidgets.add((PlayerStatsWidget) view.findViewById(R.id.playersStats_player1));
		playerStatsWidgets.add((PlayerStatsWidget) view.findViewById(R.id.playersStats_player2));
		playerStatsWidgets.add((PlayerStatsWidget) view.findViewById(R.id.playersStats_player3));
		playerStatsWidgets.add((PlayerStatsWidget) view.findViewById(R.id.playersStats_player4));
	}
	
	public void setPresenters(PlayerStatsContract.Presenters presenters) {
		this.presenters = presenters;
	}
	
	@Override
	public void setPlayerStats(List<Player> players) {
		if (players.size() > playerStatsWidgets.size())
			throw new IllegalArgumentException("More players than the game can handle!");
		
		for (int i = 0; i < playerStatsWidgets.size(); i++) {
			PlayerStatsWidget widget = playerStatsWidgets.get(i);
			if (i < players.size()) {
				widget.setVisibility(View.VISIBLE);
				widget.setPlayerColor(players.get(i).getColor());
				widget.setPlayerName(players.get(i).getUsername());
				widget.setPlayerScore(players.get(i).getScore());
//				widget.setPlayerTrainCards(players.get(i).getTrainCardsCount());
//				widget.setPlayerTrains(players.get(i).getTrainsCount());
//				widget.setPlayerDestinationCards(players.get(i).getDestinationCardsCount());
			} else {
				widget.setVisibility(View.GONE);
			}
		}
	}
	
	@Override
	public void setCurrentTurn(int playerIndex) {
		for (int i = 0; i < this.playerStatsWidgets.size(); i++) {
			this.playerStatsWidgets.get(i).setPlayersTurn(i == playerIndex);
		}
	}
}
