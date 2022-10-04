package com.example.githubusersub2.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersub2.Adapter.FollowerAdapter
import com.example.githubusersub2.Adapter.OnItemClickCallback
import com.example.githubusersub2.Detail.DetailActivity
import com.example.githubusersub2.Main.MainViewModel
import com.example.githubusersub2.Response.PersonRespons
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
            val rvAdapter = FollowerAdapter(object : OnItemClickCallback {
                override fun onItemClicked(person: PersonRespons) {
                    val i = Intent(requireContext(), DetailActivity::class.java)
                    i.putExtra(DetailActivity.EXTRA_PERSON, person)
                    startActivity(i)
                }

            })
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