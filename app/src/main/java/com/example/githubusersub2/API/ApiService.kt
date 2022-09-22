package com.example.githubuserapp.API_Network
import com.example.githubuserapp.Response.PersonRespons
import com.example.githubuserapp.Response.SearchRespons
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    @Headers("Authorization: token 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")
    suspend fun getPersonList() : ArrayList<PersonRespons>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    suspend fun getPersonDetail(
        @Path("username") username : String
    ) : PersonRespons

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    suspend fun getPersonFollowers(
        @Path("username") username : String
    ) : ArrayList<PersonRespons>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    suspend fun getPersonFollowing(
        @Path("username") username : String
    ) : ArrayList<PersonRespons>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchPerson(
        @Query("q") query: String
    ) : Call<SearchRespons>

    companion object {
        private const val API_TOKEN = "token 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"
    }
}