package byu.codemonkeys.tickettoride.presenters.game;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.game.CutScenes;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.mvpcontracts.game.CutSceneContract;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;

/**
 * Created by Ryan on 11/11/2017.
 */

public class CutScenePresenter extends PresenterBase implements CutSceneContract.Presenter {
	
	private CutSceneContract.View view;
	
	public CutScenePresenter(CutSceneContract.View view,
							 INavigator navigator,
							 IDisplaysMessages messageDisplayer,
							 IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
	}
	
	@Override
	public void cutSceneEnd() {
		this.navigator.navigate(PresenterEnum.Game, true);
//		this.navigator.navigateBack();
	}
	
	@Override
	public void initCutScene() {
		this.view.playCutScene(CutScenes.openingSequence);
	}
	
	@Override
	public boolean isSkipCutScene() {
		return false;
	}
}
