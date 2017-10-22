package byu.codemonkeys.tickettoride.views.game;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.models.DestinationCard;
import byu.codemonkeys.tickettoride.views.OnRecyclerItemClickListener;
import byu.codemonkeys.tickettoride.views.widgets.DestinationCardWidget;

/**
 * Created by Ryan on 10/18/2017.
 */

public class DestinationCardRecyclerAdapter extends RecyclerView.Adapter<DestinationCardRecyclerAdapter.DestinationCardHolder> {
	
	List<DestinationCard> destinationCards;
	private OnRecyclerItemClickListener<DestinationCard> clickListener;
	
	public DestinationCardRecyclerAdapter(List<DestinationCard> destinationCards,
										  OnRecyclerItemClickListener<DestinationCard> clickListener) {
		this.destinationCards = destinationCards;
		this.clickListener = clickListener;
	}
	
	@Override
	public DestinationCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		DestinationCardWidget cardWidget = new DestinationCardWidget(parent.getContext());
		
		return new DestinationCardHolder(cardWidget, this.clickListener);
	}
	
	@Override
	public void onBindViewHolder(DestinationCardHolder holder, int position) {
		
		DestinationCard destinationCard = destinationCards.get(position % destinationCards.size());
		holder.bindDestinationCard(destinationCard);
	}
	
	@Override
	public int getItemCount() {
		return Integer.MAX_VALUE;//destinationCards.size();
	}
	
	public void updateData(List<DestinationCard> cards) {
		this.destinationCards.clear();
		this.destinationCards.addAll(cards);
		this.notifyDataSetChanged();
	}
	
	public class DestinationCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		DestinationCardWidget cardWidget;
		private OnRecyclerItemClickListener<DestinationCard> clickListener;
		private DestinationCard destinationCard;
		
		public DestinationCardHolder(View view,
									 OnRecyclerItemClickListener<DestinationCard> clickListener) {
			super(view);
			this.cardWidget = (DestinationCardWidget) view;
			
			this.clickListener = clickListener;
			view.setOnClickListener(this);
			
		}
		
		@Override
		public void onClick(View view) {
			clickListener.onItemClick(destinationCard);
		}
		
		public void bindDestinationCard(DestinationCard destinationCard) {
			cardWidget.setDestinationA(destinationCard.getDestinationA());
			cardWidget.setDestinationB(destinationCard.getDestinationB());
			cardWidget.setPointValue(destinationCard.getPointValue());
		}
	}
}
