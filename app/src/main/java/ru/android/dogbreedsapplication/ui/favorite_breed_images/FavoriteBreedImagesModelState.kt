package ru.android.dogbreedsapplication.ui.favorite_breed_images

import java.lang.Exception

sealed class FavoriteBreedImagesModelState {
    abstract fun reduce(oldState: FavoriteBreedImagesViewState): FavoriteBreedImagesViewState

    object Init : FavoriteBreedImagesModelState() {
        override fun reduce(oldState: FavoriteBreedImagesViewState): FavoriteBreedImagesViewState = FavoriteBreedImagesViewState.init()
    }

    object FavoriteBreedImagesLoading : FavoriteBreedImagesModelState() {
        override fun reduce(oldState: FavoriteBreedImagesViewState): FavoriteBreedImagesViewState =
            FavoriteBreedImagesViewState.favoriteBreedImagesLoading()

    }

    class FavoriteBreedImagesLoaded(private val favoriteBreedImages: List<String>) : FavoriteBreedImagesModelState() {
        override fun reduce(oldState: FavoriteBreedImagesViewState): FavoriteBreedImagesViewState =
            FavoriteBreedImagesViewState.favoriteBreedImagesLoaded(favoriteBreedImages = favoriteBreedImages)

    }

    class FavoriteBreedImagesLoadingFailed(private val error: Exception) : FavoriteBreedImagesModelState() {
        override fun reduce(oldState: FavoriteBreedImagesViewState): FavoriteBreedImagesViewState =
            FavoriteBreedImagesViewState.favoriteBreedImagesLoadingFailed(error)

    }

    object FavoriteBreedImagesRefresherLoading : FavoriteBreedImagesModelState() {
        override fun reduce(oldState: FavoriteBreedImagesViewState): FavoriteBreedImagesViewState =
            FavoriteBreedImagesViewState.favoriteBreedImagesRefreshingLoading(oldState.favoriteBreedImages)

    }

    class FavoriteBreedImagesRefresherLoadingFailed(private val error: Exception) : FavoriteBreedImagesModelState() {
        override fun reduce(oldState: FavoriteBreedImagesViewState): FavoriteBreedImagesViewState =
            FavoriteBreedImagesViewState.favoriteBreedImagesRefreshingLoadingFailed(
                favoriteBreedImages = oldState.favoriteBreedImages,
                errorRefreshLoading = error
            )

    }

}