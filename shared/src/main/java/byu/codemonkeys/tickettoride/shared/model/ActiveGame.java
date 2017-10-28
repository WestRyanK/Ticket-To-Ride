package byu.codemonkeys.tickettoride.shared.model;

import java.util.List;

/**
 * We need to decide whether ActiveGame should extend GameBase. As it stands, ActiveGame doesn't
 * need any of the fields of GameBase except gameID. What we might want to do is move all the other
 * fields of GameBase to a shared PendingGame class.
 */
public class ActiveGame {
//    private GameMap map;
    private int turn;
    private List <Player> players;
}
