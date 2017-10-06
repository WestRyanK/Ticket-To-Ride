package byu.codemonkeys.tickettoride.models;

import java.util.List;
import java.util.Observer;

import byu.codemonkeys.tickettoride.exceptions.NoPendingGameException;
import byu.codemonkeys.tickettoride.exceptions.LoginException;
import byu.codemonkeys.tickettoride.exceptions.RegisterException;
import byu.codemonkeys.tickettoride.exceptions.SingleGameException;
import byu.codemonkeys.tickettoride.exceptions.UnauthorizedException;
import byu.codemonkeys.tickettoride.shared.model.*;

/**
 * Created by Megan on 10/3/2017.
 */

interface IModelFacade {

    void addObserver(Observer observer);

    UserBase getUser() throws UnauthorizedException;

    void loginUser(String username, String password) throws LoginException;

    void registerUser(String username, String password) throws RegisterException;

    void logoutUser() throws UnauthorizedException;

    List<GameBase> getPendingGames() throws UnauthorizedException;

    GameBase createPendingGame(String gameName) throws UnauthorizedException;

    GameBase joinPendingGame(GameBase game) throws UnauthorizedException, SingleGameException;

    GameBase getPendingGame() throws UnauthorizedException, NoPendingGameException;

    void leavePendingGame() throws UnauthorizedException, NoPendingGameException;

    GameBase startPendingGame() throws UnauthorizedException, NoPendingGameException;

    void cancelPendingGame() throws UnauthorizedException, NoPendingGameException;

    void setPendingGames(List<GameBase> games) throws UnauthorizedException;

    Session getSession();

    void changeConnectionConfiguration(String host, int port);

}
