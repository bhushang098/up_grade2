package com.example.upgrade.api;

import com.example.upgrade.JobsModel.Jobs;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JobApiinterface {

    @GET("posts?key=AIzaSyA4fCXQMyc7MxCys2p93qppNG9dZyklJZ8")
    Call<Jobs> getJobPoste();
}
