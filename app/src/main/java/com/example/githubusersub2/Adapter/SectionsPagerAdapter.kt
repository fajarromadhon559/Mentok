package com.example.githubuserapp.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubusersub2.FragmentContainer
import com.example.githubusersub2.FragmentFollowers
import com.example.githubusersub2.FragmentFollowing

class SectionsPagerAdapter(fragment: FragmentContainer)
    : FragmentStateAdapter(fragment){

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0 -> fragment = FragmentFollowers()
            1 -> fragment = FragmentFollowing()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int = 2
}