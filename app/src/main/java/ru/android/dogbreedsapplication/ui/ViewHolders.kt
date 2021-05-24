package ru.android.dogbreedsapplication.ui

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage
import ru.bey_sviatoslav.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImagesDatabase
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.getFavoriteDogImageFromLink
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.getPhotosSuffix
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.getSubbreedsSuffix

const val TAGViewHolder = "ViewHolder"
class BreedViewHolder(view: View, itemListener: (Pair<String, List<String>>) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val name = view.findViewById<TextView>(R.id.breed_name)
    private val countOfSubbreeds = view.findViewById<TextView>(R.id.count_of_subbreeds)

    private lateinit var breed: Pair<String, List<String>>

    init {
        view.setOnClickListener { itemListener(breed) }
    }

    fun bind(breed: Pair<String, List<String>>) {
        this.breed = breed
        name.text = breed.first[0].toUpperCase() + breed.first.substring(1)
        countOfSubbreeds.text =
            if (breed.second.size == 0) "" else "(${breed.second.size} ${getSubbreedsSuffix(breed.second.size)})"
    }
}

class SubbreedViewHolder(view: View, itemListener: (String) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val name = view.findViewById<TextView>(R.id.subbreed_name)

    private lateinit var subbreed: String

    init {
        view.setOnClickListener { itemListener(subbreed) }
    }

    fun bind(subbreed: String) {
        Log.d(TAGViewHolder, "bind")
        this.subbreed = subbreed
        name.text = subbreed[0].toUpperCase() + subbreed.substring(1)
    }
}

class BreedImageViewHolder(view: View, itemListener: (String) -> Unit, val fragment: Fragment) :
    RecyclerView.ViewHolder(view) {
    private val breedImage = view.findViewById<ImageView>(R.id.breed_image_holder)
    private val likeBtn = view.findViewById<Button>(R.id.favBtn)
    private var likeState: Boolean = false
    private lateinit var breedImageLink: String

    init {
        likeBtn.setOnClickListener {
            onLikeClicked()
        }
    }

    fun bind(breedImageLink: String) {
        Log.d(TAGViewHolder, "bind")
        this.breedImageLink = breedImageLink
        Glide.with(itemView).load(breedImageLink).placeholder(R.drawable.ic_dog).into(breedImage)
        checkIfImageInFavorites(breedImageLink)
        setLikeState()
    }


    private fun checkIfImageInFavorites(breedImageLink: String) {
        var dogImage: _root_ide_package_.ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage? = null
        runBlocking {
            launch {
                dogImage = FavoriteDogImagesDatabase.getDatabaseIstance(fragment.context!!)
                    .favoriteDogImageDao().findDogImageByLinkAsync(breedImageLink)
                likeState = dogImage != null
            }
        }
        Log.d(TAGViewHolder, "Проверили image на favorite")
    }

    private fun setLikeState() {
        if (likeState) {
            likeBtn.setBackgroundResource(R.drawable.ic_favourite_red)
        } else {
            likeBtn.setBackgroundResource(R.drawable.ic_favourite_shadow)
        }
    }

    private fun onLikeClicked() {
        if (likeState) {
            Log.d(TAGViewHolder, "Like нажат")
            runBlocking {
                launch {
                    FavoriteDogImagesDatabase.getDatabaseIstance(fragment.context!!)
                        .favoriteDogImageDao()
                        .deleteAsync(getFavoriteDogImageFromLink(breedImageLink))
                    checkIfImageInFavorites(breedImageLink)
                    setLikeState()
                }
            }
        } else {
            Log.d(TAGViewHolder, "Like не нажат")
            runBlocking {
                launch {
                    FavoriteDogImagesDatabase.getDatabaseIstance(fragment.context!!)
                        .favoriteDogImageDao()
                        .insertAsync(getFavoriteDogImageFromLink(breedImageLink))
                    checkIfImageInFavorites(breedImageLink)
                    setLikeState()
                }
            }
        }
    }
}

class FavoriteBreedViewHolder(
    view: View,
    itemListener: (String) -> Unit,
    private val context: Context
) :
    RecyclerView.ViewHolder(view) {
    private val favoriteBreedTxtVw = view.findViewById<TextView>(R.id.favorite_breed_name)
    private val countOfFavoriteImagesTxtVw =
        view.findViewById<TextView>(R.id.count_of_favorite_images)

    private lateinit var favoriteBreed: String
    private var countOfFavoriteImages: Int = 0

    init {
        view.setOnClickListener { itemListener(favoriteBreed) }
    }

    fun bind(favoriteBreed: String) {
        this.favoriteBreed = favoriteBreed
        favoriteBreedTxtVw.text = favoriteBreed[0].toUpperCase() + favoriteBreed.substring(1)
        getCountOfFavoriteImages(favoriteBreed)
    }

    fun getCountOfFavoriteImages(favoriteBreed: String) {
        Log.d(TAGViewHolder, "Получаем количество favorite images")
        runBlocking {
            launch {
                countOfFavoriteImages =
                    FavoriteDogImagesDatabase.getDatabaseIstance(context).favoriteDogImageDao()
                        .findDogImagesByBreedAsync(favoriteBreed).size
                countOfFavoriteImagesTxtVw.text = if (countOfFavoriteImages == 0) "" else "(${countOfFavoriteImages} ${getPhotosSuffix(countOfFavoriteImages)})"
            }
        }
    }
}


class MessageViewHolder(
    view: View,
    buttonId: Int,
    listener: () -> Unit
) : RecyclerView.ViewHolder(view) {
    init {
        view.findViewById<Button>(buttonId).setOnClickListener { listener() }
    }
}

class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)