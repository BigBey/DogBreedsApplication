package ru.android.dogbreedsapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import ru.bey_sviatoslav.android.dogbreedsapplication.repo.BreedImagesRepository
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breedimages.BreedImagesViewState
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breedimages.SubbreedImagesViewState

class BreedImagesViewModel: ViewModel() {
    private val breedImagesRepository = BreedImagesRepository.getInstance()

    private val _viewStateData = MediatorLiveData<BreedImagesViewState>()
    val viewStateData: LiveData<BreedImagesViewState>
        get() = _viewStateData

    init {
        _viewStateData.value = BreedImagesViewState.init()

        _viewStateData.addSource(breedImagesRepository.liveData){
            _viewStateData.value = it.reduce(_viewStateData.value!!)
        }
    }

    fun onRetry(breedname: String) {
        breedImagesRepository.getBreedImages(breedname)
    }

    fun onRefresh(breedname: String) {
        breedImagesRepository.getBreedImages(breedname, isRefresher = true)
    }
}