package com.codurance.socialnetworkingkata.command;

public interface UserRepository {

    User get(String username);

    void updateTimeline(User user, String message);
}