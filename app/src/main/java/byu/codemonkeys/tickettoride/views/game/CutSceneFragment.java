package byu.codemonkeys.tickettoride.views.game;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.mvpcontracts.game.CutSceneContract;
import byu.codemonkeys.tickettoride.mvpcontracts.game.CutScenes;
import byu.codemonkeys.tickettoride.presenters.game.CutScenePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CutSceneFragment extends Fragment implements CutSceneContract.View {
	
	private MediaPlayer cutScenePlayer;
	private VideoView videoView;
	private CutSceneContract.Presenter presenter;
	
	public CutSceneFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_cut_scene, container, false);
		this.videoView = (VideoView) view.findViewById(R.id.cutScene_videoView);
		this.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mediaPlayer) {
				presenter.cutSceneEnd();
			}
		});
		this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mediaPlayer) {
				cutScenePlayer = mediaPlayer;
				videoView.start();
			}
		});
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (!presenter.isSkipCutScene()) {
			presenter.initCutScene();
		} else {
			presenter.cutSceneEnd();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		//		if (cutScenePlayer != null) {
		//			this.cutScenePlayer.release();
		//			this.cutScenePlayer = null;
		//		}
	}
	
	public void setPresenter(CutScenePresenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void playCutScene(CutScenes cutScene) {
		int cutSceneResID;
		switch (cutScene) {
			case openingSequence:
				cutSceneResID = R.raw.opening_sequence;
				break;
			default:
				cutSceneResID = -1;
		}
		if (cutSceneResID != -1) {
			this.videoView.setVideoURI(Uri.parse("android.resource://" +
														 this.getContext().getPackageName() +
														 "/" +
														 cutSceneResID));
		}
	}
}
