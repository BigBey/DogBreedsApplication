package ru.android.dogbreedsapplication.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import ru.bey_sviatoslav.android.dogbreedsapplication.repo.FavoriteBreedImagesRepository
import ru.bey_sviatoslav.android.dogbreedsapplication.repo.FavoriteBreedsRepository
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.favorite_breed_images.FavoriteBreedImagesViewState
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.favourite_breeds.FavoriteBreedsViewState

class FavoriteBreedImagesViewModel : ViewModel(){
    private var favoriteBreedImagesRepository: FavoriteBreedImagesRepository? = null
    private var context: Context? = null
    private val _viewStateData = MediatorLiveData<FavoriteBreedImagesViewState>()

    val viewStateData: LiveData<FavoriteBreedImagesViewState>
        get() = _viewStateData

    init {
        _viewStateData.value = FavoriteBreedImagesViewState.init()
    }

    private fun setContext(context: Context){
        this.context = context
    }

    fun setFavoriteBreedImagesRepository(context: Context){
        setContext(context)
        favoriteBreedImagesRepository = FavoriteBreedImagesRepository.getInstance(context!!)
        _viewStateData.addSource(favoriteBreedImagesRepository!!.liveData){
            _viewStateData.value = it.reduce(_viewStateData.value!!)
        }
    }

    fun onRetry(breedname: String) {
        favoriteBreedImagesRepository!!.getBreedImages(breedname)
    }

    fun onRefresh(breedname: String) {
        favoriteBreedImagesRepository!!.getBreedImages(breedname, isRefresher = true)
    }
}