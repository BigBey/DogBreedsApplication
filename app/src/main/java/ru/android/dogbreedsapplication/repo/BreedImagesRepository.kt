package ru.android.dogbreedsapplication.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http.DogApiService
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breedimages.BreedImagesModelState
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.BreedImagesResult
import java.lang.Exception

const val TAGBreedImages = "BreedImages"

class BreedImagesRepository(private val dogApiService: DogApiService) {
    private val _liveData = MutableLiveData<BreedImagesModelState>()
    val liveData: LiveData<BreedImagesModelState>
        get() = _liveData

    init {
        clear()
    }

    fun getBreedImages(breedname : String,isRefresher: Boolean = false){
        _liveData.value =
            if (isRefresher) {
                BreedImagesModelState.BreedImagesRefresherLoading
                Log.d(TAGBreedImages,"Включен refresher")
            }
            else{
                BreedImagesModelState.BreedImagesLoading
                Log.d(TAGBreedImages,"Выключен refresher")
            }
        val call = dogApiService.getBreedImages(breedname)

        call.enqueue(object : Callback<BreedImagesResult?> {

            override fun onResponse(call: Call<BreedImagesResult?>, response: Response<BreedImagesResult?>) {
                val breedImages =
                    response.body()
                _liveData.postValue(BreedImagesModelState.BreedImagesLoaded(breedImages = breedImages!!.message))
                Log.d(TAGBreedImages,"Подгуржаем данные...")

            }

            override fun onFailure(call: Call<BreedImagesResult?>, t: Throwable) {
                _liveData.postValue(
                    if (isRefresher) {
                        BreedImagesModelState.BreedImagesRefresherLoadingFailed(t as Exception)
                        Log.d (TAGBreedImages, "Включен refresher, но данные пока не получили - возможно ошибка")
                    }

                else {
                        BreedImagesModelState.BreedImagesLoadingFailed(t as Exception)
                        Log.d(TAGBreedImages, "Выключен refresher, но данные пока не получили - возможно ошибка")
                    }
                )
            }

        })
    }


    fun clear() {
        _liveData.value = BreedImagesModelState.Init
        Log.d(TAGBreedImages,"Очищаем данные...")

    }

    companion object {
        private lateinit var _instance: BreedImagesRepository
        fun getInstance(): BreedImagesRepository {
            if (!::_instance.isInitialized)
                _instance = BreedImagesRepository(DogApiService.create())
            Log.d(TAGBreedImages,"Получаем Instance")
            return _instance
        }
    }
}