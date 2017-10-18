package byu.codemonkeys.tickettoride.views.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.mvpcontracts.Phase2TestingContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class Phase2TestingFragment extends Fragment implements Phase2TestingContract.View {
	
	private Phase2TestingContract.Presenter presenter;
	private TextView textViewUpdatePlayerPoints;
	private TextView textViewAddRemoveTrainCardsThisPlayer;
	private TextView textViewAddRemoveDestinationCardsThisPlayer;
	private TextView textViewUpdateTrainCardsOtherPlayers;
	private TextView textViewUpdateDestinationCardsOtherPlayers;
	private TextView textViewUpdateVisibleInvisibleCardsInTrainCardDeck;
	private TextView textViewUpdateCardsInDestinationCardDeck;
	private TextView textViewAddClaimedRoute;
	private TextView textViewAddChatMessage;
	private TextView textViewAddGameHistoryEntry;
	private TextView textViewBack;
	
	
	public Phase2TestingFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_phase2_testing, container, false);
		getViews(view);
		setClickListeners();
		return view;
	}
	
	private void setClickListeners() {
		
		textViewBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.navigateBack();
			}
		});
		textViewUpdatePlayerPoints.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.UpdatePlayerPoints();
			}
		});
		textViewAddRemoveTrainCardsThisPlayer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.AddRemoveTrainCardsThisPlayer();
			}
		});
		textViewAddRemoveDestinationCardsThisPlayer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.AddRemoveDestinationCardsThisPlayer();
			}
		});
		textViewUpdateTrainCardsOtherPlayers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.UpdateTrainCardsOtherPlayers();
			}
		});
		textViewUpdateDestinationCardsOtherPlayers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.UpdateDestinationCardsOtherPlayers();
			}
		});
		textViewUpdateVisibleInvisibleCardsInTrainCardDeck.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.UpdateVisibleInvisibleCardsInTrainCardDeck();
			}
		});
		textViewUpdateCardsInDestinationCardDeck.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.UpdateCardsInDestinationCardDeck();
			}
		});
		textViewAddClaimedRoute.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.AddClaimedRoute();
			}
		});
		textViewAddChatMessage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.AddChatMessage();
			}
		});
		textViewAddGameHistoryEntry.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.AddGameHistoryEntry();
			}
		});
	}
	
	private void getViews(View view) {
		textViewBack = (TextView) view.findViewById(R.id.testing_textViewBack);
		
		textViewUpdatePlayerPoints = (TextView) view.findViewById(R.id.testing_textViewUpdatePlayerPoints);
		textViewAddRemoveTrainCardsThisPlayer = (TextView) view.findViewById(R.id.testing_textViewAddRemoveTrainCardsThisPlayer);
		textViewAddRemoveDestinationCardsThisPlayer = (TextView) view.findViewById(R.id.testing_textViewAddRemoveDestinationCardsThisPlayer);
		textViewUpdateTrainCardsOtherPlayers = (TextView) view.findViewById(R.id.testing_textViewUpdateTrainCardsOtherPlayers);
		textViewUpdateDestinationCardsOtherPlayers = (TextView) view.findViewById(R.id.testing_textViewUpdateDestinationCardsOtherPlayers);
		textViewUpdateVisibleInvisibleCardsInTrainCardDeck = (TextView) view.findViewById(R.id.testing_textViewUpdateVisibleInvisibleCardsInTrainCardDeck);
		textViewUpdateCardsInDestinationCardDeck = (TextView) view.findViewById(R.id.testing_textViewUpdateCardsInDestinationCardDeck);
		textViewAddClaimedRoute = (TextView) view.findViewById(R.id.testing_textViewAddClaimedRoute);
		textViewAddChatMessage = (TextView) view.findViewById(R.id.testing_textViewAddChatMessage);
		textViewAddGameHistoryEntry = (TextView) view.findViewById(R.id.testing_textViewAddGameHistoryEntry);
	}
	
	public void setPresenter(Phase2TestingContract.Presenter presenter) {
		this.presenter = presenter;
	}
}
