package byu.codemonkeys.tickettoride.shared.model.map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import byu.codemonkeys.tickettoride.shared.model.LoaderBase;
import byu.codemonkeys.tickettoride.shared.model.cards.CardType;

/**
 * Created by Ryan on 10/30/2017.
 */


public class GameMapLoader extends LoaderBase {
	
	private static GameMapLoader instance;
	
	public static GameMapLoader getInstance() {
		if (instance == null)
			instance = new GameMapLoader();
		return instance;
	}
	
	private GameMapLoader() {
		
	}
	
	
	public GameMap loadGameMapFromResources() {
		List<City> cities = loadCitiesFromResource();
		List<Route> routes = loadRoutesFromResource(cities);
		GameMap map = new GameMap(cities, routes);
		return map;
	}
	
	private List<City> loadCitiesFromResource() {
		List<CityData> citiesData = getDataFromResource("cities.json",
														new TypeToken<ArrayList<CityData>>() {
														}.getType());
		List<City> cities = new ArrayList<>();
		
		if (citiesData != null) {
			for (CityData cityData : citiesData) {
				City city = new City(cityData.ID, cityData.City);
				cities.add(city);
			}
		}
		
		return cities;
	}
	
	private List<Route> loadRoutesFromResource(List<City> cities) {
		List<RouteData> routesData = getDataFromResource("routes.json",
														 new TypeToken<ArrayList<RouteData>>() {
														 }.getType());
		List<Route> routes = new ArrayList<>();
		
		
		if (routesData != null) {
			for (RouteData routeData : routesData) {
				City cityA = getCityWithID(cities, routeData.DestinationA);
				City cityB = getCityWithID(cities, routeData.DestinationB);
				assert cityA != null;
				assert cityB != null;
				
				Route route = new Route(routeData.ID,
										routeData.Color,
										cityA,
										cityB,
										routeData.length);
				routes.add(route);
			}
		}
		
		return routes;
	}
	
	private City getCityWithID(List<City> cities, int id) {
		City city = cities.get(id);
		if (city.getID() == id)
			return city;
		else {
			for (City c : cities) {
				if (c.getID() == id)
					return c;
			}
		}
		return null;
	}
	
	private class CityData {
		public int ID;
		public String City;
	}
	
	private class RouteData {
		public int ID;
		public CardType Color;
		public int DestinationA;
		public int DestinationB;
		public double ratioX;
		public double ratioY;
		public int length;
	}
}
