package com.codurance.socialnetworkingkata.command.executable;

import com.codurance.socialnetworkingkata.Console;
import com.codurance.socialnetworkingkata.DurationDeterminer;
import com.codurance.socialnetworkingkata.user.Post;
import com.codurance.socialnetworkingkata.user.User;
import com.codurance.socialnetworkingkata.user.UserRepository;

import static java.lang.String.format;

public class ExecutableReadCommand extends ExecutableCommand {

    private final UserRepository userRepository;
    private final Console console;
    private final DurationDeterminer durationDeterminer;

    public ExecutableReadCommand(UserRepository userRepository, Console console, DurationDeterminer durationDeterminer) {
        this.userRepository = userRepository;
        this.console = console;
        this.durationDeterminer = durationDeterminer;
    }

    @Override
    public void execute(String requestBody) {
        User user = userRepository.get(requestBody);
        for (Post post: user.timeline) {
            String duration = durationDeterminer.calculateDurationFromNow(post.postedOn);
            String output = format("%s (%s)", post.message, duration);
            console.output(output);
        }
    }
}
