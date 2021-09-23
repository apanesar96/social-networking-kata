package com.codurance.socialnetworkingkata;

import com.codurance.socialnetworkingkata.command.executable.ExecutableCommandFactory;
import com.codurance.socialnetworkingkata.command.input.InputableCommandMapper;
import com.codurance.socialnetworkingkata.io.Console;
import com.codurance.socialnetworkingkata.time.DurationDeterminer;
import com.codurance.socialnetworkingkata.user.Post;
import com.codurance.socialnetworkingkata.user.User;
import com.codurance.socialnetworkingkata.user.User.UserBuilder;
import com.codurance.socialnetworkingkata.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SocialNetworkServiceShould {

	@Mock
	private Console console;

	private InputableCommandMapper inputableCommandMapper;

	private ExecutableCommandFactory executableCommandFactory;

	@Mock
	private UserRepository userRepository;

	@Mock
	private DurationDeterminer durationDeterminer;

	private SocialNetworkService socialNetworkService;


	@BeforeEach
	void setUp() {
		inputableCommandMapper = new InputableCommandMapper();
		executableCommandFactory = new ExecutableCommandFactory(userRepository, console, durationDeterminer);
		socialNetworkService = new SocialNetworkService(console, inputableCommandMapper, executableCommandFactory);
	}

	@Test
	void update_user_timeline_with_message_on_submission_of_post_command() {
		given(console.readInput()).willReturn("posting: Alice -> I love the weather today");
		User alice = new UserBuilder().createUser();
		given(userRepository.get("Alice")).willReturn(alice);

		socialNetworkService.submit();

		verify(userRepository).updateTimeline(alice, "I love the weather today");
	}

	@Test
	void output_user_timeline_on_submission_of_read_command() {
		Instant postTimestamp = Instant.now();
		List<Post> timeline = List.of(
				new Post("Good game though.", postTimestamp),
				new Post("Damn! We lost!", postTimestamp)
		);
		User bob = new UserBuilder().withTimeline(timeline).createUser();
		given(console.readInput()).willReturn("reading: Bob");
		given(userRepository.get("Bob")).willReturn(bob);
		given(durationDeterminer.calculateDurationFromNow(postTimestamp)).willReturn("1 minute ago", "2 minutes ago");

		socialNetworkService.submit();

		verify(console).output("Good game though. (1 minute ago)");
		verify(console).output("Damn! We lost! (2 minutes ago)");
	}
}
