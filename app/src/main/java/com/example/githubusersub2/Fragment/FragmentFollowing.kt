package com.example.githubusersub2.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.API_Network.ApiConfig
import com.example.githubuserapp.Response.PersonRespons
import com.example.githubusersub2.Adapter.FollowerAdapter
import com.example.githubusersub2.MainViewModel
import com.example.githubusersub2.R
import com.example.githubusersub2.databinding.FragmentFollowersBinding
import com.example.githubusersub2.databinding.FragmentFollowingBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentFollowing : Fragment() {

    private lateinit var binding : FragmentFollowingBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val person : String? = arguments?.getString(EXTRA_FRAGMENT)

        if (savedInstanceState == null) {
            mainViewModel.getPersonFollowing(requireActivity(), person.toString())
        }
        initObserver()
    }

    private fun initObserver() {
        mainViewModel.apply {
            loading.observe(requireActivity()) {
                showProgressBar(it)
            }

            person.observe(requireActivity()) {
                if (it != null) {
                    getPersonFollowing(it)
                }
            }

            error.observe(requireActivity()) {

            }
        }
    }


    private fun getPersonFollowing(data: ArrayList<PersonRespons>){

        binding.rvFollowing.apply {
            val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            val rvAdapter = FollowerAdapter()
            rvAdapter.addDataList(data)
            adapter = rvAdapter
        }

    }

    private fun showProgressBar(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_FRAGMENT = "EXTRA_FRAGMENT"
    }
}