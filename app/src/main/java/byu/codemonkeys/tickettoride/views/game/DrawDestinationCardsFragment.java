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
import byu.codemonkeys.tickettoride.models.DestinationCard;
import byu.codemonkeys.tickettoride.mvpcontracts.game.DrawDestinationCardsContract;
import byu.codemonkeys.tickettoride.views.OnRecyclerItemClickListener;
import byu.codemonkeys.tickettoride.views.widgets.HorizontalSpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawDestinationCardsFragment extends Fragment implements DrawDestinationCardsContract.View {
	
	
	DrawDestinationCardsContract.Presenter presenter;
	private TextView textViewContinue;
	private RecyclerView recyclerDestinationCards;
	private LinearLayoutManager layoutManagerDestinationCards;
	private DestinationCardRecyclerAdapter destinationCardsAdapter;
	private int cardSpacing;
	
	public DrawDestinationCardsFragment() {
		// Required empty public constructor
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_draw_destination_cards, container, false);
		getViews(view);
		initRecycler(view);
//		setClickListeners();
		return view;
	}
	
	private void getViews(View view) {
		recyclerDestinationCards = (RecyclerView) view.findViewById(R.id.drawDestinationCards_recyclerCards);
		textViewContinue = (TextView) view.findViewById(R.id.drawDestinationCards_textViewContinue);
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
//		scrollToMiddle();
	}
	
	private void scrollToMiddle() {
		layoutManagerDestinationCards.scrollToPosition(Integer.MAX_VALUE / 2);
	}
	
	private void setClickListeners() {
		this.textViewContinue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.acceptSelectedCards();
			}
		});
	}
	
	// region DrawDestinationCardsContract.View Implementation
	@Override
	public List<DestinationCard> getSelectedCards() {
		return this.destinationCardsAdapter.getSelectedCards();
	}
	
	@Override
	public void setSelectedCards(List<DestinationCard> selectedCards) {
		
	}
	
	@Override
	public void setCards(List<DestinationCard> cards) {
		this.destinationCardsAdapter.updateData(cards);
		
	}
	
	@Override
	public void setCanContinue(boolean canContinue) {
		this.textViewContinue.setEnabled(canContinue);
		
	}
	
	public void setPresenter(DrawDestinationCardsContract.Presenter presenter) {
		this.presenter = presenter;
	}
	// endregion
}
