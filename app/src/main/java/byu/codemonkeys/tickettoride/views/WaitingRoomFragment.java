package byu.codemonkeys.tickettoride.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.models.PendingGame;
import byu.codemonkeys.tickettoride.mvpcontracts.WaitingRoomContract;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaitingRoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitingRoomFragment extends Fragment implements WaitingRoomContract.View {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	//	private static final String ARG_PARAM1 = "param1";
	//	private static final String ARG_PARAM2 = "param2";
	
	// TODO: Rename and change types of parameters
	//	private String mParam1;
	//	private String mParam2;
	private WaitingRoomContract.Presenter presenter;
	private RecyclerView recyclerPlayers;
	private LinearLayoutManager layoutManagerPlayers;
	private PlayersRecyclerAdapter playerAdapter;
	private Button buttonStartGame;
	private Button buttonLeaveGame;
	
	
	public WaitingRoomFragment() {
		// Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * <p>
	 * //	 * @param param1 Parameter 1.
	 * //	 * @param param2 Parameter 2.
	 *
	 * @return A new instance of fragment WaitingRoomFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static WaitingRoomFragment newInstance() {
		WaitingRoomFragment fragment = new WaitingRoomFragment();
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
		View view = inflater.inflate(R.layout.fragment_waiting_room, container, false);
		
		recyclerPlayers = (RecyclerView) view.findViewById(R.id.waitingRoom_recyclerPlayers);
		buttonStartGame = (Button) view.findViewById(R.id.waitingRoom_buttonStartGame);
		buttonLeaveGame = (Button) view.findViewById(R.id.waitingRoom_buttonLeaveGame);
	
		layoutManagerPlayers = new LinearLayoutManager(getActivity());
		recyclerPlayers.setLayoutManager(layoutManagerPlayers);
		
		buttonStartGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.startGame();
			}
		});
		
		buttonLeaveGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.leaveGame();
			}
		});
		
		return view;
	}
	
	public void setPresenter(WaitingRoomContract.Presenter presenter) {
		this.presenter = presenter;
	}
	
	// region WaitingRoomContract.View Implementation
	@Override
	public void setPendingGameInfo(PendingGame pendingGame) {
		if (playerAdapter == null) {
			playerAdapter = new PlayersRecyclerAdapter(pendingGame.getPlayers());
			recyclerPlayers.setAdapter(playerAdapter);
		} else {
			playerAdapter.updateData(pendingGame.getPlayers());
		}
	}
	
	@Override
	public void setIsStartGameVisible(boolean isStartGameVisible) {
		if (isStartGameVisible)
			this.buttonStartGame.setVisibility(View.VISIBLE);
		else
			this.buttonStartGame.setVisibility(View.INVISIBLE);
	}
	// endregion
}
