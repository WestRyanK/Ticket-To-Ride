package byu.codemonkeys.tickettoride.shared.model.map;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import byu.codemonkeys.tickettoride.shared.model.UserBase;

public class GameMap {
    transient private List<City> cities;
    transient private List<Route> routes;

    public GameMap() {
        loadMapFromResource();
    }

    public GameMap(List<City> cities, List<Route> routes) {
        this.cities = cities;
        this.routes = routes;
    }

    private void loadMapFromResource() {
        // this will load the map from a map spec file
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
        return routes.get(routeId).claim(user);
    }

    // TODO: Change all these authTokens to something the clients and server can both use.
    public int calculateLongestPathForPlayer(UserBase user) {
        return 0;
    }


}