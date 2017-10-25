package byu.codemonkeys.tickettoride.views.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.models.PlayerColorEnum;
import byu.codemonkeys.tickettoride.mvpcontracts.game.GameSidebarContract;
import byu.codemonkeys.tickettoride.views.widgets.MapPointBubble;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameSidebarFragment extends Fragment implements GameSidebarContract.View {
	
	private ImageButton imageButtonDrawTrainCards;
	private ImageButton imageButtonDestinationCards;
	private ImageButton imageButtonChatHistory;
	private TextView textViewPhase2Testing;
	private GameSidebarContract.Presenter presenter;
	
	public GameSidebarFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_game_sidebar, container, false);
		getViews(view);
		setHandlers(view);
		return view;
	}
	
	private void getViews(View view) {
		imageButtonDrawTrainCards = (ImageButton) view.findViewById(R.id.gameSideBar_imageButtonDrawTrainCards);
		imageButtonDestinationCards = (ImageButton) view.findViewById(R.id.gameSideBar_imageButtonDestinationCards);
		imageButtonChatHistory = (ImageButton) view.findViewById(R.id.gameSideBar_imageButtonChatHistory);
		textViewPhase2Testing = (TextView) view.findViewById(R.id.gameSideBar_textViewPhase2Testing);
	}
	
	private void setHandlers(View view) {
		imageButtonDrawTrainCards.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.navigateDrawTrainCards();
			}
		});
		
		imageButtonDestinationCards.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.navigateDestinationCards();
			}
		});
		
		imageButtonChatHistory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.navigateChatHistory();
			}
		});
		textViewPhase2Testing.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.navigatePhase2Testing();
				
			}
		});
	}
	
	public void setPresenter(GameSidebarContract.Presenter presenter) {
		this.presenter = presenter;
	}
}
