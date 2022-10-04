package com.example.githubusersub2.API
import com.example.githubusersub2.BuildConfig
import com.example.githubusersub2.Response.PersonRespons
import com.example.githubusersub2.Response.SearchRespons
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("users/{login}/{item}")
    @Headers("Authorization: ${myScretKey}")
    fun getPersonFollowing(
        @Path("login") login : String,
        @Path("item") item : String
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