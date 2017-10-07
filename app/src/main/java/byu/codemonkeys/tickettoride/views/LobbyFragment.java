package byu.codemonkeys.tickettoride.views;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.mvpcontracts.LobbyContract;
import byu.codemonkeys.tickettoride.shared.model.GameBase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LobbyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LobbyFragment extends Fragment implements LobbyContract.View {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	//	private static final String ARG_PARAM1 = "param1";
	//	private static final String ARG_PARAM2 = "param2";
	
	// TODO: Rename and change types of parameters
	//	private String mParam1;
	//	private String mParam2;
	private LobbyContract.Presenter presenter;
	private FloatingActionButton fabCreateGame;
	private RecyclerView recyclerPendingGames;
	private LinearLayoutManager layoutManagerPendingGames;
	private PendingGamesRecyclerAdapter pendingGamesAdapter;
	
	
	public LobbyFragment() {
		// Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * <p>
	 * //	 * @param param1 Parameter 1.
	 * //	 * @param param2 Parameter 2.
	 *
	 * @return A new instance of fragment LobbyFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static LobbyFragment newInstance() {
		LobbyFragment fragment = new LobbyFragment();
		Bundle args = new Bundle();
		//		args.putString(ARG_PARAM1, param1);
		//		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			//			mParam1 = getArguments().getString(ARG_PARAM1);
			//			mParam2 = getArguments().getString(ARG_PARAM2);
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
		return view;
	}
	
	// region LobbyContract.View implementation
	@Override
	public void setPendingGames(List<GameBase> pendingGames) {
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
			pendingGamesAdapter.updateData(pendingGames);
		}
	}
	
	public void setPresenter(LobbyContract.Presenter presenter) {
		this.presenter = presenter;
	}
	// endregion
}
