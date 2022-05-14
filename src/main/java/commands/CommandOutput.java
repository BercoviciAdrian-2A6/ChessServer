package commands;

import game.GameRoom;

public class CommandOutput
{
    private String message;
    private int status;

    public CommandOutput()
    {
        message = "Wrong command";
        status = -1;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
