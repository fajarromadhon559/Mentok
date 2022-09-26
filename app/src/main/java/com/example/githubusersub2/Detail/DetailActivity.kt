package com.example.githubusersub2.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.Adapter.SectionsPagerAdapter
import com.example.githubuserapp.Response.PersonRespons
import com.example.githubusersub2.Fragment.FragmentFollowers
import com.example.githubusersub2.Fragment.FragmentFollowers.Companion.EXTRA_FRAGMENT
import com.example.githubusersub2.Fragment.FragmentFollowing
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

        viewPager()
        dataSet()
    }

    private fun viewPager(){

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        val fragmentFollowers = FragmentFollowers()
        val fragmentFollowing = FragmentFollowing()

        val person = intent.extras?.getParcelable<PersonRespons>(EXTRA_PERSON)
        val bundle = Bundle()
        bundle.putString(EXTRA_FRAGMENT, person?.login)

        fragmentFollowers.arguments = bundle
        fragmentFollowing.arguments = bundle

        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)

        supportActionBar?.elevation = 0f
    }

    private fun dataSet() {
        val person = intent.extras?.getParcelable<PersonRespons>(EXTRA_PERSON)

        if(person != null){
            detailViewModel.SetDetailPerson(this, person.login!!)
        }
        detailViewModel.detailLoading.observe(this, {
            showProgresBar(it)
        })

        detailViewModel.detailPerson.observe(this, {
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
    }
}