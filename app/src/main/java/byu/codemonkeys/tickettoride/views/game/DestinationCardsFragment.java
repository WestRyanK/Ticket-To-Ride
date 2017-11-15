package byu.codemonkeys.tickettoride.views.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.mvpcontracts.game.DestinationCardsContract;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.views.OnRecyclerItemClickListener;
import byu.codemonkeys.tickettoride.views.widgets.HorizontalSpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class DestinationCardsFragment extends Fragment implements DestinationCardsContract.View {
	
	DestinationCardsContract.Presenter presenter;
	private TextView textViewBack;
	private TextView textViewDraw;
	private RecyclerView recyclerDestinationCards;
	private LinearLayoutManager layoutManagerDestinationCards;
	private DestinationCardRecyclerAdapter destinationCardsAdapter;
	private int cardSpacing;
	
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
		
		List<DestinationCard> cards = new ArrayList<>();
		//		cards.add(new DestinationCard("Lair of the Firebending Masters",
		//									  "Southern Earth Kingdom Islands",
		//									  10));
		//		cards.add(new DestinationCard("Ba Sing Se (South)", "Kyoshi Island", 12));
		//		cards.add(new DestinationCard("Hei Bei's Forest", "Southern Air Temple (West)", 4));
		//		cards.add(new DestinationCard(0, 1, 23));
		//		cards.add(new DestinationCard(2, 3, 16));
		//		cards.add(new DestinationCard(4, 5, 3));
		//		cards.add(new DestinationCard(6, 7, 3));
		//		cards.add(new DestinationCard(8, 9, 3));
		//		cards.add(new DestinationCard(10, 11, 3));
		//		cards.add(new DestinationCard(12, 13, 3));
		//		cards.add(new DestinationCard(14, 15, 3));
		//		cards.add(new DestinationCard(16, 17, 3));
		//		cards.add(new DestinationCard(18, 19, 3));
		//		cards.add(new DestinationCard(20, 21, 3));
		//		cards.add(new DestinationCard(22, 23, 3));
		//		cards.add(new DestinationCard(24, 25, 3));
		//		cards.add(new DestinationCard(26, 27, 3));
		//		cards.add(new DestinationCard(28, 29, 3));
		//		cards.add(new DestinationCard(30, 31, 3));
		//		cards.add(new DestinationCard(32, 33, 3));
		//		cards.add(new DestinationCard(34, 35, 3));
		//		cards.add( new DestinationCard(36,37,3));
		setDestinationCards(cards);
		return view;
	}
	
	private void initRecycler(View view) {
		layoutManagerDestinationCards = new LinearLayoutManager(getActivity());
		layoutManagerDestinationCards.setOrientation(LinearLayoutManager.HORIZONTAL);
		recyclerDestinationCards.setLayoutManager(layoutManagerDestinationCards);
		cardSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
													  20,
													  this.getResources().getDisplayMetrics());
		recyclerDestinationCards.addItemDecoration(new HorizontalSpaceItemDecoration(cardSpacing));
		destinationCardsAdapter = new DestinationCardRecyclerAdapter(new ArrayList<DestinationCard>(),
																	 new OnRecyclerItemClickListener<DestinationCard>() {
																		 @Override
																		 public void onItemClick(
																				 DestinationCard card) {
																		 }
																	 });
		recyclerDestinationCards.setAdapter(destinationCardsAdapter);
		scrollToMiddle();
	}
	
	private void setClickListeners() {
		this.textViewBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.navigateBack();
			}
		});
		this.textViewDraw.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.navigateDrawDestinationCards();
			}
		});
	}
	
	private void getViews(View view) {
		this.textViewBack = (TextView) view.findViewById(R.id.destinationCards_textViewBack);
		this.textViewDraw = (TextView) view.findViewById(R.id.destinationCards_textViewDraw);
		recyclerDestinationCards = (RecyclerView) view.findViewById(R.id.destinationCards_recyclerCards);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		presenter.loadDestinationCards();
	}
	
	@Override
	public void setDestinationCards(List<DestinationCard> cards) {
		destinationCardsAdapter.updateData(cards);
		scrollToMiddle();
	}
	
	@Override
	public void setDestinationCardsInDeckCount(int cardsInDeckCount) {
		this.textViewDraw.setText(String.format("Draw (%d)", cardsInDeckCount));
	}
	
	private void scrollToMiddle() {
		//		int dpOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
		//													   recyclerDestinationCards.getWidth(),
		//													   this.getResources().getDisplayMetrics());
		//		layoutManagerDestinationCards.scrollToPositionWithOffset(Integer.MAX_VALUE / 2, recyclerDestinationCards.getWidth() / 2);
		//		int dpOffset = (recyclerDestinationCards.getWidth() / 2) - cardSpacing;
		layoutManagerDestinationCards.scrollToPosition(Integer.MAX_VALUE / 2);
	}
	
	public void setPresenter(DestinationCardsContract.Presenter presenter) {
		this.presenter = presenter;
	}
	
}
