package ru.android.dogbreedsapplication.ui.favorite_breed_images

import java.lang.Exception

class FavoriteBreedImagesViewState(
    val favoriteBreedImages: List<String>,
    val isFavoriteBreedImagesLoading: Boolean,
    val errorLoadingFavoriteBreedImages: Exception?,
    val isRefreshLoading: Boolean,
    val errorRefreshLoading: Exception?
) {
    companion object{
        fun init() = FavoriteBreedImagesViewState(
            favoriteBreedImages = emptyList(),
            isFavoriteBreedImagesLoading = false,
            errorLoadingFavoriteBreedImages = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun favoriteBreedImagesLoading() = FavoriteBreedImagesViewState(
            favoriteBreedImages = emptyList(),
            isFavoriteBreedImagesLoading = true,
            errorLoadingFavoriteBreedImages = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun favoriteBreedImagesLoaded(favoriteBreedImages: List<String>) = FavoriteBreedImagesViewState(
            favoriteBreedImages = favoriteBreedImages,
            isFavoriteBreedImagesLoading = false,
            errorLoadingFavoriteBreedImages = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun favoriteBreedImagesLoadingFailed(errorLoadingBreeds: Exception?) = FavoriteBreedImagesViewState(
            favoriteBreedImages = emptyList(),
            isFavoriteBreedImagesLoading = false,
            errorLoadingFavoriteBreedImages = errorLoadingBreeds,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun favoriteBreedImagesRefreshingLoading(favoriteBreedImages: List<String>) = FavoriteBreedImagesViewState(
            favoriteBreedImages = favoriteBreedImages,
            isFavoriteBreedImagesLoading = false,
            errorLoadingFavoriteBreedImages = null,
            isRefreshLoading = true,
            errorRefreshLoading = null
        )

        fun favoriteBreedImagesRefreshingLoadingFailed(favoriteBreedImages: List<String>, errorRefreshLoading: Exception?) = FavoriteBreedImagesViewState(
            favoriteBreedImages = favoriteBreedImages,
            isFavoriteBreedImagesLoading = false,
            errorLoadingFavoriteBreedImages = null,
            isRefreshLoading = false,
            errorRefreshLoading = errorRefreshLoading
        )
    }
}