package byu.codemonkeys.tickettoride.mvpcontracts.game;


import byu.codemonkeys.tickettoride.shared.model.PlayerColor;

public interface MapContract {
    interface View {
        void setRouteClaimed(int routeID, PlayerColor color);
    }

    interface Presenter {
        void claimRoute(int routeID);
    }
}
