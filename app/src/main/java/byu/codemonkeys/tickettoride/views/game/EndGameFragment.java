package byu.codemonkeys.tickettoride.views.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.mvpcontracts.game.EndGameContract;
import byu.codemonkeys.tickettoride.shared.model.EndGamePlayerStats;
import byu.codemonkeys.tickettoride.views.widgets.EndGamePlayerStatsWidget;

/**
 * A simple {@link Fragment} subclass.
 */
public class EndGameFragment extends Fragment implements EndGameContract.View {
	
	private EndGameContract.Presenter presenter;
	private TextView textViewLobby;
	private TextView textViewTitle;
	private List<EndGamePlayerStatsWidget> endGamePlayerStatsWidgets;
	
	public EndGameFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_end_game, container, false);
		getViews(view);
		setClickListeners();
		return view;
	}
	
	private void setClickListeners() {
		this.textViewLobby.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.navigateLobby();
			}
		});
	}
	
	private void getViews(View view) {
		textViewLobby = (TextView) view.findViewById(R.id.endGame_textViewLobby);
		textViewTitle = (TextView) view.findViewById(R.id.endGame_textViewTitle);
		
		endGamePlayerStatsWidgets = new ArrayList<>();
		endGamePlayerStatsWidgets.add((EndGamePlayerStatsWidget) view.findViewById(R.id.endGame_endGameStats0));
		endGamePlayerStatsWidgets.add((EndGamePlayerStatsWidget) view.findViewById(R.id.endGame_endGameStats1));
		endGamePlayerStatsWidgets.add((EndGamePlayerStatsWidget) view.findViewById(R.id.endGame_endGameStats2));
		endGamePlayerStatsWidgets.add((EndGamePlayerStatsWidget) view.findViewById(R.id.endGame_endGameStats3));
		endGamePlayerStatsWidgets.add((EndGamePlayerStatsWidget) view.findViewById(R.id.endGame_endGameStats4));
	}
	
	@Override
	public void setEndGameStats(final List<EndGamePlayerStats> endGameStats) {
		if (endGameStats.size() > endGamePlayerStatsWidgets.size())
			throw new IllegalArgumentException("More stats than the game can handle!");
		
		for (int i = 0; i < endGamePlayerStatsWidgets.size(); i++) {
			EndGamePlayerStatsWidget widget = endGamePlayerStatsWidgets.get(i);
			if (i < endGameStats.size()) {
				widget.setVisibility(View.VISIBLE);
				widget.setPlayerColor(endGameStats.get(i).getPlayerColor());
				widget.setPlayerName(endGameStats.get(i).getUsername());
				widget.setPlayerTotalScore(endGameStats.get(i).getTotalScore());
				widget.setPointsFromClaimedRoutes(endGameStats.get(i).getPointsFromClaimedRoutes());
				widget.setPointsFromCompletedDestinations(endGameStats.get(i)
																	  .getPointsFromCompletedDestinations());
				widget.setPointsFromLongestRoute(endGameStats.get(i).getPointsFromLongestRoute());
				widget.setPenaltiesFromUnreachedDestinations(endGameStats.get(i)
																		 .getPenaltiesFromUnreachedDestinations());
			} else {
				widget.setVisibility(View.GONE);
			}
		}
	}
	
	@Override
	public void setWinner(final String winner) {
		textViewTitle.setText(String.format("%s is the winner!", winner));
	}
	
	@Override
	public void onResume() {
		super.onResume();
		presenter.loadEndGameResults();
	}
	
	public void setPresenter(EndGameContract.Presenter presenter) {
		this.presenter = presenter;
	}
}
