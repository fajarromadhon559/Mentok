package com.example.githubusersub2

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.API_Network.ApiConfig
import com.example.githubuserapp.Response.PersonRespons
import com.example.githubuserapp.Response.SearchRespons
import kotlinx.coroutines.Job
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainViewModel : ViewModel() {
    private val _person = MutableLiveData<ArrayList<PersonRespons>?>()
    val person: LiveData<ArrayList<PersonRespons>?> = _person
    private val _searchPerson = MutableLiveData<ArrayList<PersonRespons>?>()
    val searchPerson : LiveData<ArrayList<PersonRespons>?> = _searchPerson
    private val _loading = MutableLiveData<Boolean>()
    private val _DataFailed = MutableLiveData<Boolean>()
    private var viewModelJob = Job()

    fun getSearchPerson(context: Context, person : String){
        _loading.value = true
        val token = "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"
        val client = ApiConfig.getApiService(context).getSearchPerson(person, token)
        client.enqueue(object : retrofit2.Callback<SearchRespons> {
            override fun onResponse(call: Call<SearchRespons>, response: Response<SearchRespons>) {
                if (response.isSuccessful){
                    _loading.value = false
                    val responseBody = response.body()?.items
                    if (responseBody != null){
                        if (responseBody != null){
                            _searchPerson.postValue(responseBody)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<SearchRespons>, t: Throwable){
                _loading.value = false
                _DataFailed.value = true
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }

        })
    }
    companion object{
        private const val TAG = "MainViewModel"
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

