package com.aldoapps.retrofitpractice.net;

import com.aldoapps.retrofitpractice.data.DiscoverResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by aldokelvianto on 19/04/16.
 */
public interface TmdbApi {
    @GET("3/discover/movie")
    Call<DiscoverResponse> discoverMovies(@Query("sort_by") String sortBy,
                                          @Query("api_key") String apiKey);

}
