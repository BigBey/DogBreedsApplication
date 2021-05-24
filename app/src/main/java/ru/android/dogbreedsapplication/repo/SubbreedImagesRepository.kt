package ru.android.dogbreedsapplication.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http.DogApiService
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breedimages.BreedImagesModelState
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breedimages.SubbreedImagesModelState
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.BreedImagesResult
import java.lang.Exception


const val TAGSubbreedImages = "SubbreedImages"

class SubbreedImagesRepository(private val dogApiService: DogApiService) {
    private val _liveData = MutableLiveData<SubbreedImagesModelState>()
    val liveData: LiveData<SubbreedImagesModelState>
        get() = _liveData

    init {
        clear()
    }

    fun getSubbreedImages(breedname : String, subbreedname: String, isRefresher: Boolean = false){
        _liveData.value =
            if (isRefresher) {
                SubbreedImagesModelState.SubbreedImagesRefresherLoading
                Log.d(TAGSubbreedImages,"Включен refresher")

            }
            else {
                SubbreedImagesModelState.SubbreedImagesLoading
                Log.d(TAGSubbreedImages,"Выключен refresher")

            }

        val call = dogApiService.getSubbreedImages(breedname, subbreedname)

        call.enqueue(object : Callback<BreedImagesResult?> {

            override fun onResponse(call: Call<BreedImagesResult?>, response: Response<BreedImagesResult?>) {
                val subbreedImages =
                    response.body()
                _liveData.postValue(SubbreedImagesModelState.SubbreedImagesLoaded(subbreedImages = subbreedImages!!.message))
                Log.d(TAGSubbreedImages,"Подгуржаем данные...")

            }

            override fun onFailure(call: Call<BreedImagesResult?>, t: Throwable) {
                _liveData.postValue(
                    if (isRefresher) {
                        SubbreedImagesModelState.SubbreedImagesRefresherLoadingFailed(t as Exception)
                        Log.d (TAGSubbreedImages, "Включен refresher, но данные пока не получили - возможно ошибка")

                    }
                    else {
                        SubbreedImagesModelState.SubbreedImagesLoadingFailed(t as Exception)
                        Log.d(TAGSubbreedImages, "Выключен refresher, но данные пока не получили - возможно ошибка")
                    }
                )
            }

        })
    }


    fun clear() {
        _liveData.value = SubbreedImagesModelState.Init
        Log.d(TAGSubbreedImages,"Очищаем данные...")

    }

    companion object {
        private lateinit var _instance: SubbreedImagesRepository
        fun getInstance(): SubbreedImagesRepository {
            if (!::_instance.isInitialized)
                _instance = SubbreedImagesRepository(DogApiService.create())
            Log.d(TAGSubbreedImages,"Получаем Instance")
            return _instance
        }
    }
}