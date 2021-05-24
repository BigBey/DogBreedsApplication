package ru.android.dogbreedsapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage
import ru.bey_sviatoslav.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImagesDatabase

class LikeViewModel : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    private var dataBaseInstance: FavoriteDogImagesDatabase ?= null

    var favDogImage = MutableLiveData<_root_ide_package_.ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage>()

    fun setInstanceOfDb(databaseInstance: FavoriteDogImagesDatabase){
        this.dataBaseInstance = databaseInstance
    }

    fun getDogImageByLink(dogImageLink: String){
        dataBaseInstance?.favoriteDogImageDao()?.findDogImageByLink(dogImageLink)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                favDogImage.postValue(it)
            },{})?.let { compositeDisposable.add(it) }
    }

    fun insertDogImage(dogImageLink: String){
        val parts = dogImageLink.split('/')
        val breed = parts[parts.size - 2]
        val favDogImage =
            _root_ide_package_.ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage(
                dogImageLink,
                breed
            )
        dataBaseInstance?.favoriteDogImageDao()?.insert(favDogImage)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                getDogImageByLink(dogImageLink)
            },{})?.let { compositeDisposable.add(it) }
    }

    fun deleteDogImage(dogImageLink: String){
        val parts = dogImageLink.split('/')
        val breed = parts[parts.size - 2]
        val favDogImage =
            _root_ide_package_.ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage(
                dogImageLink,
                breed
            )
        dataBaseInstance?.favoriteDogImageDao()?.delete(favDogImage)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                getDogImageByLink(dogImageLink)
            },{})?.let { compositeDisposable.add(it) }
    }
}