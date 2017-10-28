package byu.codemonkeys.tickettoride.views.viewdata;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ryan on 10/21/2017.
 */

public class CitiesData {
	private static CitiesData instance;
	private Map<Integer, CityData> cities;
	
	private CitiesData() {
		cities = new HashMap<>();
		int index = 0;
		
		cities.put(index++, new CityData("Vancouver", "Lair of the Firebending Master", 0.332480818f, 0.372740964f, 0,0));
		cities.put(index++, new CityData("Calgary", "Western Air Temple", 0.378516624f, 0.338855422f, 0,0));
		cities.put(index++, new CityData("Seattle", "Fire Nation Capitol", 0.299232737f, 0.47439759f, 0,0));
		cities.put(index++, new CityData("Portland", "Fire Nation Harbor City", 0.304347826f, 0.519578313f, 0,0));
		cities.put(index++, new CityData("San Francisco", "The Great Gates of Azulon", 0.332480818f, 0.519578313f, 0,0));
		cities.put(index++, new CityData("Las Vegas", "Fire Fountain City", 0.393861893f, 0.504518072f, 0,0));
		cities.put(index++, new CityData("Los Angeles", "Southern Air Temple (West)", 0.383631714f, 0.62876506f, 0,0));
		cities.put(index++, new CityData("El Paso", "Aang's Iceberg", 0.445012788f, 0.715361446f, 0,0));
		cities.put(index++, new CityData("Houston", "Southern Water Tribe", 0.506393862f, 0.734186747f, 0,0));
		cities.put(index++, new CityData("Phoenix", "Southern Air Temple (Central)", 0.445012788f, 0.62123494f, 0,0));
		cities.put(index++, new CityData("Santa Fe", "Whale Tail Island", 0.480818414f, 0.587349398f, 0,0));
		cities.put(index++, new CityData("Oklahoma City", "Kyoshi Island", 0.524296675f, 0.591114458f, 0,0));
		cities.put(index++, new CityData("Kansas City", "Omashu", 0.537084399f, 0.496987952f, 0,0));
		cities.put(index++, new CityData("Little Rock", "Bei Fong Estate", 0.557544757f, 0.576054217f, 0,0));
		cities.put(index++, new CityData("Dallas", "Southern Air Temple (East)", 0.514066496f, 0.625f, 0,0));
		cities.put(index++, new CityData("Salt Lake City", "The Lion Turtle's Cliff Face", 0.409207161f, 0.414156627f, 0,0));
		cities.put(index++, new CityData("Helena", "Battlefield of 100 Year War", 0.421994885f, 0.368975904f, 0,0));
		cities.put(index++, new CityData("Winnipeg", "Northern Water Tribe", 0.485933504f, 0.278614458f, 0,0));
		cities.put(index++, new CityData("Sault St. Marie", "Northern Air Temple", 0.542199488f, 0.293674699f, 0,0));
		cities.put(index++, new CityData("Montreal", "Ba Sing Se North", 0.613810742f, 0.335090361f, 0,0));
		cities.put(index++, new CityData("Boston", "Ba Sing Se East", 0.639386189f, 0.368975904f, 0,0));
		cities.put(index++, new CityData("New York", "Ba Sing Se South", 0.618925831f, 0.399096386f, 0,0));
		cities.put(index++, new CityData("Pittsburg", "Chameleon Bay", 0.641943734f, 0.485692771f, 0,0));
		cities.put(index++, new CityData("Raleigh", "Eastern Air Temple (North)", 0.705882353f, 0.538403614f, 0,0));
		cities.put(index++, new CityData("Charleston", "Eastern Air Temple (Central)", 0.698209719f, 0.587349398f, 0,0));
		cities.put(index++, new CityData("Duluth", "Pohuai Stronghold", 0.501278772f, 0.372740964f, 0,0));
		cities.put(index++, new CityData("Omaha", "Harbor Town", 0.485933504f, 0.406626506f, 0,0));
		cities.put(index++, new CityData("Denver", "Hei Bei's Forest", 0.483375959f, 0.451807229f, 0,0));
		cities.put(index++, new CityData("Chicago", "Jet's Forest", 0.557544757f, 0.448042169f, 0,0));
		cities.put(index++, new CityData("Saint Louis", "Wan Shi Tong's Library", 0.601023018f, 0.504518072f, 0,0));
		cities.put(index++, new CityData("Nashville", "Tu Zin Ghost Town", 0.616368286f, 0.538403614f, 0,0));
		cities.put(index++, new CityData("Toronto", "Serpent's Pass", 0.578005115f, 0.399096386f, 0,0));
		cities.put(index++, new CityData("Atlanta", "Iroh's Campsite", 0.6342711f, 0.572289157f, 0,0));
		cities.put(index++, new CityData("Miami", "Eastern Air Temple (South)", 0.659846547f, 0.61746988f, 0,0));
		cities.put(index++, new CityData("New Orleans", "Southern Earth Kingom Islands", 0.578005115f, 0.662650602f, 0,0));
		cities.put(index++, new CityData("Washington", "Earth Kingdom Penninsula", 0.695652174f, 0.46310241f, 0,0));
	}
	
	public static CitiesData getInstance() {
		if (instance == null)
			instance = new CitiesData();
		return instance;
	}
	
	public CityData getCity(int id) {
		return cities.get(id);
	}
	
	public class CityData {
		private final String cityName;
		
		public String getOriginalCityName() {
			return originalCityName;
		}
		
		private final String originalCityName;
		private final float cityDestinationCardXRatio;
		private final float cityDestinationCardYRatio;
		private final float cityMapXRatio;
		private final float cityMapYRatio;
		
		public String getCityName() {
			return cityName;
		}
		
		public float getCityDestinationCardXRatio() {
			return cityDestinationCardXRatio;
		}
		
		public float getCityDestinationCardYRatio() {
			return cityDestinationCardYRatio;
		}
		
		public float getCityMapXRatio() {
			return cityMapXRatio;
		}
		
		public float getCityMapYRatio() {
			return cityMapYRatio;
		}
		
		public CityData(String originalCityName,
						String cityName,
						float cityDestinationCardXRatio,
						float cityDestinationCardYRatio,
						float cityMapXRatio,
						float cityMapYRatio) {
			this.originalCityName = originalCityName;
			this.cityName = cityName;
			this.cityDestinationCardXRatio = cityDestinationCardXRatio;
			this.cityDestinationCardYRatio = cityDestinationCardYRatio;
			this.cityMapXRatio = cityMapXRatio;
			this.cityMapYRatio = cityMapYRatio;
		}
	}
}
