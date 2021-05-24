package ru.android.dogbreedsapplication.ui.breedimages

import java.lang.Exception

class BreedImagesViewState(
    val breedImages: List<String>,
    val isBreedImagesLoading: Boolean,
    val errorLoadingBreedImages: Exception?,
    val isRefreshLoading: Boolean,
    val errorRefreshLoading: Exception?
) {
    companion object{
        fun init() = BreedImagesViewState(
            breedImages = emptyList(),
            isBreedImagesLoading = false,
            errorLoadingBreedImages = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun breedImagesLoading() = BreedImagesViewState(
            breedImages = emptyList(),
            isBreedImagesLoading = true,
            errorLoadingBreedImages = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun breedImagesLoaded(breedImages: List<String>) = BreedImagesViewState(
            breedImages = breedImages,
            isBreedImagesLoading = false,
            errorLoadingBreedImages = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun breedImagesLoadingFailed(errorLoadingBreeds: Exception?) = BreedImagesViewState(
            breedImages = emptyList(),
            isBreedImagesLoading = false,
            errorLoadingBreedImages = errorLoadingBreeds,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun breedImagesRefreshingLoading(breedImages: List<String>) = BreedImagesViewState(
            breedImages = breedImages,
            isBreedImagesLoading = false,
            errorLoadingBreedImages = null,
            isRefreshLoading = true,
            errorRefreshLoading = null
        )

        fun breedImagesRefreshingLoadingFailed(breedImages: List<String>, errorRefreshLoading: Exception?) = BreedImagesViewState(
            breedImages = breedImages,
            isBreedImagesLoading = false,
            errorLoadingBreedImages = null,
            isRefreshLoading = false,
            errorRefreshLoading = errorRefreshLoading
        )
    }
}