package byu.codemonkeys.tickettoride.mvpcontracts.game;

/**
 * Created by Ryan on 11/11/2017.
 */

public interface CutSceneContract {
	interface View {
		void playCutScene(CutScenes cutScene);
		
	}
	
	interface Presenter {
		void cutSceneEnd();
		
		void initCutScene(CutScenes scene);
		
		boolean isSkipCutScene();
	}
}
