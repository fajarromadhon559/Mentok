package com.example.githubusersub2.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusersub2.Adapter.FollowerAdapter
import com.example.githubusersub2.R
import com.example.githubusersub2.databinding.FragmentFollowersBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import org.json.JSONObject
import retrofit2.http.Header


class FragmentFollowers : Fragment() {

    private lateinit var binding : FragmentFollowersBinding
    private var layoutManager : RecyclerView.LayoutManager?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)

        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root

        getPersonFollowers(this)
    }

    private fun getPersonFollowers(fragmentFollowers : FragmentFollowers){

        fragmentFollowers.binding.progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/{username}/followers"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler(){

            override fun onSuccess(
                statusCode: Int,
                headers: Array<out cz.msebera.android.httpclient.Header>?,
                responseBody: ByteArray?
            ) {
                fragmentFollowers.binding.progressBar.visibility = View.INVISIBLE

                val listPersonFollower = ArrayList<String>()
                val result = String(responseBody!!)

                try {
                    val jsonObject = JSONObject(result)
                    val dataArray = jsonObject.getJSONArray("items")
                    for (i in 0 until dataArray.length()){
                        val dataObject = dataArray.getJSONObject(i)
                        val username = dataObject.getString("login")
                        val followes_url = dataObject.getString("followes_url")
                        listPersonFollower.add("\nUsername : $username\nFollowers URL : $followes_url\n")
                    }
                    val adapter = FollowerAdapter(listPersonFollower)
                    fragmentFollowers.binding.rvFollowers.adapter = adapter
                    fragmentFollowers.layoutManager = LinearLayoutManager(fragmentFollowers.activity)
                }
                catch (e : Exception){
                    e.printStackTrace()
                }
            }
            override fun onFailure(
                statusCode: Int,
                headers: Array<out cz.msebera.android.httpclient.Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                fragmentFollowers.binding.progressBar.visibility = View.INVISIBLE
            }
        })
    }
}