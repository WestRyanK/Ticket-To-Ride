package byu.codemonkeys.tickettoride.shared.model.map;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import byu.codemonkeys.tickettoride.shared.model.UserBase;

public class GameMap extends Observable {
	transient private List<City> cities;
	transient private List<Route> routes;
	
	public Map<Integer, UserBase> getClaimedRoutes() {
		return claimedRoutes;
	}
	
	private Map<Integer, UserBase> claimedRoutes;
	public static final String ROUTE_UPDATE = "RouteUpdate";
	
	public GameMap() {
		GameMap map = GameMapLoader.getInstance().loadGameMapFromResources();
		this.claimedRoutes = new HashMap<>();
		init(map.cities, map.routes);
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
				
				if (route1.containsCity(route2.getSource()) &&
						route1.containsCity(route2.getDestination())) {
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
			this.claimedRoutes.put(routeId, user);
			setChanged();
			notifyObservers(route);
			return true;
		}
		return false;
	}
	
	public Route getRoute(int routeID) {
		return routes.get(routeID);
	}
	
	public City getCity(int cityID) {
		return cities.get(cityID);
	}
	
	public int calculateLongestPathForPlayer(UserBase user) {
		return 0;
	}
	
	
	public void updateClaimedRoutes() {
		for (Map.Entry<Integer, UserBase> entry : claimedRoutes.entrySet()) {
			this.claimRoute(entry.getKey(), entry.getValue());
		}
	}
}
