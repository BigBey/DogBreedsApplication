package ru.android.dogbreedsapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import ru.bey_sviatoslav.android.dogbreedsapplication.repo.BreedsRepository
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds.BreedsViewState

class BreedsViewModel : ViewModel() {
    private val breedsRepository = BreedsRepository.getInstance()

    private val _viewStateData = MediatorLiveData<BreedsViewState>()
    val viewStateData: LiveData<BreedsViewState>
        get() = _viewStateData

    init {
        _viewStateData.value = BreedsViewState.init()

        _viewStateData.addSource(breedsRepository.liveData){
            _viewStateData.value = it.reduce(_viewStateData.value!!)
        }

        breedsRepository.getAllBreeds()
    }

    fun onRetry() {
        breedsRepository.getAllBreeds()
    }

    fun onRefresh() {
        breedsRepository.getAllBreeds(isRefresher = true)
    }
}