package byu.codemonkeys.tickettoride.shared.model.map;


import java.util.Objects;

public class Route {
    private City source, destination;
    private int length;
    private boolean claimed;
    private int routeId;
    private String ownerToken;
    //ResourceType type;

    public Route(City x, City y, int length, int routeId) {
        // we always want city 'source' to come first lexicographically
        if (x.getName().compareTo(y.getName()) > 0) {
            this.source = x;
            this.destination = y;
        }
        else {
            this.source = y;
            this.destination = x;
        }
        this.length = length;
        this.claimed = false;
        this.routeId = routeId;
    }

    public boolean claim(String authToken) {
        //This might need more info, like the routeId of the claiming player
        if (isClaimed()) {
            return false; //Can't claim a route twice
        }
        claimed = true;
        ownerToken = authToken;
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

    public String getOwnerToken() {
        return ownerToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o.getClass() != this.getClass())
            return false;

        Route other = (Route) o;

        if (this.source.equals(other.source) && this.destination.equals(other.destination) &&
                this.routeId == other.routeId && this.length == other.length) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source.getName(), destination.getName(), length, routeId);
    }
}
