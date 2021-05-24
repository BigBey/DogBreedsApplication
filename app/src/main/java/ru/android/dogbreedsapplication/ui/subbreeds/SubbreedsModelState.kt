package ru.android.dogbreedsapplication.ui.subbreeds

import java.lang.Exception

sealed class SubbreedsModelState {
    abstract fun reduce(oldState: SubbreedsViewState): SubbreedsViewState

    object Init : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState = SubbreedsViewState.init()
    }

    object SubbreedsLoading : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState =
            SubbreedsViewState.subbreedsLoading()

    }

    class SubbreedsLoaded(private val subbreeds: List<String>) : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState =
            SubbreedsViewState.subbreedsLoaded(subbreeds = subbreeds)

    }

    class SubbreedsLoadingFailed(private val error: Exception) : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState =
            SubbreedsViewState.subbreedsLoadingFailed(errorLoadingBreeds = error)

    }

    object SubbreedsRefresherLoading : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState =
            SubbreedsViewState.subbreedsRefreshingLoading(oldState.subbreeds)

    }

    class SubbreedsRefresherLoadingFailed(private val error: Exception) : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState =
            SubbreedsViewState.subbreedsRefreshingLoadingFailed(
                subbreeds = oldState.subbreeds,
                errorRefreshLoading = error
            )

    }

}