package byu.codemonkeys.tickettoride.views.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.async.AndroidTask;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.presenters.home.ConnectionSettingsPresenter;
import byu.codemonkeys.tickettoride.presenters.home.CreateGamePresenter;
import byu.codemonkeys.tickettoride.presenters.home.LobbyPresenter;
import byu.codemonkeys.tickettoride.presenters.home.LoginPresenter;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;
import byu.codemonkeys.tickettoride.presenters.home.RegisterPresenter;
import byu.codemonkeys.tickettoride.presenters.home.WaitingRoomPresenter;
import byu.codemonkeys.tickettoride.views.game.GameActivity;

public class HomeActivity extends AppCompatActivity implements INavigator, IDisplaysMessages {
	
	public static final String PARAM_PRESENTER = "ParamPresenter";
	private FrameLayout fragmentContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.fragmentContainer = (FrameLayout) this.findViewById(R.id.main_fragmentContainer);
		
		
		int enumIndex = getIntent().getIntExtra(PARAM_PRESENTER, PresenterEnum.Login.ordinal());
		PresenterEnum presenter = PresenterEnum.values()[enumIndex];
		navigate(presenter, false);
	}
	
	// region INavigator Implementation
	@Override
	public void navigate(final PresenterEnum presenter, final boolean allowBack) {
		final HomeActivity activity = this;
		Fragment fragment;
		switch (presenter) {
			case Login:
				LoginFragment loginFragment = new LoginFragment();
				loginFragment.setPresenter(new LoginPresenter(loginFragment,
															  activity,
															  activity,
															  ModelFacade.getInstance()));
				fragment = loginFragment;
				break;
			case Register:
				RegisterFragment registerFragment = new RegisterFragment();
				registerFragment.setPresenter(new RegisterPresenter(registerFragment,
																	activity,
																	activity,
																	ModelFacade.getInstance()));
				fragment = registerFragment;
				break;
			case ConnectionSettings:
				ConnectionSettingsFragment connectionSettingsFragment = new ConnectionSettingsFragment();
				connectionSettingsFragment.setPresenter(new ConnectionSettingsPresenter(
						connectionSettingsFragment,
						activity,
						activity,
						ModelFacade.getInstance()));
				fragment = connectionSettingsFragment;
				break;
			case CreateGame:
				CreateGameFragment createGameFragment = new CreateGameFragment();
				createGameFragment.setPresenter(new CreateGamePresenter(createGameFragment,
																		activity,
																		activity,
																		ModelFacade.getInstance()));
				fragment = createGameFragment;
				break;
			case Lobby:
				LobbyFragment lobbyFragment = new LobbyFragment();
				lobbyFragment.setPresenter(new LobbyPresenter(lobbyFragment,
															  activity,
															  activity,
															  ModelFacade.getInstance()));
				fragment = lobbyFragment;
				break;
			case WaitingRoom:
				WaitingRoomFragment waitingRoomFragment = WaitingRoomFragment.newInstance();
				waitingRoomFragment.setPresenter(new WaitingRoomPresenter(waitingRoomFragment,
																		  activity,
																		  activity,
																		  ModelFacade.getInstance()));
				fragment = waitingRoomFragment;
				break;
			case Game:
				Intent intent = new Intent(HomeActivity.this, GameActivity.class);
				startActivity(intent);
			default:
				fragment = null;
		}
		if (fragment != null) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
																		 .replace(R.id.main_fragmentContainer,
																				  fragment);
			if (allowBack)
				transaction.addToBackStack(null);
			transaction.commit();
		}
		
	}
	
	@Override
	public void navigateBack() {
		navigateBack(null);
	}
	
	@Override
	public void navigateBack(final PresenterEnum presenter) {
		if (presenter == null)
			getSupportFragmentManager().popBackStack();
		else
			getSupportFragmentManager().popBackStack(presenter.name(), 0);
	}
	// endregion
	
	// region IDisplaysMessages Implementation @Override
	public void displayMessage(final String error) {
		Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
	}
	// endregion
}
