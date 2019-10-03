package com.example.upgrade.api;

import com.example.upgrade.BlogModel.Blogs;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BlogApiInterface {


    @GET("posts?key=AIzaSyA4fCXQMyc7MxCys2p93qppNG9dZyklJZ8")
    Call<Blogs> getBlogPoste();

}
