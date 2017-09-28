package byu.codemonkeys.tickettoride.shared.results;

public abstract class Result {
    private boolean successful;
    private String errorMessage;

    public boolean isSuccessful() {
        return successful;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
