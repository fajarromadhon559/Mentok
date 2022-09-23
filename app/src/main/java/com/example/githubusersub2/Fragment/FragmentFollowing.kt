package com.example.githubusersub2.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusersub2.Adapter.FollowerAdapter
import com.example.githubusersub2.R
import com.example.githubusersub2.databinding.FragmentFollowingBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import org.json.JSONObject


class FragmentFollowing : Fragment() {

    private lateinit var binding : FragmentFollowingBinding
    private var layoutManager : RecyclerView.LayoutManager?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)

        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root

        getPersonFollowing(this)
    }

    private fun getPersonFollowing(fragmentFollowing : FragmentFollowing){

        fragmentFollowing.binding.progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/{username}/following"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler(){

            override fun onSuccess(
                statusCode: Int,
                headers: Array<out cz.msebera.android.httpclient.Header>?,
                responseBody: ByteArray?
            ) {
                fragmentFollowing.binding.progressBar.visibility = View.INVISIBLE

                val listPersonFollowing = ArrayList<String>()
                val result = String(responseBody!!)

                try {
                    val jsonObject = JSONObject(result)
                    val dataArray = jsonObject.getJSONArray("items")
                    for (i in 0 until dataArray.length()){
                        val dataObject = dataArray.getJSONObject(i)
                        val username = dataObject.getString("login")
                        val followes_url = dataObject.getString("following_url")
                        listPersonFollowing.add("\nUsername : $username\nFollowing URL : $followes_url\n")
                    }
                    val adapter = FollowerAdapter(listPersonFollowing)
                    fragmentFollowing.binding.rvFollowing.adapter = adapter
                    fragmentFollowing.layoutManager = LinearLayoutManager(fragmentFollowing.activity)
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
                fragmentFollowing.binding.progressBar.visibility = View.INVISIBLE
            }
        })
    }
}