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

public class EndGamePlayerStatsWidget extends RelativeLayout {
	
	private static final String TAG = "END_GAME_PLAYER_STATS";
	private ImageView imageViewColor;
	private TextView textViewName;
	private TextView textViewPlayerTotalScore;
	private TextView textViewPointsFromLongestRoute;
	private TextView textViewPointsFromClaimedRoutes;
	private TextView textViewPointsFromCompletedDestinations;
	private TextView textViewPenaltiesFromUnreachedDestinations;
	
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
	
	// region PlayerTotalScore Property
	private int playerTotalScore;
	
	public int getPlayerTotalScore() {
		return playerTotalScore;
	}
	
	public void setPlayerTotalScore(int playerTotalScore) {
		this.playerTotalScore = playerTotalScore;
		this.textViewPlayerTotalScore.setText(String.valueOf(playerTotalScore));
	}
	// endregion
	
	// region PointsFromLongestRoute Property
	private int pointsFromLongestRoute;
	
	public int getPointsFromLongestRoute() {
		return pointsFromLongestRoute;
	}
	
	public void setPointsFromLongestRoute(int pointsFromLongestRoute) {
		this.pointsFromLongestRoute = pointsFromLongestRoute;
		this.textViewPointsFromLongestRoute.setText(String.valueOf(pointsFromLongestRoute));
	}
	// endregion
	// region PointsFromClaimedRoutes Property
	private int pointsFromClaimedRoutes;
	
	public int getPointsFromClaimedRoutes() {
		return pointsFromClaimedRoutes;
	}
	
	public void setPointsFromClaimedRoutes(int pointsFromClaimedRoutes) {
		this.pointsFromClaimedRoutes = pointsFromClaimedRoutes;
		this.textViewPointsFromClaimedRoutes.setText(String.valueOf(pointsFromClaimedRoutes));
	}
	// endregion
	// region PointsFromCompletedDestinations Property
	private int pointsFromCompletedDestinations;
	
	public int getPointsFromCompletedDestinations() {
		return pointsFromCompletedDestinations;
	}
	
	public void setPointsFromCompletedDestinations(int pointsFromCompletedDestinations) {
		this.pointsFromCompletedDestinations = pointsFromCompletedDestinations;
		this.textViewPointsFromCompletedDestinations.setText(String.valueOf(pointsFromCompletedDestinations));
	}
	// endregion
	// region PenaltiesFromUnreachedDestinations Property
	private int penaltiesFromUnreachedDestinations;
	
	public int getPenaltiesFromUnreachedDestinations() {
		return penaltiesFromUnreachedDestinations;
	}
	
	public void setPenaltiesFromUnreachedDestinations(int penaltiesFromUnreachedDestinations) {
		this.penaltiesFromUnreachedDestinations = penaltiesFromUnreachedDestinations;
		this.textViewPenaltiesFromUnreachedDestinations.setText(String.valueOf(penaltiesFromUnreachedDestinations));
	}
	// endregion
	// endregion
	
	// region Constructors
	public EndGamePlayerStatsWidget(Context context) {
		this(context, null);
	}
	
	public EndGamePlayerStatsWidget(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	@SuppressWarnings({"UnusedDeclaration"})
	public EndGamePlayerStatsWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.end_game_player_stats, this, true);
		getViews();
		
		this.setPlayerColor(PlayerColor.Blue);
	}
	
	private void getViews() {
		imageViewColor = (ImageView) findViewById(R.id.endGameStats_imageViewColor);
		textViewName = (TextView) findViewById(R.id.endGameStats_textViewName);
		textViewPlayerTotalScore = (TextView) findViewById(R.id.endGameStats_textViewPlayerTotalScore);
		textViewPointsFromClaimedRoutes = (TextView) findViewById(R.id.endGameStats_textViewPointsFromClaimedRoutes);
		textViewPointsFromLongestRoute = (TextView) findViewById(R.id.endGameStats_textViewPointsFromLongestRoute);
		textViewPointsFromCompletedDestinations = (TextView) findViewById(R.id.endGameStats_textViewPointsFromCompletedDestinations);
		textViewPenaltiesFromUnreachedDestinations = (TextView) findViewById(R.id.endGameStats_textViewPenaltiesFromUnreachedDestinations);
	}
	// endregion
	
}
