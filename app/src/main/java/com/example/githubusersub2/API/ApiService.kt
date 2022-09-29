package com.example.githubuserapp.API_Network
import com.example.githubuserapp.Response.PersonRespons
import com.example.githubuserapp.Response.SearchRespons
import com.example.githubusersub2.BuildConfig
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users")
    @Headers("Authorization: ${myScretKey}")
    fun getPersonList() : Call<List<PersonRespons>>

    @GET("users/{login}")
    @Headers("Authorization: ${myScretKey}")
    fun getPersonDetail(
        @Path("login") login : String
    ) : Call<PersonRespons>

    @GET("users/{login}/followers")
    @Headers("Authorization: ${myScretKey}")
    fun getPersonFollowers(
        @Path("login") login : String
    ) : Call<List<PersonRespons>>

    @GET("users/{login}/following")
    @Headers("Authorization: ${myScretKey}")
    fun getPersonFollowing(
        @Path("login") login: String
    ) : Call<List<PersonRespons>>

    @GET("search/users")
    @Headers("Authorization: ${myScretKey}")
    fun getSearchPerson(
        @Query("q") query: String
    ) : Call<SearchRespons>

    companion object {

        const val myScretKey = BuildConfig.API_TOKEN
    }
}