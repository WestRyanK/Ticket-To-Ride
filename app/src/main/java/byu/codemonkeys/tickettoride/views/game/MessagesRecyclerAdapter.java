package byu.codemonkeys.tickettoride.views.game;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.views.OnRecyclerItemClickListener;
import byu.codemonkeys.tickettoride.views.widgets.FontifiedTextView;

/**
 * Created by Ryan on 10/3/2017.
 */

public class MessagesRecyclerAdapter extends RecyclerView.Adapter<MessagesRecyclerAdapter.MessageHolder> {
	
	private List<String> messages;
	
	public MessagesRecyclerAdapter(List<String> messages) {
		this.messages = messages;
	}
	
	@Override
	public MessagesRecyclerAdapter.MessageHolder onCreateViewHolder(ViewGroup parent,
																	int viewType) {
		FontifiedTextView view = new FontifiedTextView(parent.getContext());
		view.setTextSize(25);
		view.setTextColor(parent.getResources().getColor(R.color.colorPrimary));
		//		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trash, parent, false);
		
		return new MessageHolder(view);
	}
	
	@Override
	public void onBindViewHolder(MessagesRecyclerAdapter.MessageHolder holder, int position) {
		String message = messages.get(position);
		holder.bindMessage(message);
	}
	
	@Override
	public int getItemCount() {
		return this.messages.size();
	}
	
	public void updateData(List<String> messages) {
		this.messages.clear();
		this.messages.addAll(messages);
		this.notifyDataSetChanged();
	}
	
	public void appendData(String message) {
		this.messages.add(message);
		this.notifyDataSetChanged();
	}
	
	public static class MessageHolder extends RecyclerView.ViewHolder {
		
		private String message;
		private TextView textViewMessage;
		
		public MessageHolder(View itemView) {
			super(itemView);
			
			this.textViewMessage = (TextView) itemView;
			
		}
		
		public void bindMessage(String message) {
			this.message = message;
			this.textViewMessage.setText(message);
		}
	}
}
