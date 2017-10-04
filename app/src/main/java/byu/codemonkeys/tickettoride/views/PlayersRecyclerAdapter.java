package byu.codemonkeys.tickettoride.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.models.Player;

/**
 * Created by Ryan on 10/3/2017.
 */

public class PlayersRecyclerAdapter extends RecyclerView.Adapter<PlayersRecyclerAdapter.PlayerHolder> {
	
	private List<Player> players;
	
	public PlayersRecyclerAdapter(List<Player> players) {
		this.players = players;
	}
	
	@Override
	public PlayersRecyclerAdapter.PlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
								  .inflate(R.layout.recyclerview_waitingroom_player_row,
										   parent,
										   false);
		return new PlayerHolder(view);
	}
	
	@Override
	public void onBindViewHolder(PlayersRecyclerAdapter.PlayerHolder holder, int position) {
		
		Player player = players.get(position);
		holder.bindPlayer(player);
	}
	
	@Override
	public int getItemCount() {
		return players.size();
	}
	
	public void updateData(List<Player> players) {
		this.players.clear();
		this.players.addAll(players);
		this.notifyDataSetChanged();
	}
	
	public static class PlayerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		
		private TextView textViewPlayerName;
		
		public PlayerHolder(View itemView) {
			super(itemView);
			
			this.textViewPlayerName = (TextView) itemView.findViewById(R.id.recyclerView_waitingRoom_player_playerName);
			itemView.setOnClickListener(this);
		}
		
		@Override
		public void onClick(View view) {
			
		}
		
		public void bindPlayer(Player player) {
			textViewPlayerName.setText(player.getUsername());
		}
	}
}
