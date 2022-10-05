package com.example.githubusersub2.Favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersub2.Database.FavoriteEntity
import com.example.githubusersub2.Detail.DetailActivity
import com.example.githubusersub2.databinding.ActivityFavPersonBinding

class FavPerson : AppCompatActivity() {
    private var _binding : ActivityFavPersonBinding? = null
    private val binding get() = _binding
    private lateinit var favoriteViewModel : FavoriteViewModel
    private val adapter : FavoriteAdapter by lazy {
        FavoriteAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavPersonBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUpList()
        setUserFavorite()
    }

    private fun setUpList(){
        with(binding){
            val layoutManager = LinearLayoutManager(this@FavPerson)
            this?.rvFavPerson?.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this@FavPerson, layoutManager.orientation)
            this?.rvFavPerson?.addItemDecoration(itemDecoration)
            this?.rvFavPerson?.adapter = adapter
            adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback{
                override fun onItemClicked(favEntitiy : FavoriteEntity){
                    val i = Intent(this@FavPerson, DetailActivity::class.java)
                    i.putExtra(DetailActivity.EXTRA_PERSON, favEntitiy.login)
                    i.putExtra(DetailActivity.EXTRA_PERSON, favEntitiy.id)
                    startActivity(i)
                }
            })
        }
    }
    private fun setUserFavorite() {
        favoriteViewModel = obtainViewModel(this@FavPerson)
        favoriteViewModel.getAllFavorites().observe(this@FavPerson, { favList ->
            if (favList !=null){
                adapter.setListFavorite(favList)
            }
            if (favList.isEmpty()){
                showNoDataSaved(true)
            }
            else{
                showNoDataSaved(false)

            }
        })
    }
    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    private fun showNoDataSaved(isNoData: Boolean) {
        binding?.progressBar?.visibility = if (isNoData) View.VISIBLE else View.GONE
    }
}