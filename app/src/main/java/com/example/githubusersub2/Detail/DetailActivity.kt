package com.example.githubusersub2.Detail

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubusersub2.Adapter.SectionsPagerAdapter
import com.example.githubusersub2.Database.FavoriteEntity
import com.example.githubusersub2.Response.PersonRespons
import com.example.githubusersub2.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val detailViewModel by viewModels<DetailViewModel>()

    private val extraPerson: PersonRespons? by lazy {
        intent.extras?.getParcelable(EXTRA_PERSON)
    }

    private val extraId: Int by lazy {
        intent.getIntExtra(KEY_ID, 0)
    }

    private val extraFavoritePerson: String by lazy {
        intent.getStringExtra(KEY_PERSON_FAVORITE) ?: ""
    }

    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        detailViewModel.initContext(this)

        val favorite = FavoriteEntity().apply {
            login = extraPerson?.login
            id = extraId
            avatar_url = extraPerson?.avatarUrl
        }

        initView(favorite)
        initObserver(favorite)
    }

    private fun initView(favorite: FavoriteEntity) {

        supportActionBar?.elevation = 0f

        binding.apply {
            btnFav.setOnClickListener {
                if (isFavorite) {
                    detailViewModel.delete(favorite)
                    makeText(
                        this@DetailActivity,
                        "${favorite.login} telah terhapus dari data person favorite",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    detailViewModel.insert(favorite)
                    makeText(
                        this@DetailActivity,
                        "${favorite.login} telah ditambahkan ke data person favorite",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }

    }

    private fun initObserver(favorite: FavoriteEntity) {

        detailViewModel.apply {

            val payload = if (intent.hasExtra(EXTRA_PERSON)) {
                extraPerson?.login.toString()
            } else {
                extraFavoritePerson
            }

            getDetailPerson(this@DetailActivity, payload)

            getFavoriteById(favorite.id!!)
                .observe(this@DetailActivity) { favList ->
                    isFavorite = favList.isNotEmpty()
                    binding.btnFav.imageTintList = if (favList.isEmpty()) {
                        ColorStateList.valueOf(Color.rgb(255, 255, 255))
                    } else {
                        ColorStateList.valueOf(Color.rgb(255, 84, 105))
                    }
                }

            detailLoading.observe(this@DetailActivity) {
                showProgresBar(it)
            }

            detailPerson.observe(this@DetailActivity) {
                populateData(it)
            }

        }

    }

    private fun populateData(it: PersonRespons) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(it.avatarUrl)
                .centerCrop()
                .into(imgUser)
            tvDetailUsername.text = it.login
            tvDetailName.text = it.name
            tvDetailCompany.text = it.company
            tvDetailFollower.text = it.followers
            tvDetailFollowing.text = it.following
            tvDetailLocation.text = it.location
            tvDetailRepo.text = it.publicRepo

            val sectionsPagerAdapter = SectionsPagerAdapter(
                this@DetailActivity,
                supportFragmentManager,
                it
            )

            viewPager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    private fun showProgresBar(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_PERSON = "username"
        const val KEY_PERSON_FAVORITE = "favorite_person"
        const val KEY_ID = "extra id"
    }

}