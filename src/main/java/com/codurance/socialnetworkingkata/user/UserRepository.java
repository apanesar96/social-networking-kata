package com.codurance.socialnetworkingkata.user;

import com.codurance.socialnetworkingkata.user.User;

public interface UserRepository {

    User get(String username);

    void updateTimeline(User user, String message);
}