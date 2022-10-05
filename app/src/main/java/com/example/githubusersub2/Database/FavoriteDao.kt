package com.example.githubusersub2.Database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(fav: FavoriteEntity)

    @Delete
    fun delete(fav: FavoriteEntity)

    @Query("SELECT  * from favorite")
    fun getAllFavorite(): LiveData<List<FavoriteEntity>>

    @Query("SELECT  * from favorite WHERE id = :id")
    fun getPersonFavoriteById(id: Int): LiveData<List<FavoriteEntity>>
}