package byu.codemonkeys.tickettoride.views.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.shared.model.PlayerColor;

public class PlayerStatsWidget extends RelativeLayout {
	
	private static final String TAG = "PLAYER_STATS";
	private ImageView imageViewColor;
	private TextView textViewName;
	private TextView textViewScore;
	private TextView textViewTrains;
	private TextView textViewTrainCards;
	private TextView textViewDestinationCards;
	private ImageView imageViewScore;
	private ImageView imageViewTrains;
	private ImageView imageViewTrainCards;
	private ImageView imageViewDestinationCards;
	
	// region Public Properties
	// region PlayerColor Property
	private PlayerColor playerColor;
	
	public PlayerColor getPlayerColor() {
		return playerColor;
	}
	
	public void setPlayerColor(PlayerColor playerColor) {
		this.playerColor = playerColor;
		int drawableID = -1;
		switch (this.playerColor) {
			case Red:
				drawableID = R.drawable.stroke_red;
				break;
			case Green:
				drawableID = R.drawable.stroke_green;
				break;
			case Blue:
				drawableID = R.drawable.stroke_blue;
				break;
			case Black:
				drawableID = R.drawable.stroke_black;
				break;
			case Yellow:
				drawableID = R.drawable.stroke_yellow;
				break;
		}
		Drawable drawable = null;
		if (drawableID != -1) {
			drawable = getResources().getDrawable(drawableID);
		}
		this.imageViewColor.setImageDrawable(drawable);
	}
	
	// endregion
	
	// region PlayerName Property
	private String playerName;
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
		this.textViewName.setText(String.valueOf(playerName));
	}
	// endregion
	
	// region PlayerScore Property
	private int playerScore;
	
	public int getPlayerScore() {
		return playerScore;
	}
	
	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
		this.textViewScore.setText(String.valueOf(playerScore));
	}
	// endregion
	
	// region PlayerTrains Property
	private int playerTrains;
	
	public int getPlayerTrains() {
		return playerTrains;
	}
	
	public void setPlayerTrains(int playerTrains) {
		this.playerTrains = playerTrains;
		this.textViewTrains.setText(String.valueOf(playerTrains));
	}
	// endregion
	
	// region PlayerTrainCards Property
	private int playerTrainCards;
	
	public int getPlayerTrainCards() {
		return playerTrainCards;
	}
	
	public void setPlayerTrainCards(int playerTrainCards) {
		this.playerTrainCards = playerTrainCards;
		this.textViewTrainCards.setText(String.valueOf(playerTrainCards));
	}
	// endregion
	
	// region PlayerDestinationCards Property
	private int playerDestinationCards;
	
	public int getPlayerDestinationCards() {
		return playerDestinationCards;
	}
	
	public void setPlayerDestinationCards(int playerDestinationCards) {
		this.playerDestinationCards = playerDestinationCards;
		this.textViewDestinationCards.setText(String.valueOf(playerDestinationCards));
	}
	// endregion
	
	// region IsPlayersTurn Property
	private boolean isPlayersTurn;
	
	public boolean isPlayersTurn() {
		return isPlayersTurn;
	}
	
	public void setPlayersTurn(boolean playersTurn) {
		isPlayersTurn = playersTurn;
		int color = -1;
		if (this.isPlayersTurn) {
			color = getResources().getColor(R.color.colorAccent);
			imageViewScore.setImageDrawable(getResources().getDrawable(R.drawable.ic_scoreboard_accent));
			imageViewTrains.setImageDrawable(getResources().getDrawable(R.drawable.ic_old_train_accent));
			imageViewTrainCards.setImageDrawable(getResources().getDrawable(R.drawable.ic_cards_accent));
			imageViewDestinationCards.setImageDrawable(getResources().getDrawable(R.drawable.ic_destination_accent));
		} else {
			color = getResources().getColor(R.color.colorPrimary);
			imageViewScore.setImageDrawable(getResources().getDrawable(R.drawable.ic_scoreboard_small));
			imageViewTrains.setImageDrawable(getResources().getDrawable(R.drawable.ic_old_train_small));
			imageViewTrainCards.setImageDrawable(getResources().getDrawable(R.drawable.ic_cards_small));
			imageViewDestinationCards.setImageDrawable(getResources().getDrawable(R.drawable.ic_destination_small));
		}
		this.textViewName.setTextColor(color);
		this.textViewScore.setTextColor(color);
		this.textViewDestinationCards.setTextColor(color);
		this.textViewTrainCards.setTextColor(color);
		this.textViewTrains.setTextColor(color);
	}
	// endregion
	
	// region Constructors
	public PlayerStatsWidget(Context context) {
		this(context, null);
	}
	
	public PlayerStatsWidget(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	@SuppressWarnings({"UnusedDeclaration"})
	public PlayerStatsWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.player_stats, this, true);
		getViews();
		
		this.setPlayerColor(PlayerColor.Blue);
		
		applyAttributes(context, attrs);
	}
	
	private void getViews() {
		imageViewColor = (ImageView) findViewById(R.id.playerStats_imageViewColor);
		textViewName = (TextView) findViewById(R.id.playerStats_textViewName);
		textViewScore = (TextView) findViewById(R.id.playerStats_textViewScore);
		textViewTrains = (TextView) findViewById(R.id.playerStats_textViewTrains);
		textViewTrainCards = (TextView) findViewById(R.id.playerStats_textViewTrainCards);
		textViewDestinationCards = (TextView) findViewById(R.id.playerStats_textViewDestinationCards);
		
		imageViewScore = (ImageView) findViewById(R.id.playerStats_imageViewScore);
		imageViewTrains = (ImageView) findViewById(R.id.playerStats_imageViewTrains);
		imageViewTrainCards = (ImageView) findViewById(R.id.playerStats_imageViewTrainCards);
		imageViewDestinationCards = (ImageView) findViewById(R.id.playerStats_imageViewDestinationCards);
	}
	
	private void applyAttributes(Context context, AttributeSet attrs) {
		//		TypedArray a = context.getTheme()
		//							  .obtainStyledAttributes(attrs, R.styleable.TrainCardWidget, 0, 0);
		//
		//		try {
		//			setShowCount(a.getBoolean(R.styleable.TrainCardWidget_showCount, false));
		//			setCount(a.getInteger(R.styleable.TrainCardWidget_count, 0));
		//			setCardDrawable(a.getDrawable(R.styleable.TrainCardWidget_cardDrawable));
		//		} finally {
		//			a.recycle();
		//		}
	}
	
	// endregion
	
}
