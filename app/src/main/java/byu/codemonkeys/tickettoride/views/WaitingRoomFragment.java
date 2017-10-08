package byu.codemonkeys.tickettoride.views;


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
import byu.codemonkeys.tickettoride.mvpcontracts.WaitingRoomContract;
import byu.codemonkeys.tickettoride.shared.model.UserBase;

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
		
		recyclerUsers = (RecyclerView) view.findViewById(R.id.waitingRoom_recyclerUsers);
		buttonStartGame = (Button) view.findViewById(R.id.waitingRoom_buttonStartGame);
		buttonLeaveGame = (Button) view.findViewById(R.id.waitingRoom_buttonLeaveGame);
		textViewGameName = (TextView) view.findViewById(R.id.waitingRoom_textViewGameName);
		thisActivity = getActivity();
		
		layoutManagerUsers = new LinearLayoutManager(getActivity());
		recyclerUsers.setLayoutManager(layoutManagerUsers);
		
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
		
		presenter.setDefaults();
		return view;
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
		thisActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				textViewGameName.setText(gameName);
			}
		});
	}
	
	@Override
	public void setWaitingUsers(final List<UserBase> users) {
		Log.d("WR", "Update waiting users");
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
	// endregion
}
