package byu.codemonkeys.tickettoride.mvpcontracts.game;

import java.util.Map;

import byu.codemonkeys.tickettoride.shared.model.cards.CardType;

/**
 * Created by Ryan on 10/30/2017.
 */

public interface GameContract {
	interface View {

	}
	
	interface Presenter {
		void startPolling();
		
		void stopPolling();
	}
}
