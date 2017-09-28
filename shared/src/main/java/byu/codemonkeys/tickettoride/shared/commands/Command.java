package byu.codemonkeys.tickettoride.shared.commands;

public abstract class Command {
    private String commandName;

    protected Command(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
