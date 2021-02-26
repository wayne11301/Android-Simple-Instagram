package com.example.simpleinstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Post.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("XHk8aG8JFCWEZWFMpekloyYrTYe9h6HXsmxj95HH")
                .clientKey("5ZfcwJiJf5uaDTwAX2zU9BTxWv6a9Sde3gbysH6C")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
