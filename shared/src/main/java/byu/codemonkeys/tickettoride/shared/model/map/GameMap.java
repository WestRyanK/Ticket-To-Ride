package byu.codemonkeys.tickettoride.shared.model.map;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameMap {
    List<City> cities;
    Set<Route> routes;

    public GameMap() {
        loadMapFromResource();
    }

    public GameMap(List<City> cities, Set<Route> routes) {
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

    public Set<Route> getAllRoutes() {
        return routes;
    }
}
