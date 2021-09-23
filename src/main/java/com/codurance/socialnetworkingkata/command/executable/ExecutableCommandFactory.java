package com.codurance.socialnetworkingkata.command.executable;

import com.codurance.socialnetworkingkata.command.input.InputtedCommand;
import com.codurance.socialnetworkingkata.io.Console;
import com.codurance.socialnetworkingkata.time.TimestampProvider;
import com.codurance.socialnetworkingkata.user.UserRepository;

import static com.codurance.socialnetworkingkata.command.input.InputtedCommand.FOLLOW;
import static com.codurance.socialnetworkingkata.command.input.InputtedCommand.POST;
import static com.codurance.socialnetworkingkata.command.input.InputtedCommand.READ;
import static com.codurance.socialnetworkingkata.command.input.InputtedCommand.WALL;
import static java.lang.String.format;

public class ExecutableCommandFactory {

    private final UserRepository userRepository;
    private final TimestampProvider timestampProvider;
    private final Console console;

    public ExecutableCommandFactory(UserRepository userRepository, TimestampProvider timestampProvider, Console console) {
        this.userRepository = userRepository;
        this.timestampProvider = timestampProvider;
        this.console = console;
    }

    public ExecutableCommand create(InputtedCommand inputtedCommand) {
        if (inputtedCommand == POST) return new ExecutablePostCommand(userRepository);
        if (inputtedCommand == READ) return new ExecutableReadCommand(userRepository, timestampProvider, console);
        if (inputtedCommand == FOLLOW) return new ExecutableFollowCommand(userRepository);
        if (inputtedCommand == WALL) return new ExecutableWallCommand(userRepository, timestampProvider, console);

        throw new ExecutableCommandNotImplementedException(format("Executable command for inputted command '%s' not implemented", inputtedCommand.name));
    }

}
