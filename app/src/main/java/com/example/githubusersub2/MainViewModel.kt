package com.example.githubusersub2

import android.content.ContentValues.TAG
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
    private val _searchPerson = MutableLiveData<ArrayList<PersonRespons>?>()
    val searchPerson : LiveData<ArrayList<PersonRespons>?> = _searchPerson
    private val _loading = MutableLiveData<Boolean>()
    private val _DataFailed = MutableLiveData<Boolean>()
    private var viewModelJob = Job()

    fun getSearchPerson(person : String){
        _loading.value = true
        val client = ApiConfig.getApiService().getSearchPerson(person)
        client.enqueue(object : retrofit2.Callback<SearchRespons> {
            override fun onResponse(call: Call<SearchRespons>, response: Response<SearchRespons>) {
                if (response.isSuccessful){
                    _loading.value = false
                    val responseBody = response.body()
                    if (responseBody != null){
                        if (responseBody.item != null){
                            _searchPerson.postValue(responseBody.item)
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

