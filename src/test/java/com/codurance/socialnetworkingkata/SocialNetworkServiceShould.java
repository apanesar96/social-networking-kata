package com.codurance.socialnetworkingkata;

import com.codurance.socialnetworkingkata.command.executable.ExecutableCommandFactory;
import com.codurance.socialnetworkingkata.command.input.InputableCommandMapper;
import com.codurance.socialnetworkingkata.io.Console;
import com.codurance.socialnetworkingkata.time.DurationDeterminer;
import com.codurance.socialnetworkingkata.user.Post;
import com.codurance.socialnetworkingkata.user.User;
import com.codurance.socialnetworkingkata.user.User.UserBuilder;
import com.codurance.socialnetworkingkata.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.time.Instant;
import java.util.List;

import static java.util.List.of;
import static java.util.stream.Collectors.toList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SocialNetworkServiceShould {

    private final Console console = mock(Console.class);
    private final InputableCommandMapper inputableCommandMapper = new InputableCommandMapper();
    private final UserRepository userRepository = mock(UserRepository.class);
    private final DurationDeterminer durationDeterminer = mock(DurationDeterminer.class);
    private final ExecutableCommandFactory executableCommandFactory = new ExecutableCommandFactory(userRepository, console, durationDeterminer);

    private final SocialNetworkService socialNetworkService = new SocialNetworkService(console, inputableCommandMapper, executableCommandFactory);

    @Test
    void update_user_timeline_with_message_on_submission_of_post_command() {
        withConsoleInput("posting: Alice -> I love the weather today");

        socialNetworkService.submit();

        verify(userRepository).updateTimeline("Alice", "I love the weather today");
    }

    @Test
    void output_user_timeline_on_submission_of_read_command() {
        Instant postTimestamp = Instant.now();
        User bob = withTimelinePosts(of("Good game though.", "Damn! We lost!"), postTimestamp).createUser();
        withConsoleInput("reading: Bob");
        given(userRepository.get("Bob")).willReturn(bob);
        given(durationDeterminer.calculateDurationFromNow(postTimestamp)).willReturn("1 minute ago", "2 minutes ago");

        socialNetworkService.submit();

        InOrder inOrder = inOrder(console);
        inOrder.verify(console).output("Good game though. (1 minute ago)");
        inOrder.verify(console).output("Damn! We lost! (2 minutes ago)");
    }

    @Test void
    follow_requested_user_for_user() {
        withConsoleInput("following: Charlie follows Bob");

        socialNetworkService.submit();

        verify(userRepository).followUser("Charlie", "Bob");
    }

    @Test
    void output_user_wall() {
        Instant postTimestamp = Instant.now();
        User bob = withTimelinePosts(of("Good game though.", "Damn! We lost!"), postTimestamp).withUsername("Bob").createUser();
        User alice = withTimelinePosts(of("I love the weather today"), postTimestamp).withUsername("Alice").createUser();
        User charlie = withTimelinePosts(of("I'm in New York today! Anyone wants to have a coffee?"), postTimestamp)
                .withUsername("Charlie")
                .withFollowing(of(bob, alice))
                .createUser();
        withConsoleInput("wall: Charlie wall");
        given(userRepository.get("Charlie")).willReturn(charlie);
        given(durationDeterminer.calculateDurationFromNow(postTimestamp)).willReturn("15 seconds ago", "1 minute ago", "2 minutes ago", "5 minutes ago");

        socialNetworkService.submit();

        InOrder inOrder = inOrder(console);
        inOrder.verify(console).output("Charlie - I'm in New York today! Anyone wants to have a coffee? (15 seconds ago)");
        inOrder.verify(console).output("Bob - Good game though. (1 minute ago)");
        inOrder.verify(console).output("Bob - Damn! We lost! (2 minutes ago)");
        inOrder.verify(console).output("Alice - I love the weather today (5 minutes ago)");
    }

    private void withConsoleInput(String input) {
        given(console.readInput()).willReturn(input);
    }

    public UserBuilder withTimelinePosts(List<String> messages, Instant timestamp) {
        List<Post> timeline = messages.stream().map(message -> new Post(message, timestamp)).collect(toList());

        return new UserBuilder().withTimeline(timeline);
    }

}
