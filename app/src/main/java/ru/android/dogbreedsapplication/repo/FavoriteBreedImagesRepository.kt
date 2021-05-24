package ru.android.dogbreedsapplication.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.bey_sviatoslav.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImagesDatabase
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.favorite_breed_images.FavoriteBreedImagesModelState
import java.lang.Exception

const val TAGFavoriteBreedImages = "FavoriteBreedImages"


class FavoriteBreedImagesRepository(private val favoriteDogImagesDb: FavoriteDogImagesDatabase) {
    protected val compositeDisposable = CompositeDisposable()
    private val _liveData = MutableLiveData<FavoriteBreedImagesModelState>()
    val liveData: LiveData<FavoriteBreedImagesModelState>
        get() = _liveData

    init {
        clear()
    }

    fun getBreedImages(breedname: String, isRefresher: Boolean = false) {
        _liveData.value =
            if (isRefresher){
                FavoriteBreedImagesModelState.FavoriteBreedImagesRefresherLoading
                Log.d(TAGFavoriteBreedImages,"Включен refresher")

            }
            else{
                FavoriteBreedImagesModelState.FavoriteBreedImagesLoading
                Log.d(TAGFavoriteBreedImages,"Выключен refresher")

            }

        favoriteDogImagesDb.favoriteDogImageDao().findDogImagesByBreed(breedname)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                _liveData.postValue(FavoriteBreedImagesModelState.FavoriteBreedImagesLoaded(favoriteBreedImages = it))
            }, {
                _liveData.postValue(
                    if (isRefresher) {
                        FavoriteBreedImagesModelState.FavoriteBreedImagesRefresherLoadingFailed(it as Exception)
                        Log.d (TAGFavoriteBreedImages, "Включен refresher, но данные пока не получили - возможно ошибка")

                    }
                    else {
                        FavoriteBreedImagesModelState.FavoriteBreedImagesLoadingFailed(it as Exception)
                        Log.d(TAGFavoriteBreedImages, "Выключен refresher, но данные пока не получили - возможно ошибка")

                    }
                )
            })?.let { compositeDisposable.add(it) }
    }


    fun clear() {
        _liveData.value = FavoriteBreedImagesModelState.Init
        Log.d(TAGFavoriteBreedImages,"Очищаем данные...")

    }

    companion object {
        private lateinit var _instance: FavoriteBreedImagesRepository
        fun getInstance(context: Context): FavoriteBreedImagesRepository {
            if (!::_instance.isInitialized)
                _instance =
                    FavoriteBreedImagesRepository(FavoriteDogImagesDatabase.getDatabaseIstance(context))
            Log.d(TAGFavoriteBreedImages,"Получаем Instance")

            return _instance
        }
    }
}