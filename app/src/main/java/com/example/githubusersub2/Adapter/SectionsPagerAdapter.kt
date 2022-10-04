package com.example.githubusersub2.Adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.githubusersub2.Fragment.FragmentFollowers
import com.example.githubusersub2.Fragment.FragmentFollowing
import com.example.githubusersub2.R

class SectionsPagerAdapter(private val context: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0 -> fragment = FragmentFollowers()
            1 -> fragment = FragmentFollowing()
        }
        return fragment as Fragment
    }

    override fun getCount(): Int = 2


    override fun getPageTitle(position: Int): CharSequence = context.resources.getString(TAB_TITLES[position])


    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}