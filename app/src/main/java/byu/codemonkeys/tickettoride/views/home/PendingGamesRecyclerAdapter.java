package byu.codemonkeys.tickettoride.views.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.views.OnRecyclerItemClickListener;

/**
 * Created by Ryan on 10/3/2017.
 */

public class PendingGamesRecyclerAdapter extends RecyclerView.Adapter<PendingGamesRecyclerAdapter.PendingGameHolder> {
	
	private List<GameBase> pendingGames;
	private OnRecyclerItemClickListener<GameBase> clickListener;
	
	public PendingGamesRecyclerAdapter(List<GameBase> pendingGames,
									   OnRecyclerItemClickListener<GameBase> clickListener) {
		this.pendingGames = pendingGames;
		this.clickListener = clickListener;
	}
	
	@Override
	public PendingGamesRecyclerAdapter.PendingGameHolder onCreateViewHolder(ViewGroup parent,
																			int viewType) {
		View view = LayoutInflater.from(parent.getContext())
								  .inflate(R.layout.recyclerview_lobby_pendinggame_row,
										   parent,
										   false);
		
		return new PendingGameHolder(view, this.clickListener);
	}
	
	@Override
	public void onBindViewHolder(PendingGamesRecyclerAdapter.PendingGameHolder holder,
								 int position) {
		GameBase pendingGame = pendingGames.get(position);
		holder.bindPendingGame(pendingGame);
	}
	
	@Override
	public int getItemCount() {
		return this.pendingGames.size();
	}
	
	public void updateData(List<GameBase> pendingGames) {
		this.pendingGames.clear();
		this.pendingGames.addAll(pendingGames);
		this.notifyDataSetChanged();
	}
	
	public static class PendingGameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		
		private TextView textViewGameName;
		private TextView textViewOwnerName;
		private TextView textViewPlayersCount;
		private OnRecyclerItemClickListener<GameBase> clickListener;
		private GameBase game;
		
		public PendingGameHolder(View itemView,
								 OnRecyclerItemClickListener<GameBase> clickListener) {
			super(itemView);
			
			textViewGameName = (TextView) itemView.findViewById(R.id.recyclerView_lobby_pendingGame_gameName);
			textViewOwnerName = (TextView) itemView.findViewById(R.id.recyclerView_lobby_pendingGame_ownerName);
			textViewPlayersCount = (TextView) itemView.findViewById(R.id.recyclerView_lobby_pendingGame_players);
			
			this.clickListener = clickListener;
			itemView.setOnClickListener(this);
		}
		
		@Override
		public void onClick(View view) {
			clickListener.onItemClick(game);
		}
		
		public void bindPendingGame(GameBase pendingGame) {
			textViewGameName.setText(pendingGame.getName());
			textViewOwnerName.setText(pendingGame.getOwner().getUsername());
			textViewPlayersCount.setText(String.valueOf(pendingGame.getUsers().size()));
			this.game = pendingGame;
		}
	}
}
