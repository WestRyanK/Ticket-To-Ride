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
 public boolean loginUser(String username, String password) {
  return false;
 }

 @Override
 public boolean logoutUser() {
  return false;
 }

 @Override
 public boolean registerUser(String username, String password) {
  return false;
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
 public boolean joinPendingGame(PendingGame game) {
  return false;
 }

 @Override
 public boolean leavePendingGame() {
  return false;
 }

 @Override
 public Game startPendingGame(PendingGame game) {
  return null;
 }
}
