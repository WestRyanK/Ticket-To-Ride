package byu.codemonkeys.tickettoride.shared.commands;

import java.util.Map;

import byu.codemonkeys.tickettoride.shared.model.UserBase;

public class BeginCommandData extends CommandData {
    private Map<UserBase, Integer> numDestinations;

    public BeginCommandData(Map<UserBase, Integer> numDestinations) {
        super(CommandType.BEGIN_GAME);
        this.numDestinations = numDestinations;
    }
}
