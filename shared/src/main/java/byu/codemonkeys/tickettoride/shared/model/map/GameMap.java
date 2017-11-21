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
		GameMap map = GameMapLoader.getInstance().loadGameMapFromResources();
		init(map.cities, map.routes);
	}
	
	public static void main(String[] args) {
		GameMap map = new GameMap();
	}
	
	private void init(List<City> cities, List<Route> routes) {
		this.cities = cities;
		this.routes = routes;
		calculateParallelRoutes();
	}

	private void calculateParallelRoutes() {
		for (Route route1 : routes) {
			if (route1.isParallel())
				continue;

			for (Route route2 : routes) {
				if (route2.isParallel() || route1 == route2)
					continue;

				if (route1.containsCity(route2.getSource()) && route1.containsCity(route2.getDestination())) {
					route1.setParallelRoute(route2.getRouteId());
					route2.setParallelRoute(route1.getRouteId());
				}
			}
		}
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
		Route route = routes.get(routeId);
		if (route.claim(user)) {
			setChanged();
			notifyObservers(route);
			return true;
		}
		return false;
	}

	public Route getRoute(int routeID) {
		return routes.get(routeID);
	}

	public int calculateLongestPathForPlayer(UserBase user) {
		return 0;
	}
	
	
}
