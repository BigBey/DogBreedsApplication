package ru.android.dogbreedsapplication.favorite_dogs_db

import androidx.recyclerview.widget.SortedList
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FavoriteDogImageDao {
    @Query("SELECT * FROM favoritedogimage")
    fun getAll(): Flowable<List<_root_ide_package_.ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage>>

    @Query("SELECT * FROM favoritedogimage WHERE dogImageLink = (:dogImageLink)")
    fun findDogImageByLink(dogImageLink: String) : Flowable<_root_ide_package_.ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage>

    @Query("SELECT dogImageLink FROM  favoritedogimage WHERE breed = :breed")
    fun findDogImagesByBreed(breed: String): Flowable<List<String>>

    @Query("SELECT DISTINCT breed FROM favoritedogimage")
    fun getAllBreeds(): Flowable<List<String>>

    @Insert
    fun insertAll(vararg dogImages: _root_ide_package_.ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage): Completable

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(dogImage: _root_ide_package_.ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage): Completable

    @Delete
    fun delete(dogImage: _root_ide_package_.ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage): Completable

    @Query("SELECT * FROM favoritedogimage")
    suspend fun getAllAsync(): List<_root_ide_package_.ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage>

    @Query("SELECT * FROM favoritedogimage WHERE dogImageLink = (:dogImageLink)")
    suspend fun findDogImageByLinkAsync(dogImageLink: String) : _root_ide_package_.ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage

    @Query("SELECT dogImageLink FROM  favoritedogimage WHERE breed = :breed")
    suspend fun findDogImagesByBreedAsync(breed: String): List<String>

    @Insert
    suspend fun insertAllAsync(vararg dogImages: _root_ide_package_.ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAsync(dogImage: _root_ide_package_.ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage)

    @Delete
    suspend fun deleteAsync(dogImage: _root_ide_package_.ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage)
}