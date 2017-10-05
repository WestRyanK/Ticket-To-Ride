package byu.codemonkeys.tickettoride.models;

import java.util.ArrayList;

/**
 * Created by Megan on 10/3/2017.
 */

 public class ModelRoot implements ModelFacade{

 @Override
 public User getUser() {
  return null;
 }

 @Override
 public void loginUser(String username, String password) {

 }

 @Override
 public void logoutUser() {

 }

 @Override
 public void registerUser(String username, String password) {

 }

 @Override
 public ClientSession getSession() {
  return null;
 }

 @Override
 public void setSession(ClientSession session) {

 }

 @Override
 public ArrayList<PendingGame> getPendingGames() {
  return null;
 }

 @Override
 public PendingGame createPendingGame(PendingGame game) {
  return null;
 }

 @Override
 public PendingGame getPendingGame() {
        return null;
    }

 @Override
 public PendingGame joinPendingGame(PendingGame game) {
  return null;
 }

 @Override
 public void leavePendingGame() { }

 @Override
 public Game startPendingGame(PendingGame game) {
  return null;
 }
}
