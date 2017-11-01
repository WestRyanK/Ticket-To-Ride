package byu.codemonkeys.tickettoride.shared.model.cards;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import byu.codemonkeys.tickettoride.shared.model.LoaderBase;
import byu.codemonkeys.tickettoride.shared.model.map.City;
import byu.codemonkeys.tickettoride.shared.model.map.Route;

/**
 * Created by Ryan on 10/30/2017.
 */


public class DestinationCardLoader extends LoaderBase {
	
	private static DestinationCardLoader instance;
	
	public static DestinationCardLoader getInstance() {
		if (instance == null)
			instance = new DestinationCardLoader();
		return instance;
	}
	
	private DestinationCardLoader() {
		
	}
	
	public List<DestinationCard> loadDestinationCardsFromResources() {
		List<DestinationCardData> cardsData = getDataFromResource("destination_cards.json",
																  new TypeToken<ArrayList<DestinationCardData>>() {
																  }.getType());
		List<DestinationCard> cards = new ArrayList<>();
		if (cardsData != null) {
			for (DestinationCardData cardData : cardsData) {
				DestinationCard card = new DestinationCard(cardData.ID,
														   cardData.DestinationA,
														   cardData.DestinationB,
														   cardData.PointValue);
				cards.add(card);
			}
		}
		
		return cards;
	}
	
	private class DestinationCardData {
		public int ID;
		public int DestinationA;
		public int DestinationB;
		public int PointValue;
	}
}
