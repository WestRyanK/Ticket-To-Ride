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
import byu.codemonkeys.tickettoride.shared.model.ExistingGame;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.views.OnRecyclerItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LobbyFragment extends Fragment implements LobbyContract.View {
	private LobbyContract.Presenter presenter;
	private FloatingActionButton fabCreateGame;
	private RecyclerView recyclerPendingGames;
	private LinearLayoutManager layoutManagerPendingGames;
	private PendingGamesRecyclerAdapter pendingGamesAdapter;
	
	private RecyclerView recyclerExistingGames;
	private LinearLayoutManager layoutManagerExistingGames;
	private ExistingGamesRecyclerAdapter existingGamesAdapter;
	
	
	public LobbyFragment() {
		// Required empty public constructor
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_lobby, container, false);
		
		getViews(view);
		setOnClickListeners();
		createRecyclers(view);
		
		presenter.setDefaults();
		return view;
	}
	
	private void getViews(View view) {
		fabCreateGame = (FloatingActionButton) view.findViewById(R.id.lobby_fabCreateGame);
	}
	
	private void setOnClickListeners() {
		fabCreateGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.createGame();
			}
		});
		
	}
	
	private void createRecyclers(View view) {
		recyclerPendingGames = (RecyclerView) view.findViewById(R.id.lobby_recyclerNewGames);
		layoutManagerPendingGames = new LinearLayoutManager(getActivity());
		recyclerPendingGames.setLayoutManager(layoutManagerPendingGames);
		
		recyclerExistingGames = (RecyclerView) view.findViewById(R.id.lobby_recyclerMyGames);
		layoutManagerExistingGames = new LinearLayoutManager(getActivity());
		recyclerExistingGames.setLayoutManager(layoutManagerExistingGames);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		presenter.startPolling();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		presenter.stopPolling();
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
																		  presenter.joinPendingGame(
																				  game);
																	  }
																  });
			recyclerPendingGames.setAdapter(pendingGamesAdapter);
		} else {
			recyclerPendingGames.setAdapter(pendingGamesAdapter);
			pendingGamesAdapter.updateData(pendingGames);
		}
	}
	
	@Override
	public void setExistingGames(List<ExistingGame> existingGames) {
		if (existingGamesAdapter == null) {
			existingGamesAdapter = new ExistingGamesRecyclerAdapter(existingGames,
																  new OnRecyclerItemClickListener<ExistingGame>() {
																	  @Override
																	  public void onItemClick(
																			  ExistingGame game) {
																		  presenter.joinExistingGame(
																				  game);
																	  }
																  });
			recyclerExistingGames.setAdapter(existingGamesAdapter);
		} else {
			recyclerExistingGames.setAdapter(existingGamesAdapter);
			existingGamesAdapter.updateData(existingGames);
		}
		
	}
	
	public void setPresenter(LobbyContract.Presenter presenter) {
		this.presenter = presenter;
	}
	// endregion
}
