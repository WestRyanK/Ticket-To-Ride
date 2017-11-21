package byu.codemonkeys.tickettoride.presenters.game;

import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.IMediaPlayer;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.mvpcontracts.game.MapContract;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.PlayerColor;
import byu.codemonkeys.tickettoride.shared.model.turns.Turn;
import byu.codemonkeys.tickettoride.shared.results.Result;
import byu.codemonkeys.tickettoride.views.widgets.MapEdgeWidget;


public class MapPresenter extends PresenterBase implements MapContract.Presenter, Observer {
	private MapContract.View view;
	
	public MapPresenter(MapContract.View view,
						INavigator navigator,
						IDisplaysMessages messageDisplayer,
						IModelFacade modelFacade,
						IMediaPlayer mediaPlayer) {
		super(navigator, messageDisplayer, modelFacade, mediaPlayer);
		this.view = view;
		modelFacade.addObserver(this);
	}
	
	@Override
	public void claimRoute(int routeID) {
		
		if (ModelRoot.getInstance().getGame().getTurn().canClaimRoute()) {
			ICallback callback = new ICallback() {
				@Override
				public void callback(Result result) {
					if (!result.isSuccessful()) {
						messageDisplayer.displayMessage(result.getErrorMessage());
					}
				}
			};
			
//			modelFacade.claimRoute(routeID, callback);
		} else {
			messageDisplayer.displayMessage("Can't claim a route right now!");
		}
		//        if (pointBubble.getClaimedColor().equals(PlayerColor.None)) {
		//            Turn turn = ModelRoot.getInstance().getGame().getTurn();
		//            Player self = ModelRoot.getInstance().getGame().getPlayers().get(turn.getPlayerIndex());
		//            pointBubble.setClaimedColor(self.getColor());
		//            self.setScore(self.getScore() + pointBubble.getLengthValue());
		//            self.setNumTrains(self.getNumTrains() - pointBubble.getLengthValue());
		//            ModelRoot.getInstance().getGame().nextTurn();
		//            messageDisplayer.displayMessage("Claimed Route! Next Players Turn");
		//        } else {
		//            messageDisplayer.displayMessage("Route Already Claimed!");
		//        }
	}
	
	@Override
	public void update(Observable observable, Object o) {
		
	}
}
