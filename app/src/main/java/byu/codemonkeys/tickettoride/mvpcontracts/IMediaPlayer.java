package byu.codemonkeys.tickettoride.mvpcontracts;

import byu.codemonkeys.tickettoride.mvpcontracts.game.CutScenes;
import byu.codemonkeys.tickettoride.mvpcontracts.game.Sounds;

/**
 * Created by Ryan on 11/13/2017.
 */

public interface IMediaPlayer {
	void playSound(Sounds sound, boolean loop);
	
	void playSound();
	
	void stopSound();
	
	void pauseSound();
	
	boolean isSoundPlaying();
	
	void playCutScene(CutScenes cutScene);
}
