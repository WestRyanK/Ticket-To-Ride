package byu.codemonkeys.tickettoride.shared.model;

import java.util.List;
import java.util.Set;

public interface IDeck {
    TrainCard drawTrainCard();

    Set<DestinationCard> drawDestinationCards();

    List<TrainCard> getRevealed();

    int getNumHidden();

    int getNumDestinationCards();
}
