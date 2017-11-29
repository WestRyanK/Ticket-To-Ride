package byu.codemonkeys.tickettoride.views.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.shared.model.ExistingGame;
import byu.codemonkeys.tickettoride.views.OnRecyclerItemClickListener;

/**
 * Created by Ryan on 10/3/2017.
 */

public class ExistingGamesRecyclerAdapter extends RecyclerView.Adapter<ExistingGamesRecyclerAdapter.ExistingGameHolder> {
	
	private List<ExistingGame> existingGames;
	private OnRecyclerItemClickListener<ExistingGame> clickListener;
	
	public ExistingGamesRecyclerAdapter(List<ExistingGame> existingGames,
										OnRecyclerItemClickListener<ExistingGame> clickListener) {
		this.existingGames = existingGames;
		this.clickListener = clickListener;
	}
	
	@Override
	public ExistingGameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
								  .inflate(R.layout.recyclerview_lobby_existinggame_row,
										   parent,
										   false);
		
		return new ExistingGameHolder(view, this.clickListener);
	}
	
	@Override
	public void onBindViewHolder(ExistingGameHolder holder, int position) {
		ExistingGame existingGame = existingGames.get(position);
		holder.bindExistingGame(existingGame);
	}
	
	@Override
	public int getItemCount() {
		return this.existingGames.size();
	}
	
	public void updateData(List<ExistingGame> existingGames) {
		this.existingGames.clear();
		this.existingGames.addAll(existingGames);
		this.notifyDataSetChanged();
	}
	
	public static class ExistingGameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		
		private TextView textViewGameName;
		private TextView textViewOwnerName;
		private TextView textViewPlayerTurn;
		private OnRecyclerItemClickListener<ExistingGame> clickListener;
		private ExistingGame game;
		
		public ExistingGameHolder(View itemView,
								  OnRecyclerItemClickListener<ExistingGame> clickListener) {
			super(itemView);
			
			textViewGameName = (TextView) itemView.findViewById(R.id.recyclerView_lobby_existingGame_gameName);
			textViewOwnerName = (TextView) itemView.findViewById(R.id.recyclerView_lobby_existingGame_ownerName);
			textViewPlayerTurn = (TextView) itemView.findViewById(R.id.recyclerView_lobby_existingGame_playerTurn);
			
			this.clickListener = clickListener;
			itemView.setOnClickListener(this);
		}
		
		@Override
		public void onClick(View view) {
			clickListener.onItemClick(game);
		}
		
		public void bindExistingGame(ExistingGame existingGame) {
			textViewGameName.setText(existingGame.getName());
			textViewOwnerName.setText(existingGame.getOwner().getUsername());
			textViewPlayerTurn.setText(String.valueOf(existingGame.getCurrentPlayerTurn()));
			this.game = existingGame;
		}
	}
}
