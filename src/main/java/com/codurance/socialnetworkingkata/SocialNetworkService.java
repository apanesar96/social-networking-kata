package com.codurance.socialnetworkingkata;

public class SocialNetworkService {

    private final Console console;

    public SocialNetworkService(Console console) {
        this.console = console;
    }

    public void submit() {
        String input = console.readInput();
        console.output(input);
    }
}
