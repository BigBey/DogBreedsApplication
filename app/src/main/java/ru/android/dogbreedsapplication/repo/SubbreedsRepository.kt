package ru.android.dogbreedsapplication.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http.DogApiService
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.subbreeds.SubbreedsModelState
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.SubbreedsResult
import java.lang.Exception


const val TAGSubbreed = "Subbreed"

class SubbreedsRepository(private val dogApiService: DogApiService) {
    private val _liveData = MutableLiveData<SubbreedsModelState>()
    val liveData: LiveData<SubbreedsModelState>
        get() = _liveData

    init {
        clear()
    }

    fun getSubbreeds(breedname : String,isRefresher: Boolean = false){
        _liveData.value =
            if (isRefresher) {
                SubbreedsModelState.SubbreedsRefresherLoading
                Log.d(TAGSubbreed,"Включен refresher")

            }
            else{
                SubbreedsModelState.SubbreedsLoading
                Log.d(TAGSubbreed,"Выключен refresher")

            }

        val call = dogApiService.getSubbreeds(breedname)

        call.enqueue(object : Callback<SubbreedsResult?> {

            override fun onResponse(call: Call<SubbreedsResult?>, response: Response<SubbreedsResult?>) {
                val subbreeds =
                    response.body()
                _liveData.postValue(SubbreedsModelState.SubbreedsLoaded(subbreeds = subbreeds!!.message))
                Log.d(TAGSubbreed,"Подгуржаем данные...")

            }

            override fun onFailure(call: Call<SubbreedsResult?>, t: Throwable) {
                _liveData.postValue(
                    if (isRefresher) {
                        SubbreedsModelState.SubbreedsRefresherLoadingFailed(t as Exception)
                        Log.d (TAGSubbreed, "Включен refresher, но данные пока не получили - возможно ошибка")

                    }
                    else {
                        SubbreedsModelState.SubbreedsLoadingFailed(t as Exception)
                        Log.d(TAGSubbreed, "Выключен refresher, но данные пока не получили - возможно ошибка")

                    }
                )
            }

        })
    }


    fun clear() {
        _liveData.value = SubbreedsModelState.Init
        Log.d(TAGSubbreed,"Очищаем данные...")

    }

    companion object {
        private lateinit var _instance: SubbreedsRepository
        fun getInstance(): SubbreedsRepository {
            if (!::_instance.isInitialized)
                _instance = SubbreedsRepository(DogApiService.create())
            Log.d(TAGSubbreed,"Получаем Instance")

            return _instance
        }
    }
}