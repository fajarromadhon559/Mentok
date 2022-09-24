package com.example.githubusersub2.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.API_Network.ApiConfig
import com.example.githubuserapp.Response.PersonRespons
import com.example.githubusersub2.Adapter.FollowerAdapter
import com.example.githubusersub2.R
import com.example.githubusersub2.databinding.FragmentFollowersBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

        getPersonFollowers()
    }

    private fun getPersonFollowers(){
        val login = String()
        val fragmentFollowers = FragmentFollowers()
        fragmentFollowers.binding.progressBar.visibility = View.VISIBLE
        val client = ApiConfig.getApiService(requireActivity()).getPersonFollowers(login)
        client.enqueue(object : Callback<List<PersonRespons>>{

            override fun onResponse(
                call: Call<List<PersonRespons>>,
                response: Response<List<PersonRespons>>
            ) {
                fragmentFollowers.binding.progressBar.visibility = View.INVISIBLE

                val listPersonFollower = ArrayList<String>()
                val result = String()

                try {
                    val jsonObject = JSONObject(result)
                    val dataArray = jsonObject.getJSONArray("items")
                    for (i in 0 until dataArray.length()){
                        val dataObject = dataArray.getJSONObject(i)
                        val username = dataObject.getString("login")
                        val followes_url = dataObject.getString("followes_url")
                        listPersonFollower.add("\nUsername : $username\nFollowers URL : $followes_url\n")
                    }
                    val adapter = FollowerAdapter()
                    fragmentFollowers.binding.rvFollowers.adapter = adapter
                    fragmentFollowers.layoutManager = LinearLayoutManager(fragmentFollowers.activity)
                }
                catch (e : Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<List<PersonRespons>>, t: Throwable) {
                fragmentFollowers.binding.progressBar.visibility = View.INVISIBLE
            }

        })
    }
}