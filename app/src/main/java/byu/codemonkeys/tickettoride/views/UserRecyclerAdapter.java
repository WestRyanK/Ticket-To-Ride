package byu.codemonkeys.tickettoride.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.shared.model.UserBase;

/**
 * Created by Ryan on 10/3/2017.
 */

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.UserHolder> {
	
	private List<UserBase> users;
	
	public UserRecyclerAdapter(List<UserBase> users) {
		this.users = users;
	}
	
	@Override
	public UserRecyclerAdapter.UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
								  .inflate(R.layout.recyclerview_waitingroom_player_row,
										   parent,
										   false);
		return new UserHolder(view);
	}
	
	@Override
	public void onBindViewHolder(UserRecyclerAdapter.UserHolder holder, int position) {
		
		UserBase user = users.get(position);
		holder.bindUser(user);
	}
	
	@Override
	public int getItemCount() {
		return users.size();
	}
	
	public void updateData(List<UserBase> users) {
		this.users.clear();
		this.users.addAll(users);
		this.notifyDataSetChanged();
	}
	
	public static class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		
		private TextView textViewUserName;
		
		public UserHolder(View itemView) {
			super(itemView);
			
			this.textViewUserName = (TextView) itemView.findViewById(R.id.recyclerView_waitingRoom_player_userName);
			itemView.setOnClickListener(this);
		}
		
		@Override
		public void onClick(View view) {
			
		}
		
		public void bindUser(UserBase user) {
			textViewUserName.setText(user.getUsername());
		}
	}
}
