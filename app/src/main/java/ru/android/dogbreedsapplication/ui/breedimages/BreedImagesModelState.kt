package ru.android.dogbreedsapplication.ui.breedimages

import java.lang.Exception

sealed class BreedImagesModelState {
    abstract fun reduce(oldState: BreedImagesViewState): BreedImagesViewState

    object Init : BreedImagesModelState() {
        override fun reduce(oldState: BreedImagesViewState): BreedImagesViewState = BreedImagesViewState.init()
    }

    object BreedImagesLoading : BreedImagesModelState() {
        override fun reduce(oldState: BreedImagesViewState): BreedImagesViewState =
            BreedImagesViewState.breedImagesLoading()

    }

    class BreedImagesLoaded(private val breedImages: List<String>) : BreedImagesModelState() {
        override fun reduce(oldState: BreedImagesViewState): BreedImagesViewState =
            BreedImagesViewState.breedImagesLoaded(breedImages = breedImages)

    }

    class BreedImagesLoadingFailed(private val error: Exception) : BreedImagesModelState() {
        override fun reduce(oldState: BreedImagesViewState): BreedImagesViewState =
            BreedImagesViewState.breedImagesLoadingFailed(error)

    }

    object BreedImagesRefresherLoading : BreedImagesModelState() {
        override fun reduce(oldState: BreedImagesViewState): BreedImagesViewState =
            BreedImagesViewState.breedImagesRefreshingLoading(oldState.breedImages)

    }

    class BreedImagesRefresherLoadingFailed(private val error: Exception) : BreedImagesModelState() {
        override fun reduce(oldState: BreedImagesViewState): BreedImagesViewState =
            BreedImagesViewState.breedImagesRefreshingLoadingFailed(
                breedImages = oldState.breedImages,
                errorRefreshLoading = error
            )

    }

}