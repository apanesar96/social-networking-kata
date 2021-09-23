package com.codurance.socialnetworkingkata;

import com.codurance.socialnetworkingkata.command.executable.ExecutableCommand;
import com.codurance.socialnetworkingkata.command.executable.ExecutableCommandFactory;
import com.codurance.socialnetworkingkata.command.input.InputtedCommand;
import com.codurance.socialnetworkingkata.command.input.InputableCommandMapper;

public class SocialNetworkService {

    private static final String REQUEST_SEPARATOR = ":";

    private final Console console;
    private final InputableCommandMapper inputableCommandMapper;
    private final ExecutableCommandFactory executableCommandFactory;

    public SocialNetworkService(Console console, InputableCommandMapper inputableCommandMapper, ExecutableCommandFactory executableCommandFactory) {
        this.console = console;
        this.inputableCommandMapper = inputableCommandMapper;
        this.executableCommandFactory = executableCommandFactory;
    }

    public void submit() {
        String input = console.readInput();
        String[] request = input.split(REQUEST_SEPARATOR);

        String command = request[0];
        InputtedCommand inputtedCommand = inputableCommandMapper.map(command);

        ExecutableCommand executableCommand = executableCommandFactory.create(inputtedCommand);
        String requestBody = request[1].trim();
        executableCommand.execute(requestBody);
    }
}
