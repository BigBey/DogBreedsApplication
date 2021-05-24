package ru.android.dogbreedsapplication.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http.DogApiService
import ru.bey_sviatoslav.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImagesDatabase
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.favourite_breeds.FavoriteBreedsModelState
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.favourite_breeds.FavoriteBreedsViewState
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.subbreeds.SubbreedsModelState
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.SubbreedsResult
import java.lang.Exception

const val TAGFavoriteBreed = "FavoriteBreed"

class FavoriteBreedsRepository(private val favoriteDogImagesDb: FavoriteDogImagesDatabase) {
    protected val compositeDisposable = CompositeDisposable()
    private val _liveData = MutableLiveData<FavoriteBreedsModelState>()
    val liveData: LiveData<FavoriteBreedsModelState>
        get() = _liveData

    init {
        clear()
    }

    fun getBreeds(isRefresher: Boolean = false) {
        _liveData.value =
            if (isRefresher){
                FavoriteBreedsModelState.FavoriteBreedsRefresherLoading
                Log.d(TAGFavoriteBreed,"Включен refresher")

            }
            else{

                FavoriteBreedsModelState.FavoriteBreedsLoading
                Log.d(TAGFavoriteBreed,"Выключен refresher")

            }

        favoriteDogImagesDb.favoriteDogImageDao().getAllBreeds()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                _liveData.postValue(FavoriteBreedsModelState.FavoriteBreedsLoaded(favoriteBreeds = it))
            }, {
                _liveData.postValue(
                    if (isRefresher) {
                        FavoriteBreedsModelState.FavoriteBreedsRefresherLoadingFailed(it as Exception)
                        Log.d (TAGFavoriteBreed, "Включен refresher, но данные пока не получили - возможно ошибка")

                    }
                    else {
                        FavoriteBreedsModelState.FavoriteBreedsLoadingFailed(it as Exception)
                        Log.d(TAGFavoriteBreed, "Выключен refresher, но данные пока не получили - возможно ошибка")

                    }
                )
            })?.let { compositeDisposable.add(it) }
    }


    fun clear() {
        _liveData.value = FavoriteBreedsModelState.Init
        Log.d(TAGFavoriteBreed,"Очищаем данные...")

    }


    companion object {
        private lateinit var _instance: FavoriteBreedsRepository
        fun getInstance(context: Context): FavoriteBreedsRepository {
            if (!::_instance.isInitialized)
                _instance =
                    FavoriteBreedsRepository(FavoriteDogImagesDatabase.getDatabaseIstance(context))
            Log.d(TAGFavoriteBreed,"Получаем Instance")

            return _instance
        }
    }
}