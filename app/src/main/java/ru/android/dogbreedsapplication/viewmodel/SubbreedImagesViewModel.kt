package ru.android.dogbreedsapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import ru.bey_sviatoslav.android.dogbreedsapplication.repo.SubbreedImagesRepository
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breedimages.SubbreedImagesViewState

class SubbreedImagesViewModel: ViewModel() {
    private val breedImagesRepository = SubbreedImagesRepository.getInstance()

    private val _viewStateData = MediatorLiveData<SubbreedImagesViewState>()
    val viewStateData: LiveData<SubbreedImagesViewState>
        get() = _viewStateData

    init {
        _viewStateData.value = SubbreedImagesViewState.init()

        _viewStateData.addSource(breedImagesRepository.liveData){
            _viewStateData.value = it.reduce(_viewStateData.value!!)
        }
    }

    fun onRetry(breedname: String, subbreedname: String) {
        breedImagesRepository.getSubbreedImages(breedname, subbreedname)
    }

    fun onRefresh(breedname: String, subbreedname: String) {
        breedImagesRepository.getSubbreedImages(breedname, subbreedname, isRefresher = true)
    }
}