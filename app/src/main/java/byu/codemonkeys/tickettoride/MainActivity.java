package byu.codemonkeys.tickettoride;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
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

public class MainActivity extends AppCompatActivity implements INavigator {
	
	private FrameLayout fragmentContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.fragmentContainer = (FrameLayout) this.findViewById(R.id.main_fragmentContainer);
		
		Navigate(PresenterEnum.Login, false);
	}
	
	@Override
	public void Navigate(PresenterEnum presenter, boolean allowBack) {
		Fragment fragment;
		switch (presenter) {
			case Login:
				LoginFragment loginFragment = LoginFragment.newInstance();
				loginFragment.setPresenter(new LoginPresenter(loginFragment, this));
				fragment = loginFragment;
				break;
			case Register:
				RegisterFragment registerFragment = RegisterFragment.newInstance();
				registerFragment.setPresenter(new RegisterPresenter(registerFragment, this));
				fragment = registerFragment;
				break;
			case ConnectionSettings:
				ConnectionSettingsFragment connectionSettingsFragment = ConnectionSettingsFragment.newInstance();
				connectionSettingsFragment.setPresenter(new ConnectionSettingsPresenter(
						connectionSettingsFragment,
						this));
				fragment = connectionSettingsFragment;
				break;
			case CreateGame:
				CreateGameFragment createGameFragment = CreateGameFragment.newInstance();
				createGameFragment.setPresenter(new CreateGamePresenter(createGameFragment, this));
				fragment = createGameFragment;
				break;
			case Lobby:
				LobbyFragment lobbyFragment = LobbyFragment.newInstance();
				lobbyFragment.setPresenter(new LobbyPresenter(lobbyFragment, this));
				fragment = lobbyFragment;
				break;
			case WaitingRoom:
				WaitingRoomFragment waitingRoomFragment = WaitingRoomFragment.newInstance();
				waitingRoomFragment.setPresenter(new WaitingRoomPresenter(waitingRoomFragment,
																		  this));
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
}
