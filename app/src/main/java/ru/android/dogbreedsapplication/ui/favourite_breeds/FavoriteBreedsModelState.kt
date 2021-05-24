package ru.android.dogbreedsapplication.ui.favourite_breeds

import java.lang.Exception

sealed class FavoriteBreedsModelState {
    abstract fun reduce(oldState: FavoriteBreedsViewState): FavoriteBreedsViewState

    object Init : FavoriteBreedsModelState() {
        override fun reduce(oldState: FavoriteBreedsViewState): FavoriteBreedsViewState = FavoriteBreedsViewState.init()
    }

    object FavoriteBreedsLoading : FavoriteBreedsModelState() {
        override fun reduce(oldState: FavoriteBreedsViewState): FavoriteBreedsViewState =
            FavoriteBreedsViewState.favoriteBreedsLoading()

    }

    class FavoriteBreedsLoaded(private val favoriteBreeds: List<String>) : FavoriteBreedsModelState() {
        override fun reduce(oldState: FavoriteBreedsViewState): FavoriteBreedsViewState =
            FavoriteBreedsViewState.favoriteBreedsLoaded(favoriteBreeds = favoriteBreeds)

    }

    class FavoriteBreedsLoadingFailed(private val error: Exception) : FavoriteBreedsModelState() {
        override fun reduce(oldState: FavoriteBreedsViewState): FavoriteBreedsViewState =
            FavoriteBreedsViewState.favoriteBreedsLoadingFailed(errorLoadingBreeds = error)

    }

    object FavoriteBreedsRefresherLoading : FavoriteBreedsModelState() {
        override fun reduce(oldState: FavoriteBreedsViewState): FavoriteBreedsViewState =
            FavoriteBreedsViewState.favoriteBreedsRefreshingLoading(oldState.favoriteBreeds)

    }

    class FavoriteBreedsRefresherLoadingFailed(private val error: Exception) : FavoriteBreedsModelState() {
        override fun reduce(oldState: FavoriteBreedsViewState): FavoriteBreedsViewState =
            FavoriteBreedsViewState.favoriteBreedsRefreshingLoadingFailed(
                favoriteBreeds = oldState.favoriteBreeds,
                errorRefreshLoading = error
            )

    }

}