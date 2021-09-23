package com.codurance.socialnetworkingkata.user;

import java.util.List;

public class User {

    public final String username;
    public final List<Post> timeline;
    public final List<User> following;

    private User(String username, List<Post> timeline, List<User> following) {
        this.username = username;
        this.timeline = timeline;
        this.following = following;
    }

    public static class UserBuilder {

        private String username;
        private List<Post> timeline;
        private List<User> following;

        public UserBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder withTimeline(List<Post> timeline) {
            this.timeline = timeline;
            return this;
        }

        public UserBuilder withFollowing(List<User> following) {
            this.following = following;
            return this;
        }

        public User createUser() {
            return new User(username, timeline, following);
        }

    }

}


