package com.codurance.socialnetworkingkata.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExecutablePostCommandTest {

    @Mock
    private UserRepository userRepository;

    private ExecutablePostCommand target;

    @BeforeEach
    void setUp() {
        target = new ExecutablePostCommand(userRepository);
    }

    @Test
    void a_user_can_post_a_message_to_their_timeline() {
        String requestBody = "Alice -> I love the weather today";
        User alice = new User();
        given(userRepository.get("Alice")).willReturn(alice);

        target.execute(requestBody);

        verify(userRepository).updateTimeline(alice, "I love the weather today");
    }
}