package byu.codemonkeys.tickettoride.shared.model.map;


import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameMap {
    List<City> cities;
    Map<City, Set<Route>> routes;

    public GameMap() {
        loadMapFromResource();
    }

    public GameMap(List<City> cities, Map<City, Set<Route>> routes) {
        this.cities = cities;
        this.routes = routes;
    }

    private void loadMapFromResource() {
        // this will load the map from a map spec file
    }

    public Set<Route> getRoutes(City city) {
        return routes.get(city);
    }
}
