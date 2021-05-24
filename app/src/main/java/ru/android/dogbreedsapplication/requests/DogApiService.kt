package ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.BreedImagesResult
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.BreedsResult
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.SubbreedsResult

interface DogApiService {

    @GET("breeds/list/all")
    fun getAllBreeds(): Call<BreedsResult>

    @GET("breed/{breedname}/list")
    fun getSubbreeds(@Path("breedname") breedname: String): Call<SubbreedsResult>

    @GET("breed/{breedname}/images")
    fun getBreedImages(@Path("breedname") breedname: String): Call<BreedImagesResult>

    @GET("breed/{breedname}/{subbreedname}/images")
    fun getSubbreedImages(@Path("breedname") breedname: String, @Path("subbreedname") subbreedname: String): Call<BreedImagesResult>

    /**
     * Companion object to create the DogApiService
     */
    companion object Factory{
        fun create(): DogApiService{
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(DogApiService::class.java)
        }
    }
}