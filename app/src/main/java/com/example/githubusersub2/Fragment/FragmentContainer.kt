package com.example.githubusersub2.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentContainer
import com.example.githubuserapp.Adapter.SectionsPagerAdapter
import com.example.githubusersub2.databinding.FragmentContainerBinding
import com.google.android.material.tabs.TabLayoutMediator

class FragmentContainer : Fragment() {

    private var _binding : FragmentContainerBinding? = null
    private val binding get() = _binding!!
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreateView( inflater: LayoutInflater,  container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //sectionsPagerAdapter = SectionsPagerAdapter()

        with(binding){
            //viewPager.adapter = sectionsPagerAdapter

            TabLayoutMediator(tabs, viewPager){tab, position ->
                when(position){
                    0-> FragmentFollowers()
                    1-> FragmentFollowing()
                }
            }.attach()
        }
    }


}