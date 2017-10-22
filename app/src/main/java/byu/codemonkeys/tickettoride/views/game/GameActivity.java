package byu.codemonkeys.tickettoride.views.game;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.presenters.ChatHistoryPresenter;
import byu.codemonkeys.tickettoride.presenters.DestinationCardsPresenter;
import byu.codemonkeys.tickettoride.presenters.DrawDestinationCardsPresenter;
import byu.codemonkeys.tickettoride.presenters.Phase2TestingPresenter;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;

public class GameActivity extends AppCompatActivity implements INavigator, IDisplaysMessages {
	
	private static final String GAME_TAG = "GAME_TAG";
	private static final String PHASE2TESTING_TAG = "PHASE2TESTING_TAG";
	private static final String DESTINATION_CARDS_TAG = "DESTINATION_CARDS_TAG";
	private static final String DRAW_DESTINATION_CARDS_TAG = "DRAW_DESTINATION_CARDS_TAG";
	private static final String CHAT_HISTORY_TAG = "CHAT_HISTORY_TAG";
	
	private PresenterEnum currentView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		navigate(PresenterEnum.Game, true);
	}
	
	@Override
	public void displayMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void navigate(final PresenterEnum presenter, final boolean allowBack) {
		final GameActivity activity = this;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Fragment fragment;
				String tag;
				currentView = presenter;
				switch (presenter) {
					case Game:
						GameFragment gameFragment = new GameFragment();
						tag = GAME_TAG;
						fragment = gameFragment;
						break;
					case Phase2Testing:
						Phase2TestingFragment testingFragment = new Phase2TestingFragment();
						testingFragment.setPresenter(new Phase2TestingPresenter(testingFragment,
																				activity,
																				activity,
																				ModelFacade.getInstance()));
						tag = PHASE2TESTING_TAG;
						fragment = testingFragment;
						break;
					case DrawTrainCards:
						GameFragment gameFrag = (GameFragment) getSupportFragmentManager().findFragmentByTag(
								GAME_TAG);
						gameFrag.showDrawTrainCardsSidebar();
						fragment = null;
						tag = null;
						
						break;
					case DrawDestinationCards:
						DrawDestinationCardsFragment drawDestinationCardsFragment = new DrawDestinationCardsFragment();
						drawDestinationCardsFragment.setPresenter(new DrawDestinationCardsPresenter(
								drawDestinationCardsFragment,
								activity,
								activity,
								ModelFacade.getInstance()));
						fragment = drawDestinationCardsFragment;
						tag = DRAW_DESTINATION_CARDS_TAG;
						break;
					case DestinationCards:
						DestinationCardsFragment destinationCardsFragment = new DestinationCardsFragment();
						destinationCardsFragment.setPresenter(new DestinationCardsPresenter(
								destinationCardsFragment,
								activity,
								activity,
								ModelFacade.getInstance()));
						fragment = destinationCardsFragment;
						tag = DESTINATION_CARDS_TAG;
						break;
					case ChatHistory:
						ChatHistoryFragment chatHistoryFragment = new ChatHistoryFragment();
						chatHistoryFragment.setPresenter(new ChatHistoryPresenter(
								chatHistoryFragment,
								activity,
								activity,
								ModelFacade.getInstance()));
						fragment = chatHistoryFragment;
						tag = CHAT_HISTORY_TAG;
						break;
					default:
						fragment = null;
						tag = null;
				}
				if (fragment != null) {
					FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
																				 .replace(R.id.gameActivity_fragmentContainer,
																						  fragment,
																						  tag);
					if (allowBack)
						transaction.addToBackStack(null);
					transaction.commit();
				}
				
			}
		});
	}
	
	@Override
	public void navigateBack() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (currentView == PresenterEnum.DrawTrainCards) {
					GameFragment gameFrag = (GameFragment) getSupportFragmentManager().findFragmentByTag(
							GAME_TAG);
					gameFrag.showGameSidebar();
					
				} else {
					getSupportFragmentManager().popBackStack();
				}
			}
		});
		
	}
}
