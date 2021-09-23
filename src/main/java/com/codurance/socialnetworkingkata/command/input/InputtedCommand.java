package com.codurance.socialnetworkingkata.command.input;

public enum InputtedCommand {
    POST("posting"),
    READ("reading"),
    FOLLOW("following"),
    WALL("wall");

    public final String name;

    InputtedCommand(String name) {
        this.name = name;
    }
}
