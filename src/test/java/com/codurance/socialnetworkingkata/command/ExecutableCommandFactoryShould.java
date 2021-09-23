package com.codurance.socialnetworkingkata.command;

import com.codurance.socialnetworkingkata.command.input.InputtedCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExecutableCommandFactoryShould {

    private ExecutableCommandFactory target = new ExecutableCommandFactory();

    @Test
    void create_executable_post_command() {
        ExecutableCommand command = target.create(InputtedCommand.POST);

        assertTrue(command instanceof ExecutablePostCommand);
    }
}