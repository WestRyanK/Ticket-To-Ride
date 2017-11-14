package byu.codemonkeys.tickettoride.shared.commands;

public class DrawFaceUpTrainCardCommandData extends CommandData {
	private int faceUpCardIndex;
	
	public DrawFaceUpTrainCardCommandData(int faceUpCardIndex) {
		super(CommandType.DRAW_FACEUP_TRAIN_CARD);
		this.faceUpCardIndex = faceUpCardIndex;
	}

	public int getIndex() {
		return faceUpCardIndex;
	}
}
