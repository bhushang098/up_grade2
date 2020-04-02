package my.upgrade007.upgrade.api;

import my.upgrade007.upgrade.JobsModel.Jobs;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JobApiinterface {

    @GET("posts?key=AIzaSyA4fCXQMyc7MxCys2p93qppNG9dZyklJZ8")
    Call<Jobs> getJobPoste();
}
