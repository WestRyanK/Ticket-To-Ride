package byu.codemonkeys.tickettoride.views.game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.IMediaPlayer;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.game.CutScenes;
import byu.codemonkeys.tickettoride.mvpcontracts.game.Sounds;
import byu.codemonkeys.tickettoride.presenters.game.ChatHistoryPresenter;
import byu.codemonkeys.tickettoride.presenters.game.CutScenePresenter;
import byu.codemonkeys.tickettoride.presenters.game.DestinationCardsPresenter;
import byu.codemonkeys.tickettoride.presenters.game.DrawDestinationCardsPresenter;
import byu.codemonkeys.tickettoride.presenters.game.EndGamePresenter;
import byu.codemonkeys.tickettoride.presenters.game.GamePresenter;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;
import byu.codemonkeys.tickettoride.views.home.HomeActivity;

public class GameActivity extends AppCompatActivity implements INavigator, IDisplaysMessages, IMediaPlayer {
	
	
	private PresenterEnum currentView;
	private MediaPlayer mediaPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		loadMediaPlayer();
		navigate(PresenterEnum.Game, true);
	}
	
	private void loadMediaPlayer() {
		
		if (this.mediaPlayer == null) {
			this.mediaPlayer = new MediaPlayer();
			this.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					mediaPlayer.start();
				}
			});
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		loadMediaPlayer();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (this.mediaPlayer != null) {
			this.mediaPlayer.release();
			this.mediaPlayer = null;
		}
	}
	
	@Override
	public void displayMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void navigate(final PresenterEnum presenter, final boolean allowBack) {
		final GameActivity activity = this;
		Fragment fragment;
		currentView = presenter;
		switch (presenter) {
			case Game:
				GameFragment gameFragment = new GameFragment();
				gameFragment.setPresenter(new GamePresenter(gameFragment,
															activity,
															activity,
															ModelFacade.getInstance(),
															activity));
				fragment = gameFragment;
				break;
			case CutScene:
				CutSceneFragment cutSceneFragment = new CutSceneFragment();
				cutSceneFragment.setPresenter(new CutScenePresenter(cutSceneFragment,
																	activity,
																	activity,
																	ModelFacade.getInstance(),
																	activity));
				fragment = cutSceneFragment;
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
						ModelFacade.getInstance(),
						activity));
				fragment = drawDestinationCardsFragment;
				break;
			case DestinationCards:
				DestinationCardsFragment destinationCardsFragment = new DestinationCardsFragment();
				destinationCardsFragment.setPresenter(new DestinationCardsPresenter(
						destinationCardsFragment,
						activity,
						activity,
						ModelFacade.getInstance(),
						activity));
				fragment = destinationCardsFragment;
				break;
			case ChatHistory:
				ChatHistoryFragment chatHistoryFragment = new ChatHistoryFragment();
				chatHistoryFragment.setPresenter(new ChatHistoryPresenter(chatHistoryFragment,
																		  activity,
																		  activity,
																		  ModelFacade.getInstance(),
																		  activity));
				fragment = chatHistoryFragment;
				break;
			case EndGame:
				EndGameFragment endGameFragment = new EndGameFragment();
				endGameFragment.setPresenter(new EndGamePresenter(endGameFragment,
																  activity,
																  activity,
																  ModelFacade.getInstance(),
																  activity));
				fragment = endGameFragment;
				break;
			case Lobby:
				Intent intent = new Intent(GameActivity.this, HomeActivity.class);
				intent.putExtra(HomeActivity.PARAM_PRESENTER, PresenterEnum.Lobby.ordinal());
				startActivity(intent);
			
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
	
	@Override
	public void navigateBack(final PresenterEnum presenter) {
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
	
	@Override
	public void navigateBack() {
		navigateBack(null);
	}
	
	// region IMediaPlayer Implementation
	@Override
	public void playSound(Sounds sound, boolean loop) {
		if (this.mediaPlayer != null && this.mediaPlayer.isPlaying()) {
			this.mediaPlayer.stop();
			//			this.mediaPlayer.release();
			this.mediaPlayer.reset();
		}
		
		int audioResID;
		switch (sound) {
			case gameSoundtrack:
				audioResID = R.raw.game_soundtrack;
				break;
			default:
				audioResID = -1;
		}
		
		Uri sourceUri = Uri.parse("android.resource://" + this.getPackageName() + "/" + audioResID);
		try {
			this.mediaPlayer.setDataSource(this, sourceUri);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.mediaPlayer.setLooping(loop);
		this.mediaPlayer.prepareAsync();
	}
	
	@Override
	public void stopSound() {
		this.mediaPlayer.stop();
		this.mediaPlayer.release();
	}
	
	@Override
	public void playCutScene(CutScenes cutScene) {
		this.mediaPlayer.pause();
		this.navigate(PresenterEnum.CutScene, true);
	}
	// endregion
}
