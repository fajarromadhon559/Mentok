package com.example.githubusersub2.Favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubusersub2.Database.FavoriteEntity
import com.example.githubusersub2.Repository.FavoriteRepository

class FavoriteViewModel(app : Application) : ViewModel() {
    private val mFavRepository : FavoriteRepository = FavoriteRepository(app)
    fun getAllFavorites() : LiveData<List<FavoriteEntity>> = mFavRepository.getAllFavorites()
}