package com.codurance.socialnetworkingkata.user;

import java.util.List;

public class User {
    public final List<Post> timeline;

    private User(List<Post> timeline) {
        this.timeline = timeline;
    }

    public static class UserBuilder {
        private List<Post> timeline;

        public UserBuilder withTimeline(List<Post> timeline) {
            this.timeline = timeline;
            return this;
        }

        public User createUser() {
            return new User(timeline);
        }
    }
}


