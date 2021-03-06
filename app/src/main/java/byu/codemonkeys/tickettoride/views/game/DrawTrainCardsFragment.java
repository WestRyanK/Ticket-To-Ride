package byu.codemonkeys.tickettoride.views.game;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.mvpcontracts.game.DrawTrainCardsContract;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;
import byu.codemonkeys.tickettoride.views.widgets.TrainCardWidget;

import static byu.codemonkeys.tickettoride.shared.model.cards.CardType.Red;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawTrainCardsFragment extends Fragment implements DrawTrainCardsContract.View {
	
	private DrawTrainCardsContract.Presenter presenter;
	private TrainCardWidget cardDeck;
	private TextView textViewBack;
	private List<TrainCardWidget> cards;
	
	public DrawTrainCardsFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_draw_train_cards, container, false);
		getViews(view);
		setClickListeners();
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		presenter.loadCards();
	}
	
	private void setClickListeners() {
		this.textViewBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.navigateBack();
			}
		});
		
		this.cardDeck.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.drawDeckCard();
			}
		});
		
		for (int i = 0; i < this.cards.size(); i++) {
			final int finalI = i;
			this.cards.get(i).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					presenter.drawFaceUpCard(finalI);
				}
			});
		}
	}
	
	private void getViews(View view) {
		textViewBack = (TextView) view.findViewById(R.id.drawTrainCards_textViewBack);
		this.cardDeck = (TrainCardWidget) view.findViewById(R.id.drawTrainCards_cardDeck);
		this.cards = new ArrayList<>();
		this.cards.add((TrainCardWidget) view.findViewById(R.id.drawTrainCards_card0));
		this.cards.add((TrainCardWidget) view.findViewById(R.id.drawTrainCards_card1));
		this.cards.add((TrainCardWidget) view.findViewById(R.id.drawTrainCards_card2));
		this.cards.add((TrainCardWidget) view.findViewById(R.id.drawTrainCards_card3));
		this.cards.add((TrainCardWidget) view.findViewById(R.id.drawTrainCards_card4));
	}
	
	public void setPresenter(DrawTrainCardsContract.Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void setFaceUpCards(final List<TrainCard> faceUpCards) {
		if (this.getActivity() != null) {
			if (cards.size() != cards.size())
				throw new IllegalArgumentException("Wrong number of cards! Expected: " +
														   cards.size() +
														   "Actual: " +
														   cards.size());
			
			for (int i = 0; i < cards.size(); i++) {
				
				int drawableID = -1;
				
				if (i >= faceUpCards.size() || faceUpCards.get(i) == null) {
					cards.get(i).setVisibility(View.INVISIBLE);
					continue;
				}
				
				switch (faceUpCards.get(i).getCardColor()) {
					case Red:
						drawableID = R.drawable.card_red;
						break;
					case Orange:
						drawableID = R.drawable.card_orange;
						break;
					case Yellow:
						drawableID = R.drawable.card_yellow;
						break;
					case Green:
						drawableID = R.drawable.card_green;
						break;
					case Blue:
						drawableID = R.drawable.card_blue;
						break;
					case Purple:
						drawableID = R.drawable.card_purple;
						break;
					case Black:
						drawableID = R.drawable.card_black;
						break;
					case White:
						drawableID = R.drawable.card_white;
						break;
					case Wild:
						drawableID = R.drawable.card_wild;
						break;
				}
				Drawable drawable = null;
				if (drawableID != -1) {
					drawable = getResources().getDrawable(drawableID);
				}
				cards.get(i).setCardDrawable(drawable);
			}
		}
	}
	
	@Override
	public void setNumHidden(final int cardCount) {
		if (cardCount < 1)
			cardDeck.setVisibility(View.INVISIBLE);
		else {
			cardDeck.setVisibility(View.VISIBLE);
			cardDeck.setCount(cardCount);
		}
	}
}
