package com.codurance.socialnetworkingkata.command;

import com.codurance.socialnetworkingkata.command.input.InputtedCommand;

public class ExecutableCommandFactory {

    private UserRepository userRepository = new UserRepository() {
        @Override
        public User get(String username) {
            return null;
        }

        @Override
        public void updateTimeline(User user, String message) {

        }
    };

    public ExecutableCommand create(InputtedCommand inputtedCommand) {
        return new ExecutablePostCommand(userRepository);
    }

}
