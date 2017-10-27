package byu.codemonkeys.tickettoride.shared.model.map;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameMap {
    private List<City> cities;
    private List<Route> routes;

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

    public boolean claimRoute(int routeId, String authToken) {
        return routes.get(routeId).claim(authToken);
    }

    // TODO: Change all these authTokens to something the clients and server can both use.
    public int calculateLongestPathForPlayer(String authToken) {
        return 0;
    }


}
