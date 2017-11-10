package byu.codemonkeys.tickettoride.shared.commands;

/**
 * Created by Ryan on 11/10/2017.
 */

public class DrawFaceUpTrainCardCommandData extends CommandData {
	private int faceUpCardIndex;
	
	public DrawFaceUpTrainCardCommandData(int faceUpCardIndex) {
		super(CommandType.DRAW_FACEUP_TRAIN_CARD);
		this.faceUpCardIndex = faceUpCardIndex;
	}
}
