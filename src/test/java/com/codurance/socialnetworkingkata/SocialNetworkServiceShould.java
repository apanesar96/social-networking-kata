package com.codurance.socialnetworkingkata;

import com.codurance.socialnetworkingkata.command.ExecutableCommand;
import com.codurance.socialnetworkingkata.command.ExecutableCommandFactory;
import com.codurance.socialnetworkingkata.command.input.InputtedCommand;
import com.codurance.socialnetworkingkata.command.input.InputableCommandMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SocialNetworkServiceShould {

	@Mock
	private Console console;

	@Mock
	private InputableCommandMapper inputableCommandMapper;

	@Mock
	private ExecutableCommandFactory executableCommandFactory;

	@Test
	void submit_command() {
		SocialNetworkService socialNetworkService = new SocialNetworkService(console, inputableCommandMapper, executableCommandFactory);
		InputtedCommand inputtedCommand = mock(InputtedCommand.class);
		ExecutableCommand executableCommand = mock(ExecutableCommand.class);
		given(console.readInput()).willReturn("command: request from user");
		given(inputableCommandMapper.map("command")).willReturn(inputtedCommand);
		given(executableCommandFactory.create(inputtedCommand)).willReturn(executableCommand);

		socialNetworkService.submit();

		verify(executableCommand).execute("request from user");
	}
}
