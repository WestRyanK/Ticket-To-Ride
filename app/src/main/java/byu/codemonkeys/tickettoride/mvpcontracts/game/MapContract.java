package byu.codemonkeys.tickettoride.mvpcontracts.game;


import byu.codemonkeys.tickettoride.views.widgets.MapEdgeWidget;

public interface MapContract {
    interface View {

    }

    interface Presenter {
        void claimRoute(MapEdgeWidget pointBubble);
    }
}
