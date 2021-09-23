package com.codurance.socialnetworkingkata.command;

public class ExecutablePostCommand extends ExecutableCommand {

    private static final String POST_SEPARATOR = "->";

    private final UserRepository userRepository;

    public ExecutablePostCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(String requestBody) {
        String[] post = requestBody.split(POST_SEPARATOR);
        String username = post[0].trim();
        String message = post[1].trim();

        User user = userRepository.get(username);
        userRepository.updateTimeline(user, message);
    }
}
