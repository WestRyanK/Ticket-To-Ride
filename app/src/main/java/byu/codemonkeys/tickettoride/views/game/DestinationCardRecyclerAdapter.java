package byu.codemonkeys.tickettoride.views.game;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.views.OnRecyclerItemClickListener;
import byu.codemonkeys.tickettoride.views.widgets.DestinationCardWidget;

/**
 * Created by Ryan on 10/18/2017.
 */

public class DestinationCardRecyclerAdapter extends RecyclerView.Adapter<DestinationCardRecyclerAdapter.DestinationCardHolder> {
	
	List<DestinationCard> destinationCards;
	private OnRecyclerItemClickListener<DestinationCard> clickListener;
	private Set<Integer> selectedCardIndices;
	private boolean canSelectCards;
	
	public DestinationCardRecyclerAdapter(List<DestinationCard> destinationCards,
										  OnRecyclerItemClickListener<DestinationCard> clickListener) {
		this.destinationCards = destinationCards;
		this.clickListener = clickListener;
		this.selectedCardIndices = new HashSet<>();
	}
	
	@Override
	public DestinationCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		DestinationCardWidget cardWidget = new DestinationCardWidget(parent.getContext());
		
		return new DestinationCardHolder(cardWidget, this.clickListener, this);
	}
	
	@Override
	public void onBindViewHolder(DestinationCardHolder holder, int position) {
		
		int wrappedPosition = wrapPosition(position);
		DestinationCard destinationCard = destinationCards.get(wrappedPosition);
		holder.bindDestinationCard(destinationCard,
								   wrappedPosition,
								   selectedCardIndices.contains(wrappedPosition));
	}
	
	private int wrapPosition(int position) {
		int wrappedPosition;
		if (destinationCards.size() > 0)
			wrappedPosition = position % destinationCards.size();
		else
			wrappedPosition = position;
		return wrappedPosition;
	}
	
	public void toggleSelection(int position) {
		if (canSelectCards) {
			int wrappedPosition = wrapPosition(position);
			if (selectedCardIndices.contains(wrappedPosition)) {
				selectedCardIndices.remove(wrappedPosition);
			} else {
				selectedCardIndices.add(wrappedPosition);
			}
		}
	}
	
	public List<DestinationCard> getSelectedCards() {
		
		List<DestinationCard> selectedCards = new ArrayList<>();
		
		for (int index : this.selectedCardIndices) {
			selectedCards.add(this.destinationCards.get(index));
		}
		return selectedCards;
	}
	
	public boolean isCardSelected(int position) {
		int wrappedPosition = wrapPosition(position);
		return selectedCardIndices.contains(wrappedPosition);
	}
	
	@Override
	public int getItemCount() {
		if (destinationCards.size() > 3)
			return Integer.MAX_VALUE;//destinationCards.size();
		else
			return destinationCards.size();
	}
	
	public void updateData(List<DestinationCard> cards) {
		this.destinationCards.clear();
		this.destinationCards.addAll(cards);
		this.notifyDataSetChanged();
	}
	
	public boolean isCanSelectCards() {
		return canSelectCards;
	}
	
	public void setCanSelectCards(boolean canSelectCards) {
		this.canSelectCards = canSelectCards;
	}
	
	public class DestinationCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		DestinationCardWidget cardWidget;
		private OnRecyclerItemClickListener<DestinationCard> clickListener;
		private DestinationCard destinationCard;
		private int position;
		private DestinationCardRecyclerAdapter adapter;
		
		public DestinationCardHolder(View view,
									 OnRecyclerItemClickListener<DestinationCard> clickListener,
									 DestinationCardRecyclerAdapter adapter) {
			super(view);
			this.cardWidget = (DestinationCardWidget) view;
			
			this.clickListener = clickListener;
			view.setOnClickListener(this);
			this.adapter = adapter;
		}
		
		@Override
		public void onClick(View view) {
			this.adapter.toggleSelection(this.position);
			this.cardWidget.setSelected(this.adapter.isCardSelected(this.position));
			this.cardWidget.invalidate();
		}
		
		public void bindDestinationCard(DestinationCard destinationCard,
										int position,
										boolean isSelected) {
			cardWidget.setDestinationA(destinationCard.getDestinationA());
			cardWidget.setDestinationB(destinationCard.getDestinationB());
			cardWidget.setPointValue(destinationCard.getPointValue());
			this.position = position;
			cardWidget.setSelected(isSelected);
		}
	}
}
