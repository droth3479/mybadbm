package edu.touro.mco152.bm;

/**
 * Executor class to act as mediator between client and commands
 */
public class Executor {
    ICommand command;

    /**
     * Set the command the executor will execute
     * @param command A concrete command to execute
     */
    public void setCommand(ICommand command){
        this.command = command;
    }

    public void execute(){
        command.execute();
    }
}
