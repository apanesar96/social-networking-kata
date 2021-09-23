package com.codurance.socialnetworkingkata.command.executable;

import com.codurance.socialnetworkingkata.command.executable.ExecutableCommand;
import com.codurance.socialnetworkingkata.user.User;
import com.codurance.socialnetworkingkata.user.UserRepository;

public class ExecutablePostCommand extends ExecutableCommand {

    private static final String POST_SEPARATOR = " -> ";

    private final UserRepository userRepository;

    public ExecutablePostCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(String requestBody) {
        String[] post = requestBody.split(POST_SEPARATOR);
        String username = post[0];
        String message = post[1];

        userRepository.updateTimeline(username, message);
    }
}
