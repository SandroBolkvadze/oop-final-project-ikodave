package com.example.submission.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Command {

    private final List<String> command;

    public Command() {
        command = new ArrayList<>();
    }

    public Command(String command) {
        this.command = new ArrayList<>();
        appendCommand(command);
    }

    public Command(String... subCommands) {
        this.command = new ArrayList<>();
        appendCommand(subCommands);
    }

    public void appendCommand(String command) {
        this.command.addAll(List.of(command.split(" ")));
    }

    public void appendCommand(String... subCommands) {
        Collections.addAll(command, subCommands);
    }

    public List<String> getCommand() {
        return command;
    }
}
