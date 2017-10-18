package byu.codemonkeys.tickettoride.views.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.models.DestinationCard;
import byu.codemonkeys.tickettoride.mvpcontracts.DestinationCardsContract;
import byu.codemonkeys.tickettoride.views.home.PendingGamesRecyclerAdapter;
import byu.codemonkeys.tickettoride.views.widgets.DestinationCardWidget;

/**
 * A simple {@link Fragment} subclass.
 */
public class DestinationCardsFragment extends Fragment implements DestinationCardsContract.View {
	
	DestinationCardsContract.Presenter presenter;
	
	private TextView textViewBack;
	private ImageView imageViewDeck;
	private RecyclerView recyclerDestinationCards;
	private LinearLayoutManager layoutManagerDestinationCards;
	private PendingGamesRecyclerAdapter pendingDestinationCards;
	
	private DestinationCardWidget cardWidget;
	
	public DestinationCardsFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_destination_cards, container, false);
		getViews(view);
		initRecycler(view);
		setClickListeners();
		return view;
	}
	
	private void initRecycler(View view) {
//		layoutManagerDestinationCards = new LinearLayoutManager(getActivity());
//		recyclerDestinationCards.setLayoutManager(layoutManagerDestinationCards);
	}
	
	private void setClickListeners() {
		this.textViewBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.navigateBack();
			}
		});
		this.imageViewDeck.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.navigateDrawDestinationCards();
			}
		});
	}
	
	private void getViews(View view) {
		this.textViewBack = (TextView) view.findViewById(R.id.destinationCards_textViewBack);
		this.imageViewDeck = (ImageView) view.findViewById(R.id.destinationCards_imageViewDeck);
//		recyclerDestinationCards = (RecyclerView) view.findViewById(R.id.destinationCards_recyclerCards);
		
		this.cardWidget = (DestinationCardWidget) view.findViewById(R.id.destinationCards_card);
		this.cardWidget.setDestinationA("Test1");
		this.cardWidget.setDestinationB("Test2");
		this.cardWidget.setPointValue(23);
		
	}
	
	@Override
	public void setDestinationCards(List<DestinationCard> cards) {
		
	}
	
	public void setPresenter(DestinationCardsContract.Presenter presenter) {
		this.presenter = presenter;
	}
	
}
