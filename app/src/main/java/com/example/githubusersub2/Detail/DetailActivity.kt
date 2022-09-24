package com.example.githubusersub2.Detail

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapp.API_Network.ApiConfig
import com.example.githubuserapp.Adapter.SectionsPagerAdapter
import com.example.githubuserapp.Response.PersonRespons
import com.example.githubusersub2.Fragment.FragmentContainer
import com.example.githubusersub2.R
import com.example.githubusersub2.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            val fragment = FragmentContainer()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, FragmentContainer::class.java.simpleName)
                .commit()
        }
        showProgresBar(true)
        viewPager()
        dataSet()
    }

    private fun viewPager(){
        val username = intent.getStringExtra(EXTRA_PERSON)
        val bundle = Bundle()
        val sectionsPagerAdapter = SectionsPagerAdapter(bundle, FragmentContainer())
        bundle.putString(EXTRA_PERSON, username)
        val viewPager: ViewPager2 = binding.fragmentContainer.findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.fragmentContainer.findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }

    private fun dataSet() {
        val username = intent.getStringExtra(EXTRA_PERSON)
        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        if(username != null){
            viewModel.SetDetailPerson(this,username)
        }
        viewModel.detailPerson.observe(this, {
            if (it != null){
                Glide.with(this@DetailActivity)
                    .load(it.avatarUrl)
                    .centerCrop()
                    .into(binding.imgUser)
                binding.tvDetailUsername.text = it.login
                binding.tvDetailName.text = it.name
                binding.tvDetailCompany.text = it.company
                binding.tvDetailFollower.text = it.followers
                binding.tvDetailFollowing.text = it.following
                binding.tvDetailLocation.text = it.location
                binding.tvDetailRepo.text = it.publicRepo
            }
        })

    }

    private fun showProgresBar(isLoading : Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_PERSON = "extra_person"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}