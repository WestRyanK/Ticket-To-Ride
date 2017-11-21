package byu.codemonkeys.tickettoride.views.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.mvpcontracts.game.TrainCardsContract;
import byu.codemonkeys.tickettoride.presenters.game.TrainCardsPresenter;
import byu.codemonkeys.tickettoride.shared.model.cards.CardType;
import byu.codemonkeys.tickettoride.views.widgets.TrainCardWidget;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainCardsFragment extends Fragment implements TrainCardsContract.View {
	private TrainCardsContract.Presenter presenter;
	
	private Map<CardType, TrainCardWidget> trainCardWidgets;
	
	public TrainCardsFragment() {
		// Required empty public constructor
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_train_cards, container, false);
		getViews(view);
		setOnClickListeners(view);
		return view;
	}
	
	private void setOnClickListeners(View view) {
		for (final Map.Entry<CardType, TrainCardWidget> entry : trainCardWidgets.entrySet()) {
			entry.getValue().setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					presenter.setActiveCardType(entry.getKey());
				}
			});
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		presenter.loadHand();
	}
	
	private void getViews(View view) {
		trainCardWidgets = new HashMap<>();
		trainCardWidgets.put(CardType.Wild, (TrainCardWidget) view.findViewById(R.id.card_wild));
		trainCardWidgets.put(CardType.Red, (TrainCardWidget) view.findViewById(R.id.card_red));
		trainCardWidgets.put(CardType.Orange,
							 (TrainCardWidget) view.findViewById(R.id.card_orange));
		trainCardWidgets.put(CardType.Yellow,
							 (TrainCardWidget) view.findViewById(R.id.card_yellow));
		trainCardWidgets.put(CardType.Green, (TrainCardWidget) view.findViewById(R.id.card_green));
		trainCardWidgets.put(CardType.Blue, (TrainCardWidget) view.findViewById(R.id.card_blue));
		trainCardWidgets.put(CardType.Purple,
							 (TrainCardWidget) view.findViewById(R.id.card_purple));
		trainCardWidgets.put(CardType.Black, (TrainCardWidget) view.findViewById(R.id.card_black));
		trainCardWidgets.put(CardType.White, (TrainCardWidget) view.findViewById(R.id.card_white));
	}
	
	public void setPresenter(TrainCardsContract.Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void setHand(final Map<CardType, Integer> hand) {
		for (Map.Entry<CardType, Integer> entry : hand.entrySet()) {
			trainCardWidgets.get(entry.getKey()).setCount(entry.getValue());
		}
	}
	
	@Override
	public void setActiveCardType(CardType type) {
		for (final Map.Entry<CardType, TrainCardWidget> entry : trainCardWidgets.entrySet()) {
			entry.getValue().setSelected(false);
		}
		if (type != null) {
			this.trainCardWidgets.get(type).setSelected(true);
		}
	}
}
