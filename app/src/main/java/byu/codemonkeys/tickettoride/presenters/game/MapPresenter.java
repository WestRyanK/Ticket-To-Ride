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
import byu.codemonkeys.tickettoride.shared.model.Self;
import byu.codemonkeys.tickettoride.shared.model.map.Route;
import byu.codemonkeys.tickettoride.shared.model.turns.Turn;
import byu.codemonkeys.tickettoride.shared.results.ClaimRouteResult;
import byu.codemonkeys.tickettoride.shared.results.Result;
import byu.codemonkeys.tickettoride.views.widgets.MapEdgeWidget;

import static byu.codemonkeys.tickettoride.shared.model.map.GameMap.ROUTE_UPDATE;


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

					if (result instanceof ClaimRouteResult) {
						ClaimRouteResult claimRouteResult = (ClaimRouteResult) result;
						Self self = ModelRoot.getInstance().getGame().getSelf();
						self.setNumTrains(self.getNumTrains() - claimRouteResult.getNumTrainsToRemove());
						self.discardTrainCards(claimRouteResult.getCardsToRemove());
					}
				}
			};
			
			modelFacade.claimRouteAsync(routeID, callback);
		} else {
			messageDisplayer.displayMessage("Can't claim a route right now!");
		}
	}
	
	@Override
	public void update(Observable observable, Object o) {
		if (o instanceof Route) {
			Route route = (Route) o;
			Player owner = ModelRoot.getInstance().getGame().getPlayer(route.getOwner().getUsername());
			view.setRouteClaimed(route.getRouteId(), owner.getColor());
		}
	}
}
