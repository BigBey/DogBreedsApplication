package ru.android.dogbreedsapplication.ui.subbreeds

import java.lang.Exception

class SubbreedsViewState(
    val subbreeds: List<String>,
    val isBreedsLoading: Boolean,
    val errorLoadingBreeds: Exception?,
    val isRefreshLoading: Boolean,
    val errorRefreshLoading: Exception?
) {
    companion object{
        fun init() = SubbreedsViewState(
            subbreeds = emptyList(),
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun subbreedsLoading() = SubbreedsViewState(
            subbreeds = emptyList(),
            isBreedsLoading = true,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun subbreedsLoaded(subbreeds: List<String>) = SubbreedsViewState(
            subbreeds = subbreeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun subbreedsLoadingFailed(errorLoadingBreeds: Exception?) = SubbreedsViewState(
            subbreeds = emptyList(),
            isBreedsLoading = false,
            errorLoadingBreeds = errorLoadingBreeds,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun subbreedsRefreshingLoading(subbreeds: List<String>) = SubbreedsViewState(
            subbreeds = subbreeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = true,
            errorRefreshLoading = null
        )

        fun subbreedsRefreshingLoadingFailed(subbreeds: List<String>, errorRefreshLoading: Exception?) = SubbreedsViewState(
            subbreeds = subbreeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = errorRefreshLoading
        )
    }
}