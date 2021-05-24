package ru.android.dogbreedsapplication.ui.breeds

import java.lang.Exception

class BreedsViewState(
    val breeds: Map<String, List<String>>,
    val isBreedsLoading: Boolean,
    val errorLoadingBreeds: Exception?,
    val isRefreshLoading: Boolean,
    val errorRefreshLoading: Exception?
) {
    companion object{
        fun init() = BreedsViewState(
            breeds = emptyMap(),
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun breedsLoading() = BreedsViewState(
            breeds = emptyMap(),
            isBreedsLoading = true,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun breedsLoaded(breeds: Map<String, List<String>>) = BreedsViewState(
            breeds = breeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun breedsLoadingFailed(errorLoadingBreeds: Exception?) = BreedsViewState(
            breeds = emptyMap(),
            isBreedsLoading = false,
            errorLoadingBreeds = errorLoadingBreeds,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun breedsRefreshingLoading(breeds: Map<String, List<String>>) = BreedsViewState(
            breeds = breeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = true,
            errorRefreshLoading = null
        )

        fun breedsRefreshingLoadingFailed(breeds: Map<String, List<String>>, errorRefreshLoading: Exception?) = BreedsViewState(
            breeds = breeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = errorRefreshLoading
        )
    }
}