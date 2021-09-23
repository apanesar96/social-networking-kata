package com.codurance.socialnetworkingkata.user;

import java.time.Instant;

public class Post {

    public final String message;
    public final Instant postedOn;

    public Post(String message, Instant postedOn) {
        this.message = message;
        this.postedOn = postedOn;
    }

}
