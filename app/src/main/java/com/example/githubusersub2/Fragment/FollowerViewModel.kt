package com.example.githubusersub2.Fragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuserapp.API_Network.ApiConfig
import com.example.githubuserapp.Response.PersonRespons
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel {
    val listUsersFollowers = MutableLiveData<List<PersonRespons>>()

    fun setListFollowers(context: Context){
        val login = String()
        val client =  ApiConfig.getApiService(context).getPersonFollowers(login)
            client.enqueue(object : Callback<List<PersonRespons>> {

                override fun onResponse(
                    call: Call<List<PersonRespons>>,
                    response: Response<List<PersonRespons>>
                ) {
                    if (response.isSuccessful) {
                        listUsersFollowers.postValue((response.body()))
                    }
                }

                override fun onFailure(call: Call<List<PersonRespons>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getListFollowers(): LiveData<List<PersonRespons>> {
        return listUsersFollowers
    }
}