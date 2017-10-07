package byu.codemonkeys.tickettoride;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import byu.codemonkeys.tickettoride.async.AndroidTask;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.presenters.ConnectionSettingsPresenter;
import byu.codemonkeys.tickettoride.presenters.CreateGamePresenter;
import byu.codemonkeys.tickettoride.presenters.LobbyPresenter;
import byu.codemonkeys.tickettoride.presenters.LoginPresenter;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;
import byu.codemonkeys.tickettoride.presenters.RegisterPresenter;
import byu.codemonkeys.tickettoride.presenters.WaitingRoomPresenter;
import byu.codemonkeys.tickettoride.views.ConnectionSettingsFragment;
import byu.codemonkeys.tickettoride.views.CreateGameFragment;
import byu.codemonkeys.tickettoride.views.LobbyFragment;
import byu.codemonkeys.tickettoride.views.LoginFragment;
import byu.codemonkeys.tickettoride.views.RegisterFragment;
import byu.codemonkeys.tickettoride.views.WaitingRoomFragment;

public class MainActivity extends AppCompatActivity implements INavigator, IDisplaysMessages {
	
	private FrameLayout fragmentContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.fragmentContainer = (FrameLayout) this.findViewById(R.id.main_fragmentContainer);
		
		((ModelFacade) ModelFacade.getInstance()).setAsyncTask(new AndroidTask());
		Navigate(PresenterEnum.Login, false);
	}
	
	// region INavigator Implementation
	@Override
	public void Navigate(PresenterEnum presenter, boolean allowBack) {
		Fragment fragment;
		switch (presenter) {
			case Login:
				LoginFragment loginFragment = LoginFragment.newInstance();
				loginFragment.setPresenter(new LoginPresenter(loginFragment,
															  this,
															  this,
															  ModelFacade.getInstance()));
				fragment = loginFragment;
				break;
			case Register:
				RegisterFragment registerFragment = RegisterFragment.newInstance();
				registerFragment.setPresenter(new RegisterPresenter(registerFragment,
																	this,
																	this,
																	ModelFacade.getInstance()));
				fragment = registerFragment;
				break;
			case ConnectionSettings:
				ConnectionSettingsFragment connectionSettingsFragment = ConnectionSettingsFragment.newInstance();
				connectionSettingsFragment.setPresenter(new ConnectionSettingsPresenter(
						connectionSettingsFragment,
						this,
						this,
						ModelFacade.getInstance()));
				fragment = connectionSettingsFragment;
				break;
			case CreateGame:
				CreateGameFragment createGameFragment = CreateGameFragment.newInstance();
				createGameFragment.setPresenter(new CreateGamePresenter(createGameFragment,
																		this,
																		this,
																		ModelFacade.getInstance()));
				fragment = createGameFragment;
				break;
			case Lobby:
				LobbyFragment lobbyFragment = LobbyFragment.newInstance();
				lobbyFragment.setPresenter(new LobbyPresenter(lobbyFragment,
															  this,
															  this,
															  ModelFacade.getInstance()));
				fragment = lobbyFragment;
				break;
			case WaitingRoom:
				WaitingRoomFragment waitingRoomFragment = WaitingRoomFragment.newInstance();
				waitingRoomFragment.setPresenter(new WaitingRoomPresenter(waitingRoomFragment,
																		  this,
																		  this,
																		  ModelFacade.getInstance()));
				fragment = waitingRoomFragment;
				break;
			default:
				fragment = null;
		}
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
																	 .replace(R.id.main_fragmentContainer,
																			  fragment);
		if (allowBack)
			transaction.addToBackStack(null);
		transaction.commit();
		
	}
	
	@Override
	public void NavigateBack() {
		getSupportFragmentManager().popBackStack();
	}
	// endregion
	
	// region IDisplaysMessages Implementation
	@Override
	public void displayMessage(String error) {
		Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
	}
	// endregion
}
