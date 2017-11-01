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
import byu.codemonkeys.tickettoride.presenters.game.ChatHistoryPresenter;
import byu.codemonkeys.tickettoride.presenters.game.DestinationCardsPresenter;
import byu.codemonkeys.tickettoride.presenters.game.DrawDestinationCardsPresenter;
import byu.codemonkeys.tickettoride.presenters.game.GamePresenter;
import byu.codemonkeys.tickettoride.presenters.game.Phase2TestingPresenter;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;

public class GameActivity extends AppCompatActivity implements INavigator, IDisplaysMessages {
	
	
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
				currentView = presenter;
				switch (presenter) {
					case Game:
						GameFragment gameFragment = new GameFragment();
						gameFragment.setPresenter(new GamePresenter(gameFragment,
																	activity,
																	activity,
																	ModelFacade.getInstance()));
						fragment = gameFragment;
						break;
					case Phase2Testing:
						Phase2TestingFragment testingFragment = new Phase2TestingFragment();
						testingFragment.setPresenter(new Phase2TestingPresenter(testingFragment,
																				activity,
																				activity,
																				ModelFacade.getInstance()));
						fragment = testingFragment;
						break;
					case DrawTrainCards:
						GameFragment gameFrag = (GameFragment) getSupportFragmentManager().findFragmentByTag(
								PresenterEnum.Game.name());
						gameFrag.showDrawTrainCardsSidebar();
						fragment = null;
						break;
					case DrawDestinationCards:
						DrawDestinationCardsFragment drawDestinationCardsFragment = new DrawDestinationCardsFragment();
						drawDestinationCardsFragment.setPresenter(new DrawDestinationCardsPresenter(
								drawDestinationCardsFragment,
								activity,
								activity,
								ModelFacade.getInstance()));
						fragment = drawDestinationCardsFragment;
						break;
					case DestinationCards:
						DestinationCardsFragment destinationCardsFragment = new DestinationCardsFragment();
						destinationCardsFragment.setPresenter(new DestinationCardsPresenter(
								destinationCardsFragment,
								activity,
								activity,
								ModelFacade.getInstance()));
						fragment = destinationCardsFragment;
						break;
					case ChatHistory:
						ChatHistoryFragment chatHistoryFragment = new ChatHistoryFragment();
						chatHistoryFragment.setPresenter(new ChatHistoryPresenter(
								chatHistoryFragment,
								activity,
								activity,
								ModelFacade.getInstance()));
						fragment = chatHistoryFragment;
						break;
					default:
						fragment = null;
				}
				if (fragment != null) {
					FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
																				 .replace(R.id.gameActivity_fragmentContainer,
																						  fragment,
																						  presenter.name());
					if (allowBack)
						transaction.addToBackStack(presenter.name());
					transaction.commit();
				}
				
			}
		});
	}
	
	@Override
	public void navigateBack(final PresenterEnum presenter) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (currentView == PresenterEnum.DrawTrainCards) {
					GameFragment gameFrag = (GameFragment) getSupportFragmentManager().findFragmentByTag(
							PresenterEnum.Game.name());
					gameFrag.showGameSidebar();
					
				} else {
					if (presenter == null)
						getSupportFragmentManager().popBackStack();
					else
						getSupportFragmentManager().popBackStack(presenter.name(), 0);
				}
			}
		});
	}
	
	@Override
	public void navigateBack() {
		navigateBack(null);
	}
}
