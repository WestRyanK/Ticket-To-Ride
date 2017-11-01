package byu.codemonkeys.tickettoride.utils;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

import byu.codemonkeys.tickettoride.shared.model.IDeck;
import byu.codemonkeys.tickettoride.shared.model.cards.Deck;

public class IDeckInstanceCreator implements InstanceCreator<IDeck> {
    @Override
    public IDeck createInstance(Type type) {
        return new Deck();
    }
}
