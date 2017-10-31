package byu.codemonkeys.tickettoride.models;

import android.graphics.drawable.Drawable;

import byu.codemonkeys.tickettoride.shared.model.CardType;

/**
 * Created by Ryan on 10/17/2017.
 */

public class TrainCard {
	private CardType cardColor;
	
	public CardType getCardColor() {
		return cardColor;
	}
	
	public TrainCard(CardType cardColor) {
		this.cardColor = cardColor;
	}
}
