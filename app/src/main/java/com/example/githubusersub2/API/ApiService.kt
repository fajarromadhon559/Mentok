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
    suspend fun getPersonDetail(
        @Path("username") username : String
    ) : Call<PersonRespons>

    @GET("users/followers")
    @Headers("Authorization: token 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")
    fun getPersonFollowers(
        @Path("username") username : String
    ) : Call<ArrayList<PersonRespons>>

    @GET("users/following")
    @Headers("Authorization: token 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")
    fun getPersonFollowing(
        @Path("username") username : String
    ) : Call<ArrayList<PersonRespons>>

    @GET("search/users")
    fun getSearchPerson(
        @Query("q") query: String,
        @Header("Authorization") token : String
    ) : Call<SearchRespons>

    companion object {
        private const val API_TOKEN = "token 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"
    }
}