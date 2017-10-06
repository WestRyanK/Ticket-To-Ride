package byu.codemonkeys.tickettoride.models;

import java.util.List;
import java.util.Observer;

import byu.codemonkeys.tickettoride.exceptions.SingleGameException;
import byu.codemonkeys.tickettoride.exceptions.UnauthorizedException;
import byu.codemonkeys.tickettoride.networking.ClientCommunicator;
import byu.codemonkeys.tickettoride.networking.PendingGamesPoller;
import byu.codemonkeys.tickettoride.networking.Poller;
import byu.codemonkeys.tickettoride.networking.ServerProxy;
import byu.codemonkeys.tickettoride.shared.IServer;
import byu.codemonkeys.tickettoride.shared.results.*;
import byu.codemonkeys.tickettoride.shared.model.Session;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.shared.model.UserBase;

import byu.codemonkeys.tickettoride.exceptions.LoginException;
import byu.codemonkeys.tickettoride.exceptions.RegisterException;
import byu.codemonkeys.tickettoride.exceptions.NoPendingGameException;

/**
 * Created by Megan on 10/3/2017.
 */
//TODO(compy-386): implement poller for game starting, get API endpoint for it
public class ModelFacade implements IModelFacade {
    private static IModelFacade instance;
    private IServer serverProxy = ServerProxy.getInstance();
    private ClientCommunicator communicator = ClientCommunicator.getInstance();
    private ModelRoot models = ModelRoot.getInstance();
    private PendingGamesPoller pendingGamesPoller = PendingGamesPoller.getInstance();

    private ModelFacade(){}

    public static IModelFacade getInstance() {
        if(instance == null) {
            instance = new ModelFacade();
        }
        return instance;
    }

    @Override
    public void addObserver(Observer observer) {
        models.addObserver(observer);
    }

    @Override
    public UserBase getUser() {
        return ModelRoot.getInstance().getUser();
    }

    @Override
    public void login(String username, String password) throws LoginException {
        LoginResult result = serverProxy.login(username, password);
        if(result.getErrorMessage() == null) {
            models.setSession(result.getUserSession());
            models.setUser(new UserBase(username));
            start(pendingGamesPoller);
        } else throw new LoginException(result.getErrorMessage());
    }

    @Override
    public void logout() throws UnauthorizedException {
        Result result = serverProxy.logout(models.getSession().getAuthToken());
        if(result.getErrorMessage() == null) {
            //clear the models since they are logging out
            stop(pendingGamesPoller);
            models.getInstance().clear();
        } else throw new UnauthorizedException(result.getErrorMessage());
    }

    @Override
    public void register(String username, String password) throws RegisterException {
        LoginResult result = serverProxy.register(username, password);
        if(result.getErrorMessage() == null){
            models.setSession(result.getUserSession());
            models.setUser(new UserBase(username));
            start(pendingGamesPoller);
        } else throw new RegisterException(result.getErrorMessage());
    }

    @Override
    public List<GameBase> getPendingGames() throws UnauthorizedException {
        if(models.getSession() == null){
            throw new UnauthorizedException("No user logged in");
        } else {
            return models.getPendingGames();
        }
    }

    @Override
    public GameBase createGame(String gameName) throws UnauthorizedException {
        PendingGameResult result = serverProxy.createGame(models.getSession().getAuthToken(), gameName);
        if(result.getErrorMessage() == null) {
            models.setPendingGame(result.getGame());
            stop(pendingGamesPoller);
            //TODO(compy-386): Poll for game started, waiting so no longer have to get all pending games
            return models.getPendingGame();
        } else throw new UnauthorizedException(result.getErrorMessage());
    }

    @Override
    public GameBase getPendingGame() throws UnauthorizedException, NoPendingGameException {
        if(models.getSession() == null) {throw new UnauthorizedException("No user logged in");}
        else if(models.getPendingGame() == null) {throw new NoPendingGameException();}
        else {return models.getPendingGame();}
    }

    @Override
    public GameBase joinPendingGame(GameBase game) throws UnauthorizedException, SingleGameException{
        if(models.getPendingGame() == null) {
            PendingGameResult result = serverProxy.joinPendingGame(models.getSession().getAuthToken(), game.getID());
            if (result.getErrorMessage() == null) {
                 models.setPendingGame(result.getGame());
                start(pendingGamesPoller);
                //TODO(compy-386): Poll for game started
                return models.getPendingGame();
            } else throw new UnauthorizedException(result.getErrorMessage());
        } else throw new SingleGameException();
    }

    @Override
    public void leavePendingGame() throws UnauthorizedException, NoPendingGameException{
        if(models.getPendingGame() == null) {
            throw new NoPendingGameException();
        } else {
            PendingGamesResult result = serverProxy.leavePendingGame(models.getSession().getAuthToken(), models.getPendingGame().getID());
            if(result.getErrorMessage() == null) {
                models.setPendingGame(null);
                start(pendingGamesPoller);
            }
            else throw new UnauthorizedException(result.getErrorMessage());
        }
    }

    @Override
    public GameBase startGame() throws UnauthorizedException, NoPendingGameException{
        if(models.getPendingGame() == null){
            throw new NoPendingGameException();
        } else {
            StartGameResult result = serverProxy.startGame(models.getSession().getAuthToken(), models.getPendingGame().getID());
            if(result.getErrorMessage()==null) {
                models.setGame(result.getGame());
                return models.getGame();
            } else throw new UnauthorizedException(result.getErrorMessage());
        }
    }

    @Override
    public void cancelGame() throws UnauthorizedException, NoPendingGameException{
        if(models.getPendingGame() == null) {
            throw new NoPendingGameException();
        } else{
            PendingGamesResult result = serverProxy.cancelGame(models.getSession().getAuthToken(), models.getPendingGame().getID());
            if(result.getErrorMessage() == null) {
                models.setPendingGame(null);
                models.setPendingGames(result.getGames());
                start(pendingGamesPoller);
            }
        }
    }

    @Override
    public Session getSession(){
        return models.getSession();
    }


    //TODO(compy-386): Add error checking for invalid host and port inputs?
    @Override
    public void changeConnectionConfiguration(String host, int port){
        communicator.changeConfiguration(host, port);
    }

    private void start(Poller poller) {
        poller.startPolling();
    }

    private void stop(Poller poller) {
        poller.stopPolling();
    }
}
