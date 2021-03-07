package com.example.simpleinstagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simpleinstagram.Post;
import com.example.simpleinstagram.PostsAdaptor;
import com.example.simpleinstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {

    RecyclerView rvPosts;
    public static final String TAG = "PostsFragment";
    protected PostsAdaptor adaptor;
    protected List<Post> allPost;
    SwipeRefreshLayout swipeRefreshLayout;

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);
        allPost = new ArrayList<>();
        adaptor = new PostsAdaptor(getContext(), allPost);
        rvPosts.setAdapter(adaptor);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout =view.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "onRefresh");
                queryPost();
            }
        });
        queryPost();

    }
    protected void queryPost() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
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