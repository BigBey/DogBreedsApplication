package ru.android.dogbreedsapplication.ui.breedimages

import java.lang.Exception

sealed class SubbreedImagesModelState {
    abstract fun reduce(oldState: SubbreedImagesViewState): SubbreedImagesViewState

    object Init : SubbreedImagesModelState() {
        override fun reduce(oldState: SubbreedImagesViewState): SubbreedImagesViewState = SubbreedImagesViewState.init()
    }

    object SubbreedImagesLoading : SubbreedImagesModelState() {
        override fun reduce(oldState: SubbreedImagesViewState): SubbreedImagesViewState =
            SubbreedImagesViewState.subbreedImagesLoading()

    }

    class SubbreedImagesLoaded(private val subbreedImages: List<String>) : SubbreedImagesModelState() {
        override fun reduce(oldState: SubbreedImagesViewState): SubbreedImagesViewState =
            SubbreedImagesViewState.subbreedImagesLoaded(subbreedImages = subbreedImages)

    }

    class SubbreedImagesLoadingFailed(private val error: Exception) : SubbreedImagesModelState() {
        override fun reduce(oldState: SubbreedImagesViewState): SubbreedImagesViewState =
            SubbreedImagesViewState.subbreedImagesLoadingFailed(error)

    }

    object SubbreedImagesRefresherLoading : SubbreedImagesModelState() {
        override fun reduce(oldState: SubbreedImagesViewState): SubbreedImagesViewState =
            SubbreedImagesViewState.subbreedImagesRefreshingLoading(oldState.subbreedImages)

    }

    class SubbreedImagesRefresherLoadingFailed(private val error: Exception) : SubbreedImagesModelState() {
        override fun reduce(oldState: SubbreedImagesViewState): SubbreedImagesViewState =
            SubbreedImagesViewState.subbreedImagesRefreshingLoadingFailed(
                subbreedImages = oldState.subbreedImages,
                errorRefreshLoading = error
            )

    }

}