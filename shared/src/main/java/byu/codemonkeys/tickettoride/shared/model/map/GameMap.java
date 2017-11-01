package byu.codemonkeys.tickettoride.shared.model.map;


import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import byu.codemonkeys.tickettoride.shared.model.UserBase;

public class GameMap extends Observable {
	transient private List<City> cities;
	transient private List<Route> routes;
	public static final String ROUTE_UPDATE = "RouteUpdate";
	
	public GameMap() {
		//GameMap map = GameMapLoader.getInstance().loadGameMapFromResources();
		//init(map.cities, map.routes);
	}
	
	public static void main(String[] args) {
		GameMap map = new GameMap();
	}
	
	private void init(List<City> cities, List<Route> routes) {
		this.cities = cities;
		this.routes = routes;
	}
	
	public GameMap(List<City> cities, List<Route> routes) {
		init(cities, routes);
	}
	
	public Set<Route> getRoutesForCity(City city) {
		Set<Route> subRoutes = new HashSet<>();
		for (Route route : routes) {
			if (route.containsCity(city))
				subRoutes.add(route);
		}
		return subRoutes;
	}
	
	public List<Route> getAllRoutes() {
		return routes;
	}
	
	public boolean claimRoute(int routeId, UserBase user) {
		setChanged();
		notifyObservers(ROUTE_UPDATE);
		return routes.get(routeId).claim(user);
	}
	
	// TODO: Change all these authTokens to something the clients and server can both use.
	public int calculateLongestPathForPlayer(UserBase user) {
		return 0;
	}
	
	
}
