package com.example.githubusersub2.Detail

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapp.Response.PersonRespons
import com.example.githubusersub2.Fragment.FragmentContainer
import com.example.githubusersub2.R
import com.example.githubusersub2.databinding.ActivityDetailBinding

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

    }

    private fun setTabLayout(personRespons: PersonRespons?){
        if (personRespons != null){
            with(binding){
                detailLayout.visibility = View.VISIBLE
                imgUser.visibility = View.VISIBLE
                Glide.with(root)
                    .load(personRespons.avatarUrl)
                    .apply {
                        RequestOptions.placeholderOf(R.drawable.ic_baseline_refresh_24)
                            .error(R.drawable.ic_baseline_broken_image_24)
                    }
                    .circleCrop()
                    .into(binding.imgUser)
                tvDetailUsername.visibility = View.VISIBLE
                tvDetailUsername.text = personRespons.login
                tvDetailName.visibility = View.VISIBLE
                tvDetailName.text = personRespons.name
                if(personRespons.company != null){
                    tvDetailCompany.visibility = View.VISIBLE
                    tvDetailCompany.text = personRespons.company
                }else{
                    tvDetailCompany.visibility = View.GONE
                }
                if(personRespons.company != null){
                    tvDetailCompany.visibility = View.VISIBLE
                    tvDetailCompany.text = personRespons.company
                }else{
                    tvDetailCompany.visibility = View.GONE
                }
                if(personRespons.location != null){
                    tvDetailLocation.visibility = View.VISIBLE
                    tvDetailLocation.text = personRespons.company
                }else{
                    tvDetailLocation.visibility = View.GONE
                }
                if(personRespons.publicRepo != null){
                    tvDetailRepo.visibility = View.VISIBLE
                    tvDetailRepo.text = personRespons.company
                }else{
                    tvDetailRepo.visibility = View.GONE
                }
                if(personRespons.followers != null){
                    tvDetailFollower.visibility = View.VISIBLE
                    tvDetailFollower.text = personRespons.company
                }else{
                    tvDetailFollower.visibility = View.GONE
                }
                if(personRespons.following != null){
                    tvDetailFollowing.visibility = View.VISIBLE
                    tvDetailFollowing.text = personRespons.company
                }else{
                    tvDetailFollowing.visibility = View.GONE
                }
            }
        }
    }

    private fun dataSet(personRespons: PersonRespons?) {
        if (personRespons != null) {
            with(binding) {
                detailLayout.visibility = View.VISIBLE
                imgUser.visibility = View.VISIBLE
                Glide.with(root)
                    .load(personRespons.avatarUrl)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_baseline_refresh_24)
                            .error(R.drawable.ic_baseline_broken_image_24)
                    )
                    .circleCrop()
                    .into(binding.imgUser)
                tvDetailUsername.visibility = View.VISIBLE
                tvDetailName.visibility = View.VISIBLE
                tvDetailUsername.text = personRespons.login
                tvDetailName.text = personRespons.name
                if (personRespons.company != null) {
                    tvDetailCompany.visibility = View.VISIBLE
                    tvDetailCompany.text = personRespons.company
                } else {
                    tvDetailCompany.visibility = View.GONE
                }
                if (personRespons.location != null) {
                    tvDetailLocation.visibility = View.VISIBLE
                    tvDetailLocation.text = personRespons.location
                } else {
                    tvDetailLocation.visibility = View.GONE
                }
                if (personRespons.publicRepo != null) {
                    tvDetailRepo.visibility = View.VISIBLE
                    tvDetailRepo.text = personRespons.location
                } else {
                    tvDetailRepo.visibility = View.GONE
                }
                if (personRespons.followers != null) {
                    tvDetailFollower.visibility = View.VISIBLE
                    tvDetailFollower.text = personRespons.location
                } else {
                    tvDetailFollower.visibility = View.GONE
                }
                if (personRespons.following != null) {
                    tvDetailFollowing.visibility = View.VISIBLE
                    tvDetailFollowing.text = personRespons.location
                } else {
                    tvDetailFollowing.visibility = View.GONE
                }
            }
        } else {
            Log.i(ContentValues.TAG, "Set data is eror")
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
        const val EXTRA_PERSON = "extra_person"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}