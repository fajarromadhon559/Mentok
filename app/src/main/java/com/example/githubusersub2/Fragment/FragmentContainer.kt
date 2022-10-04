package com.example.githubusersub2.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.githubusersub2.databinding.FragmentContainerBinding
import com.google.android.material.tabs.TabLayoutMediator

class FragmentContainer : Fragment() {

    private var _binding : FragmentContainerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView( inflater: LayoutInflater,  container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){

            TabLayoutMediator(tabs, viewPager){tab, position ->
                when(position){
                    0-> FragmentFollowers()
                    1-> FragmentFollowing()
                }
            }.attach()
        }
    }


}