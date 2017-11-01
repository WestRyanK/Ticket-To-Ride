package byu.codemonkeys.tickettoride.views.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.game.GameContract;
import byu.codemonkeys.tickettoride.presenters.game.DrawTrainCardsPresenter;
import byu.codemonkeys.tickettoride.presenters.game.GameSidebarPresenter;
import byu.codemonkeys.tickettoride.presenters.game.PlayerStatsPresenter;
import byu.codemonkeys.tickettoride.shared.model.cards.CardType;
import byu.codemonkeys.tickettoride.views.widgets.TrainCardWidget;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment implements GameContract.View{
	
	
	FrameLayout frameSidebar;
	FrameLayout framePlayerStats;
	FrameLayout frameTrainCards;
	FrameLayout frameMap;

    Map<CardType, TrainCardWidget> trainCardWidgets;
	
	public void setPresenter(GameContract.Presenter presenter) {
		this.presenter = presenter;
	}
	
	GameContract.Presenter presenter;
	
	public GameFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_game, container, false);
		setupGameFragments(view);
		setupTrainCards(view);
		return view;
	}
	
	private void setupGameFragments(View view) {
		
		GameActivity activity = (GameActivity) this.getActivity();
		frameSidebar = (FrameLayout) view.findViewById(R.id.game_frameSidebar);
		GameSidebarFragment sidebarFragment = new GameSidebarFragment();
		sidebarFragment.setPresenter(new GameSidebarPresenter(sidebarFragment,
															  activity,
															  activity,
															  ModelFacade.getInstance()));
		activity.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.game_frameSidebar, sidebarFragment)
				.commit();
		framePlayerStats = (FrameLayout) view.findViewById(R.id.game_framePlayerStats);
		PlayersStatsFragment statsFragment = new PlayersStatsFragment();
		statsFragment.setPresenters(new PlayerStatsPresenter(statsFragment,
															 activity,
															 activity,
															 ModelFacade.getInstance()));
		activity.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.game_framePlayerStats, statsFragment)
				.commit();
		frameTrainCards = (FrameLayout) view.findViewById(R.id.game_frameTrainCards);
		TrainCardsFragment trainCardsFragment = new TrainCardsFragment();
		//		trainCardsFragment.SetPresenter(new TrainCardsPresenter(trainCardsFragment,
		//																activity,
		//																activity,
		//																ModelFacade.getInstance()));
		activity.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.game_frameTrainCards, trainCardsFragment)
				.commit();
		frameMap = (FrameLayout) view.findViewById(R.id.game_frameMap);
		MapFragment mapFragment = new MapFragment();
		//		mapFragment.setPresenter(new MapPresenter(mapFragment,
		//															  activity,
		//															  activity,
		//															  ModelFacade.getInstance()));
		activity.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.game_frameMap, mapFragment)
				.commit();
		
		
	}

	private void setupTrainCards(View view) {
        trainCardWidgets = new HashMap<>();
        trainCardWidgets.put(CardType.Wild, (TrainCardWidget) view.findViewById(R.id.card_wild));
        trainCardWidgets.put(CardType.Red, (TrainCardWidget) view.findViewById(R.id.card_red));
        trainCardWidgets.put(CardType.Orange, (TrainCardWidget) view.findViewById(R.id.card_orange));
        trainCardWidgets.put(CardType.Yellow, (TrainCardWidget) view.findViewById(R.id.card_yellow));
        trainCardWidgets.put(CardType.Green, (TrainCardWidget) view.findViewById(R.id.card_green));
        trainCardWidgets.put(CardType.Blue, (TrainCardWidget) view.findViewById(R.id.card_blue));
        trainCardWidgets.put(CardType.Purple, (TrainCardWidget) view.findViewById(R.id.card_purple));
        trainCardWidgets.put(CardType.Black, (TrainCardWidget) view.findViewById(R.id.card_black));
        trainCardWidgets.put(CardType.White, (TrainCardWidget) view.findViewById(R.id.card_white));
    }
	
	public void showDrawTrainCardsSidebar() {
		GameActivity activity = (GameActivity) this.getActivity();
		DrawTrainCardsFragment fragment = new DrawTrainCardsFragment();
		fragment.setPresenter(new DrawTrainCardsPresenter(fragment,
														  activity,
														  activity,
														  ModelFacade.getInstance()));
		this.getFragmentManager()
			.beginTransaction()
			.replace(R.id.game_frameSidebar, fragment)
			.commit();
	}
	
	public void showGameSidebar() {
		GameActivity activity = (GameActivity) this.getActivity();
		GameSidebarFragment fragment = new GameSidebarFragment();
		fragment.setPresenter(new GameSidebarPresenter(fragment,
													   activity,
													   activity,
													   ModelFacade.getInstance()));
		this.getFragmentManager()
			.beginTransaction()
			.replace(R.id.game_frameSidebar, fragment)
			.commit();
	}

	@Override
	public void setHand(Map<CardType, Integer> hand) {
        for (Map.Entry<CardType, Integer> entry : hand.entrySet()) {
            trainCardWidgets.get(entry.getKey()).setCount(entry.getValue());
        }
    }

		@Override
	public void onResume() {
		super.onResume();
		
		presenter.startPolling();
		Log.d("GAME", "Resuming game...");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		presenter.stopPolling();
		Log.d("GAME", "Pausing game...");
	}
}
