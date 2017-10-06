package byu.codemonkeys.tickettoride.models;

import java.util.List;
import java.util.Observer;

import byu.codemonkeys.tickettoride.exceptions.SingleGameException;
import byu.codemonkeys.tickettoride.exceptions.UnauthorizedException;
import byu.codemonkeys.tickettoride.networking.ClientCommunicator;
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
//TODO(compy-386): clear models on logout via client and logout from server
//TODO(compy-386): throw proper errors for invalid input on login and register, also invalid authorization
public class ModelFacade implements IModelFacade {
    private static IModelFacade instance;
    private IServer serverProxy = ServerProxy.getInstance();
    private ModelRoot models = ModelRoot.getInstance();

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
    public void loginUser(String username, String password) throws LoginException {
        LoginResult result = serverProxy.login(username, password);
        if(result.getErrorMessage() == null) {
            models.setSession(result.getUserSession());
            models.setUser(new UserBase(username));
            //TODO(compy-386):start poller here since they are logged in
        } else throw new LoginException(result.getErrorMessage());
    }

    @Override
    public void logoutUser() throws UnauthorizedException {
        Result result = serverProxy.logout(models.getSession());
        if(result.getErrorMessage() == null) {
            //this.instance = new ModelRoot();
            //TODO(compy-386): clear the existing data
        } else throw new UnauthorizedException(result.getErrorMessage());
    }

    @Override
    public void registerUser(String username, String password) throws RegisterException {
        LoginResult result = serverProxy.register(username, password);
        if(result.getErrorMessage() == null){
            models.setSession(result.getUserSession());
            models.setUser(new UserBase(username));
            //TODO(compy-386): start poller here
        } else throw new RegisterException(result.getErrorMessage());
    }

    @Override
    public List<GameBase> getPendingGames() throws UnauthorizedException {
        if(models.getSession() == null){
            throw new UnauthorizedException("No user logged in");
        } else {
            return models.getPendingGames();
        }
        //TODO(compy-386): fix to work with poller
    }

    @Override
    public GameBase createPendingGame(String gameName) throws UnauthorizedException {
        PendingGameResult result = serverProxy.createGame(models.getSession(), gameName);
        if(result.getErrorMessage() == null) {
            models.setPendingGame(result.getGame());
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
            PendingGameResult result = serverProxy.joinPendingGame(models.getSession(), game.getName());
            if (result.getErrorMessage() == null) {
                 models.setPendingGame(result.getGame());
                return models.getPendingGame();
            } else throw new UnauthorizedException(result.getErrorMessage());
        } else throw new SingleGameException();
    }

    @Override
    public void leavePendingGame() throws UnauthorizedException, NoPendingGameException{
        if(models.getPendingGame() == null) {
            throw new NoPendingGameException();
        } else {
            PendingGamesResult result = serverProxy.leavePendingGame(models.getSession(), models.getPendingGame().getName());
            if(result.getErrorMessage() == null) models.setPendingGame(null);
            else throw new UnauthorizedException(result.getErrorMessage());
        }
    }

    @Override
    public GameBase startPendingGame() throws UnauthorizedException, NoPendingGameException{
        if(models.getPendingGame() == null){
            throw new NoPendingGameException();
        } else {
            StartGameResult result = serverProxy.startGame(models.getSession(), models.getPendingGame().getName());
            if(result.getErrorMessage()==null) {
                models.setGame(result.getGame());
                return models.getGame();
            } else throw new UnauthorizedException(result.getErrorMessage());
        }
    }

    @Override
    public void cancelPendingGame() throws UnauthorizedException, NoPendingGameException{
        if(models.getPendingGame() == null) {
            throw new NoPendingGameException();
        } else{
            PendingGamesResult result = serverProxy.cancelGame(models.getSession(), models.getPendingGame().getName());
            if(result.getErrorMessage() == null) {
                models.setPendingGame(null);
                models.setPendingGames(result.getPendingGames());
            }
        }
    }

    @Override
    public void setPendingGames(List<GameBase> games){
        models.setPendingGames(games);
    }

    @Override
    public Session getSession(){
        return models.getSession();
    }


    //TODO(compy-386): Add error checking for invalid host and port inputs?
    @Override
    public void changeConnectionConfiguration(String host, int port){
        ClientCommunicator.getInstance().changeConfiguration(host, port);
    }
}
