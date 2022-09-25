package com.example.githubuserapp.Adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubusersub2.Fragment.FragmentContainer
import com.example.githubusersub2.Fragment.FragmentFollowers
import com.example.githubusersub2.Fragment.FragmentFollowing

class SectionsPagerAdapter(fm: FragmentContainer)
    : FragmentStateAdapter(fm){

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