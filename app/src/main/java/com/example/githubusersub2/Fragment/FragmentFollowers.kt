package com.example.githubusersub2.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.Response.PersonRespons
import com.example.githubusersub2.Adapter.FollowerAdapter
import com.example.githubusersub2.Main.MainViewModel
import com.example.githubusersub2.databinding.FragmentFollowersBinding

class FragmentFollowers : Fragment() {

    private lateinit var binding : FragmentFollowersBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val person : String? = arguments?.getString(EXTRA_FRAGMENT)

        if (savedInstanceState == null) {
            mainViewModel.getPersonFollowers(requireActivity(), person.toString())
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
                    getPersonFollowers(it)
                }
            }

            error.observe(requireActivity()) {

            }
        }
    }


    private fun getPersonFollowers(data: ArrayList<PersonRespons>){

        binding.rvFollowers.apply {
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