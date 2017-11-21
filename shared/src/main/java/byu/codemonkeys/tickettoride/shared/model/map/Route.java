package byu.codemonkeys.tickettoride.shared.model.map;


import java.util.Objects;

import byu.codemonkeys.tickettoride.shared.model.cards.CardType;
import byu.codemonkeys.tickettoride.shared.model.UserBase;

public class Route {
	public static int getPointValue(int length) {
		switch (length) {
			case 1 :
				return 1;
			case 2:
				return 2;
			case 3:
				return 4;
			case 4:
				return 7;
			case 5:
				return 10;
			case 6:
				return 15;
			default:
				throw new IllegalArgumentException(String.format("Length: %d", length));
		}
	}

	private final City source, destination;
	private final int length;
	private final int routeId;
	private final CardType routeType;
	private boolean claimed, parallel;
	private int parallelRouteID;
	private UserBase owner;
	
	public Route(int routeId, CardType routeType, City x, City y, int length) {
		// we always want city 'source' to come first lexicographically
		if (x.getName().compareTo(y.getName()) > 0) {
			this.source = x;
			this.destination = y;
		} else {
			this.source = y;
			this.destination = x;
		}
		this.length = length;
		this.claimed = false;
		this.routeId = routeId;
		this.routeType = routeType;
		this.parallel = false;
	}
	
	public boolean claim(UserBase user) {
		//This might need more info, like the routeId of the claiming player
		if (isClaimed()) {
			return false; //Can't claim a route twice
		}
		claimed = true;
		owner = user;
		return true;
	}
	
	public boolean isClaimed() {
		return claimed;
	}
	
	public boolean containsCity(City x) {
		return (source.equals(x) || destination.equals(x));
	}
	
	public int getLength() {
		return length;
	}
	
	public City getSource() {
		return source;
	}
	
	public City getDestination() {
		return destination;
	}
	
	public UserBase getOwner() {
		return owner;
	}

	public void setParallelRoute(int parallelRouteID) {
		this.parallelRouteID = parallelRouteID;
		parallel = true;

	}

	public boolean isParallel() {
		return parallel;
	}

	public int getParallelRouteID() {
		return parallelRouteID;
	}

	public int getRouteId() {
		return routeId;
	}

	public int getPoints() {
		switch (length) {
			case 1:
				return 1;
			case 2:
				return 2;
			case 3:
				return 4;
			case 4:
				return 7;
			case 5:
				return 10;
			case 6:
				return 15;
			default:
				return 0;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		
		if (o.getClass() != this.getClass())
			return false;
		
		Route other = (Route) o;
		
		if (this.source.equals(other.source) &&
				this.destination.equals(other.destination) &&
				this.routeId == other.routeId &&
				this.length == other.length) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(source.getName(), destination.getName(), length, routeId);
	}
	
	public CardType getRouteType() {
		return routeType;
	}

	public City getOtherCity(City city) {
		if (source.equals(city)) return destination;
		if (destination.equals(city)) return source;

		return null;
	}
}
