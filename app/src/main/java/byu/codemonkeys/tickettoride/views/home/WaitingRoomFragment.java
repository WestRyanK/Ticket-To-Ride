package byu.codemonkeys.tickettoride.views.home;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.mvpcontracts.home.WaitingRoomContract;
import byu.codemonkeys.tickettoride.shared.model.UserBase;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class WaitingRoomFragment extends Fragment implements WaitingRoomContract.View {
	
	private TextView textViewGameName;
	private WaitingRoomContract.Presenter presenter;
	private RecyclerView recyclerUsers;
	private LinearLayoutManager layoutManagerUsers;
	private UserRecyclerAdapter userAdapter;
	private Button buttonStartGame;
	private Button buttonLeaveGame;
	private Activity thisActivity;
	
	
	public WaitingRoomFragment() {
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
		View view = inflater.inflate(R.layout.fragment_waiting_room, container, false);
		
		getViews(view);
		setOnClickListeners();
		setUpRecycler();
		
		thisActivity = getActivity();
		if (presenter != null)
			presenter.setDefaults();
		return view;
	}
	
	private void setUpRecycler() {
		layoutManagerUsers = new LinearLayoutManager(getActivity());
		recyclerUsers.setLayoutManager(layoutManagerUsers);
	}
	
	private void setOnClickListeners() {
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
	}
	
	private void getViews(View view) {
		recyclerUsers = (RecyclerView) view.findViewById(R.id.waitingRoom_recyclerUsers);
		//		buttonStartGame = (Button) view.findViewById(R.id.waitingRoom_buttonStartGame);
		//		buttonLeaveGame = (Button) view.findViewById(R.id.waitingRoom_buttonLeaveGame);
		buttonStartGame = (Button) view.findViewById(R.id.waitingRoom_buttonStartGame);
		buttonLeaveGame = (Button) view.findViewById(R.id.waitingRoom_buttonLeaveGame);
		textViewGameName = (TextView) view.findViewById(R.id.waitingRoom_textViewGameName);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		presenter.startPolling();
		Log.d("WR", "Resuming WaitingRoom...");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		presenter.stopPolling();
		Log.d("WR", "Pausing WaitingRoom...");
	}
	
	public void setPresenter(WaitingRoomContract.Presenter presenter) {
		this.presenter = presenter;
	}
	
	// region WaitingRoomContract.View Implementation
	@Override
	public void setPendingGameName(final String gameName) {
		textViewGameName.setText(gameName);
	}
	
	@Override
	public void setWaitingUsers(final List<UserBase> users) {
		thisActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (userAdapter == null) {
					userAdapter = new UserRecyclerAdapter(users);
					recyclerUsers.setAdapter(userAdapter);
				} else {
					recyclerUsers.setAdapter(userAdapter);
					userAdapter.updateData(users);
				}
			}
		});
	}
	
	@Override
	public void setIsStartGameVisible(boolean isStartGameVisible) {
		if (isStartGameVisible)
			this.buttonStartGame.setVisibility(View.VISIBLE);
		else
			this.buttonStartGame.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public void setCanStartGame(boolean canStartGame) {
		this.buttonStartGame.setEnabled(canStartGame);
	}
	// endregion
}
