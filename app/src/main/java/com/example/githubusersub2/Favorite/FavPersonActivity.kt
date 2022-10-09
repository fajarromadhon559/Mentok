package com.example.githubusersub2.Favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersub2.Adapter.FavoriteAdapter
import com.example.githubusersub2.Database.FavoriteEntity
import com.example.githubusersub2.Detail.DetailActivity
import com.example.githubusersub2.databinding.ActivityFavPersonBinding

class FavPersonActivity : AppCompatActivity() {

    private val binding: ActivityFavPersonBinding by lazy {
        ActivityFavPersonBinding.inflate(layoutInflater)
    }

    private lateinit var favoriteViewModel: FavoriteViewModel

    private val adapter: FavoriteAdapter by lazy {
        FavoriteAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpList()
        setUserFavorite()
    }

    private fun setUpList() {
        binding.apply {
            val layoutManager = LinearLayoutManager(this@FavPersonActivity)
            rvFavPerson.layoutManager = layoutManager
            val itemDecoration =
                DividerItemDecoration(this@FavPersonActivity, layoutManager.orientation)
            rvFavPerson.addItemDecoration(itemDecoration)
            rvFavPerson.adapter = adapter
            adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
                override fun onItemClicked(favEntitiy: FavoriteEntity) {
                    val i = Intent(this@FavPersonActivity, DetailActivity::class.java)
                    i.putExtra(DetailActivity.KEY_PERSON_FAVORITE, favEntitiy.login)
                    i.putExtra(DetailActivity.KEY_ID, favEntitiy.id)
                    startActivity(i)
                }
            })
        }
    }

    private fun setUserFavorite() {
        favoriteViewModel = obtainViewModel(this@FavPersonActivity)
        favoriteViewModel.getAllFavorites().observe(this@FavPersonActivity) { favList ->

            Log.d("FavPersonActivity", "favList: $favList")

            if (favList != null) {
                adapter.setListFavorite(favList)
            }
            if (favList.isEmpty()) {
                showNoDataSaved(true)
            } else {
                showNoDataSaved(false)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    private fun showNoDataSaved(isNoData: Boolean) {
        binding.progressBar.visibility = if (isNoData) View.VISIBLE else View.GONE
    }
}