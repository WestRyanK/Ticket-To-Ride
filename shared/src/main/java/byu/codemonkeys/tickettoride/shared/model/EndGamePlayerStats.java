package byu.codemonkeys.tickettoride.shared.model;

/**
 * Created by Ryan on 11/4/2017.
 */

public class EndGamePlayerStats {
	
	// region Properties
	//region UserName Property
	private final String userName;
	
	public String getUserName() {
		return userName;
	}
	
	//endregion
	//region PlayerColor Property
	private final PlayerColor playerColor;
	
	public PlayerColor getPlayerColor() {
		return playerColor;
	}
	
	//endregion
	//region PointsFromLongestRoute Property
	private final int pointsFromLongestRoute;
	
	public int getPointsFromLongestRoute() {
		return pointsFromLongestRoute;
	}
	
	//endregion
	//region PointsFromClaimedRoutes Property
	private final int pointsFromClaimedRoutes;
	
	public int getPointsFromClaimedRoutes() {
		return pointsFromClaimedRoutes;
	}
	
	//endregion
	//region PointsFromCompletedDestinations Property
	private final int pointsFromCompletedDestinations;
	
	public int getPointsFromCompletedDestinations() {
		return pointsFromCompletedDestinations;
	}
	
	//endregion
	//region PenaltiesFromUnreachedDestinations Property
	private final int penaltiesFromUnreachedDestinations;
	
	public int getPenaltiesFromUnreachedDestinations() {
		return penaltiesFromUnreachedDestinations;
	}
	
	//endregion
	//region TotalScore Property
	private final int totalScore;
	
	public int getTotalScore() {
		return totalScore;
	}
	//endregion
	// endregion
	
	public EndGamePlayerStats(String userName,
							  PlayerColor playerColor,
							  int totalScore,
							  int pointsFromLongestRoute,
							  int pointsFromClaimedRoutes,
							  int pointsFromCompletedDestinations,
							  int penaltiesFromUnreachedDestinations) {
		this.userName = userName;
		this.playerColor = playerColor;
		this.pointsFromLongestRoute = pointsFromLongestRoute;
		this.pointsFromClaimedRoutes = pointsFromClaimedRoutes;
		this.pointsFromCompletedDestinations = pointsFromCompletedDestinations;
		this.penaltiesFromUnreachedDestinations = penaltiesFromUnreachedDestinations;
		this.totalScore = totalScore;
	}
}
