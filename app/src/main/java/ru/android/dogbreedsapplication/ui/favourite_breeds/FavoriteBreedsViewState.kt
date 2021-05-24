package ru.android.dogbreedsapplication.ui.favourite_breeds

import java.lang.Exception

class FavoriteBreedsViewState(
    val favoriteBreeds: List<String>,
    val isFavoriteBreedsLoading: Boolean,
    val errorLoadingFavoriteBreeds: Exception?,
    val isRefreshLoading: Boolean,
    val errorRefreshLoading: Exception?
) {
    companion object{
        fun init() = FavoriteBreedsViewState(
            favoriteBreeds = emptyList(),
            isFavoriteBreedsLoading = false,
            errorLoadingFavoriteBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun favoriteBreedsLoading() = FavoriteBreedsViewState(
            favoriteBreeds = emptyList(),
            isFavoriteBreedsLoading = true,
            errorLoadingFavoriteBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun favoriteBreedsLoaded(favoriteBreeds: List<String>) = FavoriteBreedsViewState(
            favoriteBreeds = favoriteBreeds,
            isFavoriteBreedsLoading = false,
            errorLoadingFavoriteBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun favoriteBreedsLoadingFailed(errorLoadingBreeds: Exception?) = FavoriteBreedsViewState(
            favoriteBreeds = emptyList(),
            isFavoriteBreedsLoading = false,
            errorLoadingFavoriteBreeds = errorLoadingBreeds,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun favoriteBreedsRefreshingLoading(favoriteBreeds: List<String>) = FavoriteBreedsViewState(
            favoriteBreeds = favoriteBreeds,
            isFavoriteBreedsLoading = false,
            errorLoadingFavoriteBreeds = null,
            isRefreshLoading = true,
            errorRefreshLoading = null
        )

        fun favoriteBreedsRefreshingLoadingFailed(favoriteBreeds: List<String>, errorRefreshLoading: Exception?) = FavoriteBreedsViewState(
            favoriteBreeds = favoriteBreeds,
            isFavoriteBreedsLoading = false,
            errorLoadingFavoriteBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = errorRefreshLoading
        )
    }
}