package com.example.githubusersub2.Detail

import android.app.Application
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubusersub2.Adapter.SectionsPagerAdapter
import com.example.githubusersub2.Database.FavoriteEntity
import com.example.githubusersub2.Favorite.FavoriteViewModel
import com.example.githubusersub2.Fragment.FragmentFollowers
import com.example.githubusersub2.Fragment.FragmentFollowers.Companion.EXTRA_FRAGMENT
import com.example.githubusersub2.Fragment.FragmentFollowing
import com.example.githubusersub2.R
import com.example.githubusersub2.Response.PersonRespons
import com.example.githubusersub2.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class   DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    private val extraPerson : PersonRespons? by lazy {
        intent.extras?.getParcelable(EXTRA_PERSON)
    }
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailViewModel.initContext(this)
        extraPerson?.login?.let {
            clickFavBtn(it)
        }
        viewPager()
        dataSet()
    }

    private fun viewPager(){

        val sectionsPagerAdapter =
            extraPerson?.let { SectionsPagerAdapter(this, supportFragmentManager, it) }

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

    private fun clickFavBtn(username : String){
        val favorite = FavoriteEntity()
        val person = intent.getParcelableExtra<PersonRespons>(EXTRA_PERSON)
        favorite.login = username
        favorite.id = intent.getIntExtra(KEY_ID, 0)
        favorite.avatar_url = person?.avatarUrl

        detailViewModel.getFavoriteById(favorite.id!!)
            .observe(this@DetailActivity, { favList ->
                isFavorite = favList.isNotEmpty()

                binding.btnFav.imageTintList = if(favList.isEmpty()) {
                    ColorStateList.valueOf(Color.rgb(255, 255, 255))
                } else {
                    ColorStateList.valueOf(Color.rgb(255, 84, 105))
                }
            })

        with(binding){
            btnFav.apply {
                setOnClickListener{
                    if(isFavorite){
                        detailViewModel.delete(favorite)
                        makeText(this@DetailActivity,
                            "${favorite.login} telah terhapus dari data person favorite", Toast.LENGTH_LONG).show()
                    }else{
                        detailViewModel.insert(favorite)
                        makeText(this@DetailActivity,
                            "${favorite.login} telah ditambahkan ke data person favorite", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun showProgresBar(isLoading : Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_PERSON = "username"
        const val KEY_PERSON = "person"
        const val KEY_ID = "extra id"
    }
}