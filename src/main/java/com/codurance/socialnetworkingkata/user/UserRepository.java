package com.codurance.socialnetworkingkata.user;

public interface UserRepository {

    User get(String username);

    void updateTimeline(String username, String message);

    void followUser(String subscriberUsername, String username);

}