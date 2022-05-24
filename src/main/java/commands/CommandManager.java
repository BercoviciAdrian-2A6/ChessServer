package commands;

import tcp.ClientThread;

import java.util.ArrayList;

public class CommandManager
{
    static CommandManager singleton = null;
    ArrayList<Command> commands = new ArrayList<>();

    private CommandManager ()
    {
        commands.add( new LoginCommand("login") );
        commands.add( new LogoutCommand("logout") );
        commands.add( new RegisterCommand("register") );
        commands.add( new IdleCommand("idle") );
        commands.add( new QueueCommand("queue") );
        commands.add( new ChessMoveCommand("move") );
        commands.add( new ForfeitCommand("forfeit") );
        commands.add( new GameMessageCommand("gameMessage") );
        commands.add( new ReplayCommand("replay") );
        commands.add( new MatchHistoryCommand("history") );
        commands.add( new InviteCommand("invite") );
        commands.add( new ViewReceivedInvitesCommand("viewRecInvites") );
        commands.add( new ViewSentInvitationsCommand("viewSentInvites") );
        commands.add( new CancelInviteCommand("cancelInvite") );
        commands.add( new RespondInviteCommand("respondInvite") );
        commands.add( new DequeueCommand("dequeue") );
    }

    public static CommandManager getSingleton()
    {
        if (singleton == null)
            singleton = new CommandManager();

        return singleton;
    }

    public CommandOutput runCommand(String commandLine, ClientThread clientThread) throws Exception {
        /**
         * the first two parameters are sender and commandTrigger
         * the following parameters are command parameters
         */
        String[] commandBreakdown = commandLine.split(" ");

        ArrayList<String> paremeters = new ArrayList<>();

        for (int parameterIndex = 2; parameterIndex < commandBreakdown.length; parameterIndex++)
        {
            paremeters.add( commandBreakdown[parameterIndex] );
        }

        CommandOutput commandOutput = null;

        for (Command command: commands)
        {
            commandOutput = command.triggerCommand( commandBreakdown[0], commandBreakdown[1], paremeters, clientThread);

            if (commandOutput != null) {//a command has been triggered
                return commandOutput;
            }
        }

        return null;

    }
}
