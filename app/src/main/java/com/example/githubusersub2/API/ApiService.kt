package com.example.githubuserapp.API_Network
import com.example.githubuserapp.Response.PersonRespons
import com.example.githubuserapp.Response.SearchRespons
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users")
    @Headers("Authorization: token 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")
    fun getPersonList() : Call<List<PersonRespons>>

    @GET("users/{login}")
    @Headers("Authorization: token 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")
    fun getPersonDetail(
        @Path("login") login : String
    ) : Call<PersonRespons>

    @GET("users/{login}/followers")
    @Headers("Authorization: token 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")
    fun getPersonFollowers(
        @Path("login") login : String
    ) : Call<List<PersonRespons>>

    @GET("users/{login}/following")
    @Headers("Authorization: token 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")
    fun getPersonFollowing(
        @Path("login") login : String
    ) : Call<List<PersonRespons>>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchPerson(
        @Query("q") query: String
    ) : Call<SearchRespons>

    companion object {
        private const val API_TOKEN = "token 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"
    }
}