package ru.android.dogbreedsapplication.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import ru.bey_sviatoslav.android.dogbreedsapplication.repo.FavoriteBreedsRepository
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.favourite_breeds.FavoriteBreedsViewState

class FavoriteBreedsViewModel : ViewModel() {
    private var favoriteBreedsRepository: FavoriteBreedsRepository? = null
    private var context: Context? = null
    private val _viewStateData = MediatorLiveData<FavoriteBreedsViewState>()

    val viewStateData: LiveData<FavoriteBreedsViewState>
        get() = _viewStateData

    init {
        _viewStateData.value = FavoriteBreedsViewState.init()
    }

    private fun setContext(context: Context){
        this.context = context
    }

    fun setFavoriteBreedsRepository(context: Context){
        setContext(context)
        favoriteBreedsRepository = FavoriteBreedsRepository.getInstance(context!!)
        _viewStateData.addSource(favoriteBreedsRepository!!.liveData){
            _viewStateData.value = it.reduce(_viewStateData.value!!)
        }
        onRetry()
    }

    fun onRetry() {
        favoriteBreedsRepository!!.getBreeds()
    }

    fun onRefresh() {
        favoriteBreedsRepository!!.getBreeds(isRefresher = true)
    }
}