package com.codurance.socialnetworkingkata.command.input;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputableCommandMapperTest {

    private final InputableCommandMapper target = new InputableCommandMapper();

    @ParameterizedTest
    @CsvSource({
            "posting, POST",
            "reading, READ",
            "following, FOLLOW",
            "wall, WALL"
    })
    void map_to_known_inputted_commands(String command, String inputtedCommand) {
        InputtedCommand expectedInputtedCommand = InputtedCommand.valueOf(inputtedCommand);

        assertEquals(expectedInputtedCommand, target.map(command));
    }

    @Test void
    throw_exception_when_unable_to_map_command() {
        InvalidInputtedCommandException exception = assertThrows(InvalidInputtedCommandException.class, () -> target.map("unknown"));

        assertEquals("Unable to map to unknown command", exception.getMessage());
    }
}