package byu.codemonkeys.tickettoride.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.models.PendingGame;

/**
 * Created by Ryan on 10/3/2017.
 */

public class PendingGamesRecyclerAdapter extends RecyclerView.Adapter<PendingGamesRecyclerAdapter.PendingGameHolder> {
	
	private List<PendingGame> pendingGames;
	
	public PendingGamesRecyclerAdapter(List<PendingGame> pendingGames) {
		this.pendingGames = pendingGames;
	}
	
	@Override
	public PendingGamesRecyclerAdapter.PendingGameHolder onCreateViewHolder(ViewGroup parent,
																			int viewType) {
		View view = LayoutInflater.from(parent.getContext())
								  .inflate(R.layout.recyclerview_lobby_pendinggame_row,
										   parent,
										   false);
		
		return new PendingGameHolder(view);
	}
	
	@Override
	public void onBindViewHolder(PendingGamesRecyclerAdapter.PendingGameHolder holder,
								 int position) {
		PendingGame pendingGame = pendingGames.get(position);
		holder.bindPendingGame(pendingGame);
	}
	
	@Override
	public int getItemCount() {
		return this.pendingGames.size();
	}
	
	public void updateData(List<PendingGame> pendingGames) {
		this.pendingGames.clear();
		this.pendingGames.addAll(pendingGames);
		this.notifyDataSetChanged();
	}
	
	public static class PendingGameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		
		private TextView textViewGameName;
		private TextView textViewPlayers;
		
		public PendingGameHolder(View itemView) {
			super(itemView);
			
			textViewGameName = (TextView) itemView.findViewById(R.id.recyclerView_lobby_pendingGame_gameName);
			textViewPlayers = (TextView) itemView.findViewById(R.id.recyclerView_lobby_pendingGame_players);
			
			itemView.setOnClickListener(this);
		}
		
		@Override
		public void onClick(View view) {
			
		}
		
		public void bindPendingGame(PendingGame pendingGame) {
			textViewGameName.setText(pendingGame.getGameName());
			textViewPlayers.setText(String.format("%1$d to %2$d",
												  pendingGame.getMinPlayers(),
												  pendingGame.getMaxPlayers()));
		}
	}
}
