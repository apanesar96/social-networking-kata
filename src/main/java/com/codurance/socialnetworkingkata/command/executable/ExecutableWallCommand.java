package com.codurance.socialnetworkingkata.command.executable;

import com.codurance.socialnetworkingkata.io.Console;
import com.codurance.socialnetworkingkata.time.DurationDeterminer;
import com.codurance.socialnetworkingkata.user.Post;
import com.codurance.socialnetworkingkata.user.User;
import com.codurance.socialnetworkingkata.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class ExecutableWallCommand extends ExecutableCommand {

    private static final String WALL_SEPARATOR = " wall";

    private final UserRepository userRepository;
    private DurationDeterminer durationDeterminer;
    private Console console;

    public ExecutableWallCommand(UserRepository userRepository, DurationDeterminer durationDeterminer, Console console) {
        this.userRepository = userRepository;
        this.durationDeterminer = durationDeterminer;
        this.console = console;
    }

    @Override
    public void execute(String requestBody) {
        String[] wallRequest = requestBody.split(WALL_SEPARATOR);
        String username = wallRequest[0];
        User user = userRepository.get(username);

        for (User wallUser : getUsersOnWall(user))
            outputUserPosts(wallUser);
    }

    private List<User> getUsersOnWall(User user) {
        List<User> following = user.following;

        List<User> usersOnWall = new ArrayList<>();
        usersOnWall.add(user);
        usersOnWall.addAll(following);

        return usersOnWall;
    }

    private void outputUserPosts(User user) {
        for (Post post: user.timeline) {
            String duration = durationDeterminer.calculateDurationFromNow(post.postedOn);
            String output = format("%s - %s (%s)", user.username, post.message, duration);
            console.output(output);
        }
    }

}
