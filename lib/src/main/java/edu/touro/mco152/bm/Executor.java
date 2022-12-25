package edu.touro.mco152.bm;

public class Executor {
    ICommand command;

    public void setCommand(ICommand command){
        this.command = command;
    }

    public void execute(){
        command.execute();
    }
}
