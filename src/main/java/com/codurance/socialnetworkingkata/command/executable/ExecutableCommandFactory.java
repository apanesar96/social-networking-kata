package com.codurance.socialnetworkingkata.command.executable;

import com.codurance.socialnetworkingkata.command.input.InputtedCommand;
import com.codurance.socialnetworkingkata.io.Console;
import com.codurance.socialnetworkingkata.time.DurationDeterminer;
import com.codurance.socialnetworkingkata.user.UserRepository;

import static com.codurance.socialnetworkingkata.command.input.InputtedCommand.FOLLOW;
import static com.codurance.socialnetworkingkata.command.input.InputtedCommand.POST;
import static com.codurance.socialnetworkingkata.command.input.InputtedCommand.READ;

public class ExecutableCommandFactory {

    private final UserRepository userRepository;
    private final Console console;
    private final DurationDeterminer durationDeterminer;

    public ExecutableCommandFactory(UserRepository userRepository, Console console, DurationDeterminer durationDeterminer) {
        this.userRepository = userRepository;
        this.console = console;
        this.durationDeterminer = durationDeterminer;
    }

    public ExecutableCommand create(InputtedCommand inputtedCommand) {
        if (inputtedCommand == POST) return new ExecutablePostCommand(userRepository);
        if (inputtedCommand == READ) return new ExecutableReadCommand(userRepository, console, durationDeterminer);
        if (inputtedCommand == FOLLOW) return new ExecutableFollowCommand(userRepository);

        return new ExecutableWallCommand(userRepository, durationDeterminer, console);
    }

}
