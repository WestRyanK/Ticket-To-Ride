package byu.codemonkeys.tickettoride.views.home;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.mvpcontracts.home.LobbyContract;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.views.OnRecyclerItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LobbyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LobbyFragment extends Fragment implements LobbyContract.View {
	private LobbyContract.Presenter presenter;
	private FloatingActionButton fabCreateGame;
	private RecyclerView recyclerPendingGames;
	private LinearLayoutManager layoutManagerPendingGames;
	private PendingGamesRecyclerAdapter pendingGamesAdapter;
	
	
	public LobbyFragment() {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_lobby, container, false);
		
		fabCreateGame = (FloatingActionButton) view.findViewById(R.id.lobby_fabCreateGame);
		
		recyclerPendingGames = (RecyclerView) view.findViewById(R.id.lobby_recyclerPendingGames);
		layoutManagerPendingGames = new LinearLayoutManager(getActivity());
		recyclerPendingGames.setLayoutManager(layoutManagerPendingGames);
		
		fabCreateGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.createGame();
			}
		});
		
		presenter.setDefaults();
		Log.d("LOBBY", "Creating lobby...");
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		presenter.startPolling();
		Log.d("LOBBY", "Resuming lobby...");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		presenter.stopPolling();
		Log.d("LOBBY", "Pausing lobby...");
	}
	
	
	// region LobbyContract.View implementation
	@Override
	public void setPendingGames(final List<GameBase> pendingGames) {
		
		if (pendingGamesAdapter == null) {
			pendingGamesAdapter = new PendingGamesRecyclerAdapter(pendingGames,
																  new OnRecyclerItemClickListener<GameBase>() {
																	  @Override
																	  public void onItemClick(
																			  GameBase game) {
																		  presenter.joinGame(game);
																	  }
																  });
			recyclerPendingGames.setAdapter(pendingGamesAdapter);
		} else {
			recyclerPendingGames.setAdapter(pendingGamesAdapter);
			pendingGamesAdapter.updateData(pendingGames);
		}
	}
	
	public void setPresenter(LobbyContract.Presenter presenter) {
		this.presenter = presenter;
	}
	// endregion
}
