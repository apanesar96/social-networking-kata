package com.codurance.socialnetworkingkata;

import com.codurance.socialnetworkingkata.command.executable.ExecutableCommandFactory;
import com.codurance.socialnetworkingkata.command.input.InputableCommandMapper;
import com.codurance.socialnetworkingkata.io.Console;
import com.codurance.socialnetworkingkata.time.TimestampProvider;
import com.codurance.socialnetworkingkata.user.Post;
import com.codurance.socialnetworkingkata.user.User;
import com.codurance.socialnetworkingkata.user.User.UserBuilder;
import com.codurance.socialnetworkingkata.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.ZoneOffset.UTC;
import static java.util.List.of;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SocialNetworkServiceShould {

    private final Instant NOW = toInstant("2021-09-23T10:15:00");

    private final Console console = mock(Console.class);
    private final InputableCommandMapper inputableCommandMapper = new InputableCommandMapper();
    private final UserRepository userRepository = mock(UserRepository.class);
    private final TimestampProvider timestampProvider = mock(TimestampProvider.class);
    private final ExecutableCommandFactory executableCommandFactory = new ExecutableCommandFactory(userRepository, timestampProvider, console);

    private final SocialNetworkService socialNetworkService = new SocialNetworkService(console, inputableCommandMapper, executableCommandFactory);

    @Test
    void update_user_timeline_with_message_on_submission_of_post_command() {
        withConsoleInput("posting: Alice -> I love the weather today");

        socialNetworkService.submit();

        verify(userRepository).updateTimeline("Alice", "I love the weather today");
    }

    @Test
    void output_user_timeline_on_submission_of_read_command() {
        User bob = createUserBob();
        withConsoleInput("reading: Bob");
        given(userRepository.get("Bob")).willReturn(bob);
        given(timestampProvider.now()).willReturn(NOW);

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
        User bob = createUserBob();
        User alice = createAliceUser();
        User charlie = createCharlieUser(of(bob, alice));
        withConsoleInput("wall: Charlie wall");
        given(userRepository.get("Charlie")).willReturn(charlie);
        given(timestampProvider.now()).willReturn(NOW);

        socialNetworkService.submit();

        InOrder inOrder = inOrder(console);
        inOrder.verify(console).output("Charlie - I'm in New York today! Anyone wants to have a coffee? (moments ago)");
        inOrder.verify(console).output("Bob - Good game though. (1 minute ago)");
        inOrder.verify(console).output("Bob - Damn! We lost! (2 minutes ago)");
        inOrder.verify(console).output("Alice - I love the weather today (5 minutes ago)");
    }

    private Instant toInstant(String dateTime) {
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime);
        return parsedDateTime.toInstant(UTC);
    }

    private void withConsoleInput(String input) {
        given(console.readInput()).willReturn(input);
    }

    private User createUserBob() {
        List<Post> posts = of(
                withPost("Good game though.", "2021-09-23T10:14:00"),
                withPost("Damn! We lost!", "2021-09-23T10:13:00")

        );

        return new UserBuilder().withUsername("Bob").withTimeline(posts).createUser();
    }

    private User createAliceUser() {
        List<Post> posts = of(
                withPost("I love the weather today", "2021-09-23T10:10:00")

        );

        return new UserBuilder().withUsername("Alice").withTimeline(posts).createUser();
    }

    private User createCharlieUser(List<User> following) {
        List<Post> posts = of(
                withPost("I'm in New York today! Anyone wants to have a coffee?", "2021-09-23T10:14:45")

        );

        return new UserBuilder().withUsername("Charlie").withTimeline(posts).withFollowing(following).createUser();
    }

    public Post withPost(String message, String dateTime) {
        Instant timestamp = toInstant(dateTime);
        return new Post(message, timestamp);
    }

}
