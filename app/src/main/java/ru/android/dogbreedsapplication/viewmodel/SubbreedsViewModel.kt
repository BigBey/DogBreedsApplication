package ru.android.dogbreedsapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import ru.bey_sviatoslav.android.dogbreedsapplication.repo.SubbreedsRepository
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.subbreeds.SubbreedsViewState

class SubbreedsViewModel: ViewModel() {
    private val subbreedsRepository = SubbreedsRepository.getInstance()

    private val _viewStateData = MediatorLiveData<SubbreedsViewState>()
    val viewStateData: LiveData<SubbreedsViewState>
        get() = _viewStateData

    init {
        _viewStateData.value = SubbreedsViewState.init()

        _viewStateData.addSource(subbreedsRepository.liveData){
            _viewStateData.value = it.reduce(_viewStateData.value!!)
        }
    }

    fun onRetry(breedname: String) {
        subbreedsRepository.getSubbreeds(breedname)
    }

    fun onRefresh(breedname: String) {
        subbreedsRepository.getSubbreeds(breedname, isRefresher = true)
    }
}