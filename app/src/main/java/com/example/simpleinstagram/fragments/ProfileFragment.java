package com.example.simpleinstagram.fragments;

import android.util.Log;

import com.example.simpleinstagram.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment{

    @Override
    protected void queryPost(){
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder(Post.KEY_CREATED);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e!= null){
                    Log.e(TAG, "Issue with getting post", e);
                    return;
                }
                for (Post post:posts){
                    Log.i(TAG, "Post: "+post.getDescription()+" user: "+ post.getUser().getUsername());
                }
                adaptor.clear();
                adaptor.addAll(posts);
                //allPost.addAll(posts);
                //adaptor.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
