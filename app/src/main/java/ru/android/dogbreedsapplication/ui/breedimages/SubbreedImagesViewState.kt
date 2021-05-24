package ru.android.dogbreedsapplication.ui.breedimages

import java.lang.Exception

class SubbreedImagesViewState(
    val subbreedImages: List<String>,
    val isSubbreedImagesLoading: Boolean,
    val errorLoadingSubbreedImages: Exception?,
    val isRefreshLoading: Boolean,
    val errorRefreshLoading: Exception?
) {
    companion object{
        fun init() = SubbreedImagesViewState(
            subbreedImages = emptyList(),
            isSubbreedImagesLoading = false,
            errorLoadingSubbreedImages = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun subbreedImagesLoading() = SubbreedImagesViewState(
            subbreedImages = emptyList(),
            isSubbreedImagesLoading = true,
            errorLoadingSubbreedImages = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun subbreedImagesLoaded(subbreedImages: List<String>) = SubbreedImagesViewState(
            subbreedImages = subbreedImages,
            isSubbreedImagesLoading = false,
            errorLoadingSubbreedImages = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun subbreedImagesLoadingFailed(errorLoadingBreeds: Exception?) = SubbreedImagesViewState(
            subbreedImages = emptyList(),
            isSubbreedImagesLoading = false,
            errorLoadingSubbreedImages = errorLoadingBreeds,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun subbreedImagesRefreshingLoading(subbreedImages: List<String>) = SubbreedImagesViewState(
            subbreedImages = subbreedImages,
            isSubbreedImagesLoading = false,
            errorLoadingSubbreedImages = null,
            isRefreshLoading = true,
            errorRefreshLoading = null
        )

        fun subbreedImagesRefreshingLoadingFailed(subbreedImages: List<String>, errorRefreshLoading: Exception?) = SubbreedImagesViewState(
            subbreedImages = subbreedImages,
            isSubbreedImagesLoading = false,
            errorLoadingSubbreedImages = null,
            isRefreshLoading = false,
            errorRefreshLoading = errorRefreshLoading
        )
    }
}