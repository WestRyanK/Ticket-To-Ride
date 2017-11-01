package byu.codemonkeys.tickettoride.shared.model;

public class DestinationCard {
    protected int score;
    protected String startCity;
    protected String endCity;

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (o.getClass() != this.getClass()) {
            return false;
        }

        DestinationCard other = (DestinationCard) o;

        return (other.startCity.equals(this.startCity)) && (other.endCity.equals(this.endCity));
    }
}
